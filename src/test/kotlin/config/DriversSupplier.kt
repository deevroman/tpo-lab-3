package config

import base.waitReady
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.MalformedURLException
import java.net.URL
import java.time.Duration.ofSeconds
import java.time.LocalDate

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

        return RemoteWebDriver(selenoidUrl, chromeOptions).apply {
            manage().timeouts().implicitlyWait(ofSeconds(10))
            waitReady(5)
        }
    }

    @Throws(MalformedURLException::class)
    private fun getFirefoxDriver(name: String): RemoteWebDriver {
        val firefoxOptions = FirefoxOptions().apply {
            setCapability("browserVersion", selenoidBrowsersConfig.firefox.default)
            setCapability("selenoid:options", opts("$name-firefox"))
        }

        return RemoteWebDriver(selenoidUrl, firefoxOptions).apply {
            manage().timeouts().implicitlyWait(ofSeconds(10))
            waitReady(5)
        }
    }
}
