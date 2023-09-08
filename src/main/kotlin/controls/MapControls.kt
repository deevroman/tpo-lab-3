package controls
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page

class MapControls(private val driver: WebDriver): Page(driver) {
    // кнопка пробок
    @FindBy(css = ".traffic-raw-icon__text")
    private val trafficButton: WebElement? = null

    // кнопка с тремя точками
    @FindBy(xpath = "//div[2]/div/button")
    private val extButton: WebElement? = null

    fun clickTrafficButton() {
        // TODO: #5
        trafficButton!!.click()
    }

    fun openExtControls(): ExtControls {
        // TODO: #5
        extButton!!.click()
        return ExtControls(driver)
    }
}