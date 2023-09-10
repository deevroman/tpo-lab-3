package route

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page
import route.Mode.MASS_TRANSIT

class MassTransitRoute(driver: WebDriver): Page(driver), Route {
    override val mode: Mode = MASS_TRANSIT

    @FindBy(css = "._mode_masstransit")
    override lateinit var modeButton: WebElement

    @FindBy(css = ".masstransit-route-snippet-view__route-title")
    override lateinit var title: WebElement
    override val titlePattern: String = "\\d{1,2} минПрибытие в \\d{1,2}:\\d{1,2}"

    @FindBy(css = ".masstransit-route-snippet-view__route-duration")
    override lateinit var duration: WebElement
}
