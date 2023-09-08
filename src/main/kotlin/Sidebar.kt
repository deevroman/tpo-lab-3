
import base.waitClickableAndClick
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOf
import page.Page
import place.Business
import route.RoutePanel
import java.time.Duration

open class Sidebar(private val driver: WebDriver): Page(driver) {
    // строка поиска
    @FindBy(xpath = "//input")
    private val searchInput: WebElement? = null

    @FindBy(css = ".suggest__group")
    private val searchResultView: WebElement? = null

    @FindBy(css = "._type_route")
    private val routeButton: WebElement? = null

    @FindBy(css = "._type_close")
    private val closeButton: WebElement? = null

    @FindBy(css = ".close-button path")
    private val closeAlertButton: WebElement? = null

    @FindBy(css = "._id_food > .catalog-grid-view__icon")
    private val foodIcon: WebElement? = null

    @FindBy(css = ".search-list-view__list")
    val searchResultList: WebElement? = null

    fun inputQuery(login: String?): Business? {
        // TODO: #5
        searchInput!!.sendKeys(login, Keys.ENTER)
        Thread.sleep(Duration.ofSeconds(2).toMillis())

        return if (invisibilityOf(searchResultView).apply(driver))
            Business(driver)
        else null
    }

    fun openBusinessFromResult(i: Int = 1): Business {
        waitClickableAndClick(driver, cssSelector(".search-snippet-view:nth-child($i)"))
        return Business(driver)
    }

    fun openRoutePanel(): RoutePanel {
        // TODO: #5
        routeButton!!.click()
        return RoutePanel(driver)
    }

    fun showFoodPlaces() = waitClickableAndClick(driver, foodIcon)

    fun showBars() = waitClickableAndClick(driver, cssSelector("[aria-label=\"Бары\"]"))

    fun closeAlert() = closeAlertButton!!.click()
}