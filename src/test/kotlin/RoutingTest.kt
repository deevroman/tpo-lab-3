
import base.BaseTest
import base.wait
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import java.time.Duration


class RoutingTest : BaseTest() {

    @Test
    fun simpleRoute() = runTest { driver ->
        val sidebar = Sidebar(driver)
        Thread.sleep(Duration.ofSeconds(1).toMillis())
        val business = sidebar.inputQuery("кронверский 49")!!

        Thread.sleep(Duration.ofSeconds(2).toMillis())

        val routePanel = business.openRouteToPlace()

        Thread.sleep(Duration.ofSeconds(2).toMillis())

        with(routePanel) {
            routeFromInput.input("ломоносова 9")
            getAllModes().forEach {
                elementToBeClickable(it).wait(driver)
                it!!.click()
            }
        }
    }

    @Test
    fun taxi() = runTest { driver ->
        Thread.sleep(Duration.ofSeconds(2).toMillis())
        val sidebar = Sidebar(driver)
        val routePanel = sidebar.openRoutePanel()

        with(routePanel) {
            routeFromInput.input("кронверский 49")
            routeToInput.input("думская 4")

            val taxiRoute = openTaxiRoute()
            // TODO: #5
            taxiRoute.peekTariffButton!!.click()

            assertWindowSwitched(driver)
        }
    }

    @Test
    fun nearestBar() = runTest { driver ->
        val sidebar = Sidebar(driver)
        // TODO: #5
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