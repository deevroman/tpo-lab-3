package place

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page

class Post(driver: WebDriver): Page(driver) {
    @FindBy(css = ".business-post-view__title")
    lateinit var title: WebElement

    @FindBy(css = ".business-post-view__text")
    lateinit var text: WebElement
}
