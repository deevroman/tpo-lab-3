package route

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page

class Tooltip(driver: WebDriver): Page(driver) {
    @FindBy(css = ".tooltip-content-view__title")
    lateinit var title: WebElement

    @FindBy(css = ".tooltip-content-view__text")
    lateinit var text: WebElement

    @FindBy(css = ".close-button path")
    private lateinit var closeButton: WebElement

    fun closeTooltip() = closeButton.click()
}
