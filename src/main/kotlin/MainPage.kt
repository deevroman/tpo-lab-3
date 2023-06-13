import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class MainPage(driver: WebDriver) {

    var driver: WebDriver

    init {
        PageFactory.initElements(driver, this)
        this.driver = driver
    }

    // строка поиска
    @FindBy(xpath = "//input")
    private val searchInput: WebElement? = null

    // кнопка с тремя точками
    @FindBy(css = ".traffic-control > .button")
    private val trafficButton: WebElement? = null

    // кнопка с тремя точками
    @FindBy(xpath = "//div[2]/div/button")
    private val extButton: WebElement? = null

    fun inputQuery(login: String?) {
        searchInput!!.sendKeys(login, Keys.ENTER)
    }

    fun clickTrafficButton() {
        trafficButton!!.click()
    }

    fun clickExtButton() {
        extButton!!.click()
    }
}