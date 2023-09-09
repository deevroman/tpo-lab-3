package route

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page
import route.Mode.SCOOTER

class ScooterRoute(driver: WebDriver): Page(driver), Route {
    override val mode: Mode = SCOOTER

    @FindBy(css = "._mode_scooter")
    override val modeButton: WebElement? = null

    @FindBy(css = ".bicycle-route-snippet-view__route-title")
    override val title: WebElement? = null
    override val titlePattern: String = "\\d+ мин\\d+\\,\\d+ км"

    @FindBy(css = ".bicycle-route-snippet-view__route-title-primary")
    override val duration: WebElement? = null
}