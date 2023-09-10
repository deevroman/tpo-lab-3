package page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class PrintPage(driver: WebDriver): Page(driver) {
    @FindBy(css = ".print-controls-view__control .button__text")
    lateinit var printButton: WebElement
}
