
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

    fun inputQuery(login: String?): Business? {
        // TODO: #5
        searchInput!!.sendKeys(login, Keys.ENTER)
        Thread.sleep(Duration.ofSeconds(2).toMillis())

        return if (invisibilityOf(searchResultView).apply(driver))
            Business(driver)
        else null
    }

    fun openBusinessFromQueryResult(i: Int = 1): Business {
        val businessRow = driver.findElement(
            cssSelector(".search-snippet-view:nth-child($i) .search-business-snippet-view__head")
        )
        businessRow.click()
        return Business(driver)
    }

    fun openRoutePanel(): RoutePanel {
        // TODO: #5
        routeButton!!.click()
        return RoutePanel(driver)
    }
}