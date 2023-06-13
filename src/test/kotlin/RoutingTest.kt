import org.junit.jupiter.api.Test
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.By.xpath
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable


class RoutingTest : BaseTest() {

    @Test
    fun simpleRoute() {
        runTest(::simpleRouteOneDriver, drivers)
    }

    private fun simpleRouteOneDriver(d: WebDriver) {
        val mainPage = MainPage(d)
        mainPage.inputQuery("кронверский 49")

        elementToBeClickable(cssSelector(".\\_view_primary > .button__icon > .inline-image")).wait(d)
        mainPage.driver.findElement(cssSelector(".\\_view_primary > .button__icon > .inline-image")).click()

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

        mainPage.driver.findElement(cssSelector("._no-shadow")).click()
        mainPage.driver.findElement(cssSelector(".list-item-view:nth-child(1) > .list-item-view__content")).click()

        assertWindowSwitched(d)

        mainPage.driver.findElement(cssSelector(".print-controls-view__page-controls:nth-child(1) > .print-controls-view__control .button__text"))
    }

    @Test
    fun taxi() {
        runTest(::taxiOneDriver, drivers)
    }

    private fun taxiOneDriver(d: WebDriver) {
        val mainPage = MainPage(d)

        mainPage.driver.findElement(cssSelector(".small-search-form-view__icon path:nth-child(2)")).click()
        mainPage.driver.findElement(xpath("//div[2]/div/div/span/span/input")).click()
        mainPage.inputQuery("кронверский 49")
        mainPage.driver.findElement(xpath("//div[2]/div/div/div[2]/div/div/span/span/input")).click()
        mainPage.inputQuery("думская")
        mainPage.driver.findElement(cssSelector("._mode_taxi")).click()
        mainPage.driver.findElement(xpath("//span[contains(.,'Выбрать тариф')]")).click()
    }

}