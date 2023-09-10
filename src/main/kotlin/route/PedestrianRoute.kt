package route

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page
import route.Mode.PEDESTRIAN

class PedestrianRoute(driver: WebDriver): Page(driver), Route {
    override val mode: Mode = PEDESTRIAN

    @FindBy(css = "._mode_pedestrian")
    override lateinit var modeButton: WebElement

    @FindBy(css = ".pedestrian-route-snippet-view__route-title")
    override lateinit var title: WebElement
    override val titlePattern: String = "\\d+ мин\\d+\\,\\d+ км"

    @FindBy(css = ".pedestrian-route-snippet-view__route-title-primary")
    override lateinit var duration: WebElement
}
