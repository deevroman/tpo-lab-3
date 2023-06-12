import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.concurrent.TimeUnit

open class BaseTest(private val baseUrl: String = "https://yandex.ru/maps") {
    companion object Driver {

        init {
            WebDriverManager.chromedriver().setup()
            WebDriverManager.firefoxdriver().setup()
        }
        // TODO Selenoid
        val driver: WebDriver = ChromeDriver().apply {
            manage().timeouts()?.implicitlyWait(10, TimeUnit.SECONDS)
            manage().window()?.maximize()
        }


        @AfterAll
        @JvmStatic
        fun closeDriver() {
            driver.quit()
        }

    }
    @BeforeEach
    fun openBasePage() {
        driver.navigate().to(baseUrl)
    }
}