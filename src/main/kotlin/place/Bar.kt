package place

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class Bar(private val driver: WebDriver): Business(driver) {
    @FindBy(css = "._name_menu")
    private lateinit var toMenu: WebElement

    fun openMenu() {
        toMenu.click()
    }
}
