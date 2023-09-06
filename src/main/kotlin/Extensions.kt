import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

fun <T> ExpectedCondition<T>.wait(driver: WebDriver) {
    WebDriverWait(
        driver,
        Duration.ofSeconds(10)
    ).until(this)
}