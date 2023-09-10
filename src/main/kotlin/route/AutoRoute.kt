package route

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import route.Mode.AUTO

class AutoRoute(driver: WebDriver) : Route(driver) {
    override val mode: Mode = AUTO

    @FindBy(css = "._mode_auto")
    override lateinit var modeButton: WebElement

    @FindBy(css = ".auto-route-snippet-view__route-title")
    override lateinit var title: WebElement
    override val titlePattern: String = "\\d{1,2} минПрибытие в \\d{1,2}:\\d{1,2}"

    @FindBy(css = ".auto-route-snippet-view__route-title-primary")
    override lateinit var duration: WebElement
}
