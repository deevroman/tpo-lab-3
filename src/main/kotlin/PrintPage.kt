import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class PrintPage(driver: WebDriver) {

    private var driver: WebDriver

    init {
        PageFactory.initElements(driver, this)
        this.driver = driver
    }

    // кнопка печати
    @FindBy(xpath = "//div[3]/button/span")
    private val printButton: WebElement? = null

}