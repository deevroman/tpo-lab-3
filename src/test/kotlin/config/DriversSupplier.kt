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
import java.time.temporal.ChronoUnit

class DriversSupplier : (DriversConfig) -> Map<String, WebDriver> {
    override fun invoke(config: DriversConfig): Map<String, WebDriver> =
        if (config.useSelenoid) selenoidDrivers(config.browsers) else localDrivers(config.browsers)

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
    private fun getChromeDriver(name: String): RemoteWebDriver {
        val chromeOptions = ChromeOptions().apply {
            setCapability("browserVersion", "113.0")
            setCapability("selenoid:options", opts("$name-chrome"))
            addArguments("--remote-allow-origins=*")
        }

        val chromeDriver = RemoteWebDriver(URL("http://localhost:4444/wd/hub"), chromeOptions)

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
            setCapability("browserVersion", "113.0")
            setCapability("selenoid:options", opts("$name-firefox"))
        }

        val firefoxDriver = RemoteWebDriver(URL("http://localhost:4444/wd/hub"), firefoxOptions)

        firefoxDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        WebDriverWait(firefoxDriver, Duration.of(5, ChronoUnit.SECONDS)).until { webDriver: WebDriver ->
            "complete" == (webDriver as JavascriptExecutor).executeScript(
                "return document.readyState"
            )
        }
        return firefoxDriver
    }
}