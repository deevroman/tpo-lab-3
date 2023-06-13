import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class TaxiPage(driver: WebDriver) {

    private var driver: WebDriver

    init {
        PageFactory.initElements(driver, this)
        this.driver = driver
    }

    @FindBy(xpath = ".Header__middle")
    private val taxiHeader: WebElement? = null
}