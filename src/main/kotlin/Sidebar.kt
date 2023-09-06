
import base.wait
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions
import page.Page
import route.RoutePanel

open class Sidebar(private val driver: WebDriver): Page(driver) {
    // строка поиска
    @FindBy(xpath = "//input")
    private val searchInput: WebElement? = null

    @FindBy(css = "._type_route")
    private val routeButton: WebElement? = null

    @FindBy(css = "._type_close")
    private val closeButton: WebElement? = null

    @FindBy(css = ".\\_view_primary > .button__icon > .inline-image")
    private val routeToPlace: WebElement? = null

    fun inputQuery(login: String?) {
        // TODO: #5
        searchInput!!.sendKeys(login, Keys.ENTER)
    }

    fun openRoutePanel(): RoutePanel {
        // TODO: #5
        routeButton!!.click()
        return RoutePanel(driver)
    }

    fun openRouteToPlace(): RoutePanel {
        ExpectedConditions.elementToBeClickable(routeToPlace).wait(driver)
        // TODO: #5
        routeToPlace!!.click()
        return RoutePanel(driver)
    }
}