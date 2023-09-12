package controls

import base.waitClickableAndClick
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

    fun openTrafficPanel(): TrafficPanel {
        waitClickableAndClick(driver, trafficButton)
        return TrafficPanel(driver)
    }

    fun openExtControls(): ExtControls {
        waitClickableAndClick(driver, extButton)
        return ExtControls(driver)
    }
}
