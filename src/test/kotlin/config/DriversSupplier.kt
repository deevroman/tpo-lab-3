package config

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.MalformedURLException
import java.net.URL
import java.time.Duration
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DriversSupplier(
    private val driversConfig: DriversConfig,
    private val selenoidBrowsersConfig: SelenoidBrowsersConfig
): () -> Map<String, WebDriver> {

    private val selenoidUrl = URL("http://localhost:4444/wd/hub")

    override fun invoke(): Map<String, WebDriver> =
        if (driversConfig.local) localDrivers(driversConfig.browsers) else selenoidDrivers(driversConfig.browsers)

    private fun selenoidDrivers(browsers: Set<String>) = browsers.associateWith {
        when (it) {
            "chrome" -> getChromeDriver(this.javaClass.name)
            "firefox" -> getFirefoxDriver(this.javaClass.name)
            else -> throw IllegalArgumentException("browser: $it")
        }
    }

    private fun localDrivers(browsers: Set<String>) = browsers.associateWith {
        when (it) {
            "chrome" ->
                ChromeDriver().apply {
                    manage().timeouts()?.implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS)
                    manage().window()?.maximize()
                } as WebDriver

            "firefox" ->
                FirefoxDriver().apply {
                    manage().timeouts()?.implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS)
                    manage().window()?.maximize()
                }

            else -> throw IllegalArgumentException("browser: $it")
        }
    }

    private fun opts(name: String): Map<String, Any?> {
        return with(driversConfig.opts) {
            mutableMapOf(
                "name" to name,
                "sessionTimeout" to sessionTimeout,
                "env" to env,
                "labels" to labels,
                "enableVideo" to enableVideo,
                "videoName" to (name + "-" + LocalDate.now() + ".mp4").takeIf { enableVideo },
                "enableVNC" to enableVNC
            )
        }
    }

    @Throws(MalformedURLException::class)
    private fun getChromeDriver(name: String): RemoteWebDriver {
        val chromeOptions = ChromeOptions().apply {
            setCapability("browserVersion", selenoidBrowsersConfig.chrome.default)
            setCapability("selenoid:options", opts("$name-chrome"))
            addArguments("--remote-allow-origins=*")
        }

        val chromeDriver = RemoteWebDriver(selenoidUrl, chromeOptions)

        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        WebDriverWait(chromeDriver, Duration.of(5, ChronoUnit.SECONDS)).until { webDriver: WebDriver ->
            "complete" == (webDriver as JavascriptExecutor).executeScript(
                "return document.readyState"
            )
        }
        return chromeDriver
    }

    @Throws(MalformedURLException::class)
    private fun getFirefoxDriver(name: String): RemoteWebDriver {
        val firefoxOptions = FirefoxOptions().apply {
            setCapability("browserVersion", selenoidBrowsersConfig.firefox.default)
            setCapability("selenoid:options", opts("$name-firefox"))
        }

        val firefoxDriver = RemoteWebDriver(selenoidUrl, firefoxOptions)

        firefoxDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        WebDriverWait(firefoxDriver, Duration.of(5, ChronoUnit.SECONDS)).until { webDriver: WebDriver ->
            "complete" == (webDriver as JavascriptExecutor).executeScript(
                "return document.readyState"
            )
        }
        return firefoxDriver
    }
}