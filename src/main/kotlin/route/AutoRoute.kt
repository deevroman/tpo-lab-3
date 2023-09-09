package route

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page
import route.Mode.AUTO

class AutoRoute(driver: WebDriver): Page(driver), Route {
    override val mode: Mode = AUTO

    @FindBy(css = "._mode_auto")
    override val modeButton: WebElement? = null

    @FindBy(css = ".auto-route-snippet-view__route-title")
    override val title: WebElement? = null
    override val titlePattern: String = "\\d{1,2} минПрибытие в \\d{1,2}:\\d{1,2}"

    @FindBy(css = ".auto-route-snippet-view__route-title-primary")
    override val duration: WebElement? = null
}