
import base.BaseTest
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
        val business = sidebar.inputQuery("кронверский 49")!!
        val routePanel = business.openRouteToPlace()

        with(routePanel) {
            routeFromInput.input("ломоносова 9")
            val modes = Mode.values()
            var prevDuration = 0

            modes.forEach { mode ->
                val modeRoute = openRoute(mode)

                assertThat(modeRoute.modeButton.hasClass("_checked")).isTrue

                try {
                    val routeDuration = modeRoute.duration
                        .text
                        .split(" ")
                        .first()
                        .toInt()

                    assertThat(routeDuration).isNotEqualTo(prevDuration)
                    prevDuration = routeDuration

                    assertThat(modeRoute.title.text)
                        .containsPattern(modeRoute.titlePattern)
                } catch (_: NoSuchElementException) {}
            }
        }
    }

    @Test
    fun taxi() = runTest { driver ->
        val sidebar = Sidebar(driver)
        val routePanel = sidebar.openRoutePanel()

        with(routePanel) {
            routeFromInput.input("кронверский 49")
            routeToInput.input("думская 4")

            val taxiRoute = openRoute(TAXI) as TaxiRoute
            val price = taxiRoute.price?.text?.drop(1)?.dropLast(2)?.toInt()
            assertThat(price).isGreaterThan(100)
            assertThat(price).isLessThan(1000)

            taxiRoute.peekTariffButton.click()

            assertWindowSwitched(driver)
        }
    }

    @Test
    fun nearestBar() = runTest { driver ->
        val sidebar = Sidebar(driver)
        sidebar.showFoodPlaces()
        sidebar.closeAlert()
        sidebar.showBars()

        // TODO: #5 wait until loader
        val bar = sidebar.openBusinessFromResult().asBar()
        bar.openMenu()
        bar.openPosts()
        bar.openPost(1)

        val gallery = bar.openGallery()
        gallery.openVideos()
        gallery.openPhotosInside()

        bar.openRatingView()
        bar.openFeatures()
    }
}
