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
        elementToBeClickable(xpath("//input")).wait(d)
        d.findElement(xpath("//input")).sendKeys("кронверский 49", Keys.ENTER)
        elementToBeClickable(cssSelector(".\\_view_primary > .button__icon > .inline-image")).wait(d)
        d.findElement(cssSelector(".\\_view_primary > .button__icon > .inline-image")).click()
        elementToBeClickable(xpath("//div[2]/div/div/span/span/input")).wait(d)
        d.findElement(xpath("//div[2]/div/div/span/span/input")).sendKeys("ломоносова 9", Keys.ENTER)
    }
}