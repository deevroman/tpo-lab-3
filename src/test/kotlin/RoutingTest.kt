import org.junit.jupiter.api.Test
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.By.xpath
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe


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

        elementToBeClickable(cssSelector(".\\_mode_masstransit")).wait(d) // TODO добавить разных видов транпорта
        mainPage.driver.findElement(cssSelector(".\\_mode_masstransit")).click()
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

        // Wait for the new window or tab
        numberOfWindowsToBe(2).wait(d)

        val originalWindow: String = d.windowHandle
        // Loop through until we find a new window handle
        for (windowHandle in d.windowHandles) {
            if (!originalWindow.contentEquals(windowHandle)) {
                d.switchTo().window(windowHandle)
                break
            }
        }
        mainPage.driver.findElement(cssSelector(".print-controls-view__page-controls:nth-child(1) > .print-controls-view__control .button__text"))
    }
}