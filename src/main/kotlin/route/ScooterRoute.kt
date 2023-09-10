package route

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import route.Mode.SCOOTER

class ScooterRoute(driver: WebDriver) : Route(driver) {
    override val mode: Mode = SCOOTER

    @FindBy(css = "._mode_scooter")
    override lateinit var modeButton: WebElement

    @FindBy(css = ".bicycle-route-snippet-view__route-title")
    override lateinit var title: WebElement
    override val titlePattern: String = "\\d+ мин\\d+\\,\\d+ км"

    @FindBy(css = ".bicycle-route-snippet-view__route-title-primary")
    override lateinit var duration: WebElement
}
