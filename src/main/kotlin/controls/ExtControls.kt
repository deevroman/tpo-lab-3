package controls

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page
import page.PrintPage

class ExtControls(private val driver: WebDriver): Page(driver) {
    @FindBy(css = ".list-item-view:nth-child(1) > .list-item-view__content")
    private lateinit var printButton: WebElement

    fun openPrintPage(): PrintPage {
        printButton.click()
        return PrintPage(driver)
    }
}
