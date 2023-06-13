import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
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

        elementToBeClickable(cssSelector(".\\_mode_masstransit")).wait(d) // TODO добавить разных видов транпорта
        mainPage.driver.findElement(cssSelector(".\\_mode_masstransit")).click()
        elementToBeClickable(cssSelector(".route-snippet-view:nth-child(1)")).wait(d)
    }
}