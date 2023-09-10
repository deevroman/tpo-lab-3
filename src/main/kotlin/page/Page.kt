package page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory

open class Page(driver: WebDriver) {
    init {
        PageFactory.initElements(driver, this)
    }
}
