package base

import config.Config
import config.DriversSupplier
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.Keys.ENTER
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions

open class BaseTest(
    private val config: Config = Config()
) {
    private val driversSupplier = DriversSupplier(config.driversConfig, config.selenoidBrowsersConfig)
    private lateinit var drivers: Map<String, WebDriver>


    @BeforeEach
    fun initDrivers() {
        drivers = driversSupplier()

        drivers.forEach {
            it.value.navigate().to(config.baseUrl)
            it.value.waitReady(10)
        }
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

    protected fun runTest(testFun: (WebDriver) -> Unit) = runTest(drivers, testFun)

    protected fun WebElement.input(text: String) = this.sendKeys(text, ENTER)

    protected fun WebElement.hasClass(className: String) = this
        .getAttribute("class")
        .split(" ")
        .contains(className)

    protected val WebElement.value: String get() = getAttribute("value")
}
