
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
    fun `can make route for each mode`() = runTest { driver ->
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
    fun `can get taxi`() = runTest { driver ->
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
    fun `can open nearest bar from suggested and see its posts and rating`() = runTest { driver ->
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
        assertThat(post.date.text)
            .matches("\\d{1,2} \\W+, \\d{2}:\\d{2}")
        assertThat(post.text.text)
            .isNotBlank()

        val gallery = bar.openGallery()
        gallery.openVideos()
        gallery.openPhotosInside()

        val rating = bar.openRatingView()
        assertThat(rating.ratingValue.text)
            .matches("Рейтинг \\n\\d,\\d")
        assertThat(rating.ratingSummary.text)
            .matches("\\d+ оцен(ок|ка|ки)")
    }

    @Test
    fun `can see business features`() = runTest { driver ->
        val sidebar = Sidebar(driver)
        sidebar.inputQuery("Лаборатория 31")
        val bar = sidebar.openBusinessFromResult()
        bar.openPosts()
        bar.openRatingView()
        val features = bar.openFeatures()
        assertThat(features.getFeatureTitles(3))
            .isEqualTo(listOf(
                "Цена бокала пива:",
                "Цены:",
                "Средний счёт:",
            ))
        assertThat(features.getFeatureValues(3))
            .isEqualTo(listOf(
                "370 ₽",
                "выше среднего",
                "1000–1500 ₽"
            ))
    }

    @Test
    fun `can close route`() = runTest { driver ->
        val sidebar = Sidebar(driver)
        val business = sidebar.openBusinessByQuery("кронверкский 49")
        val routePanel = business.openRouteToPlace()

        assertThat(routePanel.isDisplayed()).isTrue

        waitClickableAndClick(driver, sidebar.closeButton)

        assertThat(routePanel.isDisplayed()).isFalse
    }
}
