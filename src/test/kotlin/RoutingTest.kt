import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class RoutingTest : BaseTest() {
    @Test
    fun simpleRoute() {
        WebDriverWait(
            driver,
            Duration.ofSeconds(5)
        ).until(ExpectedConditions.elementToBeClickable(By.xpath("//input")))
        driver.findElement(By.xpath("//input")).sendKeys("кронверский 49", Keys.ENTER)
        Thread.sleep(5)
        WebDriverWait(
            driver,
            Duration.ofSeconds(5)
        ).until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector(".\\_view_primary > .button__icon > .inline-image")
            )
        )
        driver.findElement(By.cssSelector(".\\_view_primary > .button__icon > .inline-image")).click()
        WebDriverWait(
            driver,
            Duration.ofSeconds(5)
        ).until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[2]/div/div/span/span/input")
            )
        )
        driver.findElement(By.xpath("//div[2]/div/div/span/span/input")).sendKeys("ломоносова 9", Keys.ENTER)
    }
}