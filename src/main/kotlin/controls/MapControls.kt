package controls

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page

class MapControls(private val driver: WebDriver) : Page(driver) {
    // кнопка пробок
    @FindBy(css = ".traffic-raw-icon__text")
    private lateinit var trafficButton: WebElement

    // кнопка с тремя точками
    @FindBy(xpath = "//div[2]/div/button")
    private lateinit var extButton: WebElement

    fun clickTrafficButton() {
        trafficButton.click()
    }

    fun openExtControls(): ExtControls {
        extButton.click()
        return ExtControls(driver)
    }
}
