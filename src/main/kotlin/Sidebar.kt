import base.Page
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import route.RoutePanel

open class Sidebar(private val driver: WebDriver): Page(driver) {
    @FindBy(css = "._type_route")
    private val routeButton: WebElement? = null

    @FindBy(css = "._type_close")
    private val closeButton: WebElement? = null

    fun openRoutePanel(): RoutePanel {
        // TODO: #5
        routeButton!!.click()
        return RoutePanel(driver)
    }
}