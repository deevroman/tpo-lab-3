import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.By.xpath
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import java.time.Duration


class RoutingTest : BaseTest() {

    @Test
    fun simpleRoute() {
        runTest(::simpleRouteOneDriver, drivers)
    }

    private fun simpleRouteOneDriver(d: WebDriver) {
        val mainPage = MainPage(d)
        Thread.sleep(Duration.ofSeconds(1).toMillis())
        mainPage.inputQuery("кронверский 49")

        Thread.sleep(Duration.ofSeconds(2).toMillis())
        elementToBeClickable(cssSelector(".\\_view_primary > .button__icon > .inline-image")).wait(d)
        mainPage.driver.findElement(cssSelector(".\\_view_primary > .button__icon > .inline-image")).click()

        Thread.sleep(Duration.ofSeconds(2).toMillis())
        elementToBeClickable(xpath("//div[2]/div/div/span/span/input")).wait(d)
        mainPage.driver.findElement(xpath("//div[2]/div/div/span/span/input")).sendKeys("ломоносова 9", Keys.ENTER)
        elementToBeClickable(cssSelector(".route-snippet-view:nth-child(1)")).wait(d)

        setOf(
            "._mode_masstransit",
            "._mode_bicycle",
            "._mode_auto",
            "._mode_scooter",
            "._mode_taxi"
        ).forEach {
            elementToBeClickable(cssSelector(it)).wait(d)
            mainPage.driver.findElement(cssSelector(it)).click()
        }

        elementToBeClickable(cssSelector(".route-snippet-view:nth-child(1)")).wait(d)
    }

    @Test
    fun print() {
        runTest(::printOneDriver, drivers)
    }

    private fun printOneDriver(d: WebDriver) {
        val mainPage = MainPage(d)

        mainPage.clickExtButton()
        mainPage.driver.findElement(cssSelector(".list-item-view:nth-child(1) > .list-item-view__content")).click()

        assertWindowSwitched(d)

        mainPage.driver.findElement(
            cssSelector(
                ".print-controls-view__page-controls:nth-child(1) > .print-controls-view__control .button__text"
            )
        )
    }

    @Test
    fun taxi() {
        runTest(::taxiOneDriver, drivers)
    }

    private fun taxiOneDriver(d: WebDriver) {
        val mainPage = MainPage(d)
        Thread.sleep(Duration.ofSeconds(2).toMillis())
        waitClickableAndClick(mainPage.driver, cssSelector(".small-search-form-view__icon path:nth-child(2)"))
        waitClickableAndClick(mainPage.driver, xpath("//div[2]/div/div/span/span/input"))

        mainPage.driver.findElement(xpath("//div[2]/div/div/span/span/input"))
            .sendKeys("кронверский 49", Keys.ENTER)
        mainPage.driver.findElement(xpath("//div[2]/div/div/div[2]/div/div/span/span/input"))
            .sendKeys("думская 4", Keys.ENTER)

        waitClickableAndClick(mainPage.driver, cssSelector("._mode_taxi"))
        waitClickableAndClick(mainPage.driver, xpath("//span[contains(.,'Выбрать тариф')]"))
    }

    @Test
    fun nearestBar() {
        runTest(::nearestBarOneDriver, drivers)
    }

    private fun waitClickableAndClick(d: WebDriver, by: By) {
        Thread.sleep(Duration.ofSeconds(7).toMillis())
        elementToBeClickable(by).wait(d)
        d.findElement(by).click()
    }

    private fun nearestBarOneDriver(driver: WebDriver) {
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
}