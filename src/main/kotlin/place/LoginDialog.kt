package place

import base.wait
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import page.Page

class LoginDialog(private val driver: WebDriver): Page(driver) {
    @FindBy(css = ".login-dialog-view__button button")
    private val loginButton: WebElement? = null

    fun login() {
        elementToBeClickable(loginButton).wait(driver)
        loginButton!!.click()
    }
}