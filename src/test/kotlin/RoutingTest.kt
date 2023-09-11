
import base.BaseTest
import base.wait
import base.waitClickableAndClick
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openqa.selenium.NoSuchElementException
import route.Mode
import route.Mode.TAXI
import route.TaxiRoute


class RoutingTest : BaseTest() {

    @Test
    fun simpleRoute() = runTest { driver ->
        val sidebar = Sidebar(driver)
        val business = sidebar.openBusinessByQuery("кронверкский 49")
        val routePanel = business.openRouteToPlace()

        with(routePanel) {
            routeFromInput.input("ломоносова 9")
            val modes = Mode.values()

            modes.forEach { mode ->
                val modeRoute = openRoute(mode)

                assertThat(modeRoute.modeButton.hasClass("_checked")).isTrue

                try {
                    val routeDuration = modeRoute.duration()
                    assertThat(routeDuration).isGreaterThanOrEqualTo(15)
                    assertThat(modeRoute.title.text)
                        .containsPattern(modeRoute.titlePattern)
                } catch (_: NoSuchElementException) {
                    assertThat(mode == TAXI).isTrue
                    assertThat(modeRoute.routeError.text)
                        .isEqualTo("Не удалось проложить\nмаршрут на такси")
                }
            }
        }
    }

    @Test
    fun taxi() = runTest { driver ->
        val sidebar = Sidebar(driver)
        val routePanel = sidebar.openRoutePanel()

        with(routePanel) {
            routeFromInput.input("кронверкская 4")
            routeToInput.input("кронверкский 49")

            driver.wait {
                routeFromInput.value == "Кронверкская улица, 4" &&
                routeToInput.value == "Кронверкский проспект, 49"
            }

            val taxiRoute = openRoute(TAXI) as TaxiRoute
            val price = taxiRoute.price.text.drop(1).dropLast(2).toInt()

            assertThat(price).isGreaterThan(10)
            assertThat(price).isLessThan(1000)

            taxiRoute.peekTariffButton.click()

            assertWindowSwitched(driver)
        }
    }

    @Test
    fun nearestBar() = runTest { driver ->
        val sidebar = Sidebar(driver)

        val tooltip = sidebar.showFoodPlaces()
        driver.wait {
            tooltip.title.text == "Взгляните!"
        }
        assertThat(tooltip.text.text)
            .isEqualTo("По вашему запросу мы нашли несколько подборок отличных мест," +
                    " которые рекомендуют редакция и пользователи Яндекса")

        tooltip.closeTooltip()

        sidebar.showBars()

        val bar = sidebar.openBusinessFromResult().asBar()
        bar.openMenu()
        bar.openPosts()

        val post = bar.openPost(1)
        assertThat(post.title.text.lowercase())
            .isEqualTo("25 февраля 2022")
        assertThat(post.text.text)
            .isEqualTo(
                "Акция в ирландском пабе Mollie`s Mews на Большой Конюшенной улице.\n\n" +
                "Сет из 3х одинаковых шотов - 780 руб. А сет из 5ти шотов 1070 руб! \n\n\n" +
                "*Сроки действия акции уточняйте по телефону, указанному в профиле."
            )

        val gallery = bar.openGallery()
        gallery.openVideos()
        gallery.openPhotosInside()

        val rating = bar.openRatingView()
        assertThat(rating.ratingValue.text)
            .isEqualTo("Рейтинг \n4,9")
        assertThat(rating.ratingSummary.text)
            .matches("\\d{4} оцен(ок|ка|ки)")

        val features = bar.openFeatures()
        assertThat(features.getFeatureTitles(3))
            .isEqualTo(listOf(
                "Цена бокала пива:",
                "Цены:",
                "Цена бизнес-ланча:",
            ))
        assertThat(features.getFeatureValues(3))
            .isEqualTo(listOf(
                "330–630 ₽",
                "выше среднего",
                "350 ₽"
            ))
    }

    @Test
    fun closeRoute() = runTest { driver ->
        val sidebar = Sidebar(driver)
        val business = sidebar.openBusinessByQuery("кронверкский 49")
        val routePanel = business.openRouteToPlace()

        assertThat(routePanel.isDisplayed()).isTrue

        waitClickableAndClick(driver, sidebar.closeButton)

        assertThat(routePanel.isDisplayed()).isFalse
    }
}
