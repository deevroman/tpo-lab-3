import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.MalformedURLException
import java.net.URL
import java.time.Duration
import java.time.temporal.ChronoUnit

open class BaseTest(private val baseUrl: String = "https://yandex.ru/maps") {
    lateinit var drivers: Map<String, WebDriver>

    private fun opts(name: String): Map<String, Any> {
        val opts = HashMap<String, Any>()
        opts["name"] = name
        opts["sessionTimeout"] = "15m"
        opts["env"] = listOf("TZ=UTC")
        opts["labels"] = "manual" to "true"
//        opts["enableVideo"] = true
//        opts["videoName"] = name + "-" + LocalDate.now() + ".mp4"
        opts["enableVNC"] = true
        return opts
    }

    @Throws(MalformedURLException::class)
    fun getChromeDriver(name: String): RemoteWebDriver {
        val chromeDriver: RemoteWebDriver
        val chromeOptions = ChromeOptions()
        chromeOptions.setCapability("browserVersion", "113.0")
        chromeOptions.setCapability("selenoid:options", opts("$name-chrome"))
        chromeOptions.addArguments("--remote-allow-origins=*")
        chromeDriver = RemoteWebDriver(URL("http://localhost:4444/wd/hub"), chromeOptions)
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        WebDriverWait(chromeDriver, Duration.of(5, ChronoUnit.SECONDS)).until { webDriver: WebDriver ->
            "complete" == (webDriver as JavascriptExecutor).executeScript(
                "return document.readyState"
            )
        }
        return chromeDriver
    }

    @Throws(MalformedURLException::class)
    fun getFirefoxDriver(name: String): RemoteWebDriver {
        val firefoxDriver: RemoteWebDriver
        val firefoxOptions = FirefoxOptions()
        firefoxOptions.setCapability("browserVersion", "113.0")
        firefoxOptions.setCapability("selenoid:options", opts("$name-firefox"))
        firefoxDriver = RemoteWebDriver(URL("http://localhost:4444/wd/hub"), firefoxOptions)
        firefoxDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        WebDriverWait(firefoxDriver, Duration.of(5, ChronoUnit.SECONDS)).until { webDriver: WebDriver ->
            "complete" == (webDriver as JavascriptExecutor).executeScript(
                "return document.readyState"
            )
        }
        return firefoxDriver
    }

    @BeforeEach
    fun initDrivers() {
        drivers = mutableMapOf(
//            "chrome" to getChromeDriver(this.javaClass.name),
//            "firefox" to getFirefoxDriver(this.javaClass.name)
        )
        // for local runs
        drivers = mutableMapOf(
//            "chrome" to
//                    ChromeDriver().apply {
//                        manage().timeouts()?.implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS)
//                        manage().window()?.maximize()
//                    },
            "firefox" to
                    FirefoxDriver().apply {
                        manage().timeouts()?.implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS)
                        manage().window()?.maximize()
                    },
        )
        drivers.forEach {
            it.value.navigate().to(baseUrl)
            WebDriverWait(it.value, Duration.ofSeconds(10)).until { webDriver: WebDriver ->
                ((webDriver as JavascriptExecutor).executeScript(
                    "return document.readyState"
                ) == "complete")
            }
        }
    }

    fun <T> ExpectedCondition<T>.wait(driver: WebDriver) {
        WebDriverWait(
            driver,
            Duration.ofSeconds(10)
        ).until(this)
    }

    fun assertWindowSwitched(d: WebDriver) {
        // Wait for the new window or tab
        ExpectedConditions.numberOfWindowsToBe(2).wait(d)

        val originalWindow: String = d.windowHandle
        // Loop through until we find a new window handle
        for (windowHandle in d.windowHandles) {
            if (!originalWindow.contentEquals(windowHandle)) {
                d.switchTo().window(windowHandle)
                break
            }
        }
    }

    @AfterEach
    fun closeDriver() {
        drivers.forEach { it.value.quit() }
    }
}