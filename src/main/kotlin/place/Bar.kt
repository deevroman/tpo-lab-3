package place

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class Bar(private val driver: WebDriver): Business(driver) {
    @FindBy(css = "._name_menu")
    private val toMenu: WebElement? = null

    fun openMenu() {
        toMenu!!.click()
    }
}