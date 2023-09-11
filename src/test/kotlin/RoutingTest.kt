
import base.BaseTest
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
        val business = sidebar.openBusinessByQuery("кронверский 49")
        val routePanel = business.openRouteToPlace()

        with(routePanel) {
            routeFromInput.input("ломоносова 9")
            val modes = Mode.values()
            var prevDuration = 0

            modes.forEach { mode ->
                val modeRoute = openRoute(mode)

                assertThat(modeRoute.modeButton.hasClass("_checked")).isTrue

                try {
                    val routeDuration = modeRoute.duration()

                    assertThat(routeDuration).isNotEqualTo(prevDuration)
                    prevDuration = routeDuration

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
            routeFromInput.input("кронверкский 49")
            routeToInput.input("думская 4")

            assertThat(routeFromInput.value).isEqualTo("Кронверкский проспект, 49")
            assertThat(routeToInput.value).isEqualTo("Думская улица, 4")

            val taxiRoute = openRoute(TAXI) as TaxiRoute
            val price = taxiRoute.price.text.drop(1).dropLast(2).toInt()
            assertThat(price).isGreaterThan(100)
            assertThat(price).isLessThan(1000)

            taxiRoute.peekTariffButton.click()

            assertWindowSwitched(driver)
        }
    }

    @Test
    fun nearestBar() = runTest { driver ->
        val sidebar = Sidebar(driver)

        val tooltip = sidebar.showFoodPlaces()
        assertThat(tooltip.title.text)
            .isEqualTo("Взгляните!")
        assertThat(tooltip.text.text)
            .isEqualTo("По вашему запросу мы нашли несколько подборок отличных мест," +
                    " которые рекомендуют редакция и пользователи Яндекса")

        tooltip.closeTooltip()

        sidebar.showBars()

        // TODO: #5 wait until loader
        val bar = sidebar.openBusinessFromResult().asBar()
        // TODO: #8 assert bar is displayed
        bar.openMenu()
        // TODO: #8 assert menu
        bar.openPosts()

        val post = bar.openPost(1)
        assertThat(post.title.text.lowercase())
            .isEqualTo("1 марта 2023")
        assertThat(post.text.text)
            .isEqualTo("31 Chemical network дарит скидку 20% на коктейльное меню" +
                    " в Ваш День Рождения при счете от 2000р, сертификат на 500р на посещение наших баров," +
                    " а наши ученые поздравят сладким сюрпризом!\n\n" +
                    "Скидка действует при предъявлении паспорта +/- 5 дней!\n\n" +
                    "*Скидки и акции не суммируются. Подробнее по телефону.")

        val gallery = bar.openGallery()
        gallery.openVideos()
        gallery.openPhotosInside()

        val rating = bar.openRatingView()
        assertThat(rating.ratingValue.text)
            .isEqualTo("Рейтинг \n5,0")
        assertThat(rating.ratingSummary.text)
            .matches("\\d{4} оцен(ок|ка|ки)")

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
    fun closeRoute() = runTest { driver ->
        val sidebar = Sidebar(driver)
        val business = sidebar.openBusinessByQuery("кронверкский 49")
        val routePanel = business.openRouteToPlace()

        assertThat(routePanel.isDisplayed()).isTrue

        waitClickableAndClick(driver, sidebar.closeButton)

        assertThat(routePanel.isDisplayed()).isFalse
    }

    // TODO: #8 test catalog-grid-view
}
