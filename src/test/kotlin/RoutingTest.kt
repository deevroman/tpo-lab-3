
import base.BaseTest
import base.wait
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.By.xpath
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
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
            elementToBeClickable(cssSelector(".route-snippet-view:nth-child(1)")).wait(driver)
            getAllModes().forEach {
                elementToBeClickable(it).wait(driver)
                it!!.click()
            }
        }

        elementToBeClickable(cssSelector(".route-snippet-view:nth-child(1)")).wait(driver)
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
        Actions(driver).moveToElement(driver.findElement(cssSelector(".\\_id_food .catalog-grid-view__title"))).perform()
        Actions(driver).moveToElement(driver.findElement(By.tagName("body")), 0, 0).perform()
        driver.findElement(cssSelector(".\\_id_food > .catalog-grid-view__icon")).click()

        waitClickableAndClick(driver, cssSelector(".close-button path"))

        Actions(driver).moveToElement(
            driver.findElement(cssSelector(".carousel__item:nth-child(2) .filter-pictured-carousel-item"))
        ).perform()
        Actions(driver).moveToElement(driver.findElement(By.tagName("body")), 0, 0).perform()

        Actions(driver).moveToElement(
            driver.findElement(cssSelector(".carousel__item:nth-child(3) .filter-pictured-carousel-item__img"))
        ).perform()
        driver.findElement(xpath("//div[4]/a/div/div/div")).click()

        waitClickableAndClick(driver, xpath("//li/div/div/div/div[2]/div"))

        waitClickableAndClick(driver, cssSelector(".\\_name_menu"))

        waitClickableAndClick(driver, cssSelector(".\\_first .image__content"))

        waitClickableAndClick(driver, cssSelector(".photos-player-view__button svg"))

        waitClickableAndClick(driver, cssSelector(".\\_name_posts"))

        waitClickableAndClick(
            driver,
            cssSelector(".business-posts-list-post-view:nth-child(1) > .business-posts-list-post-view__read-more")
        )

        waitClickableAndClick(driver, cssSelector(".\\_name_gallery"))

        waitClickableAndClick(driver, cssSelector(".carousel__item:nth-child(2) .button__text"))

        waitClickableAndClick(driver, cssSelector(".carousel__item:nth-child(3) .button__text"))

        waitClickableAndClick(driver, cssSelector(".\\_name_reviews"))

        waitClickableAndClick(driver, cssSelector(".\\_name_chain"))
//        elementToBeClickable(cssSelector(".\\_name_features")).wait(driver)
//        driver.findElement(cssSelector(".\\_name_features")).click()
    }

    private fun waitClickableAndClick(d: WebDriver, by: By) {
        Thread.sleep(Duration.ofSeconds(7).toMillis())
        elementToBeClickable(by).wait(d)
        d.findElement(by).click()
    }
}