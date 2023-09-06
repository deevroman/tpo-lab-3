import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

open class Page(driver: WebDriver) {

    // строка поиска
    @FindBy(xpath = "//input")
    private val searchInput: WebElement? = null

    fun inputQuery(login: String?) {
        searchInput!!.sendKeys(login, Keys.ENTER)
    }

    init {
        PageFactory.initElements(driver, this)
    }
}