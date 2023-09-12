package place

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page

class Post(driver: WebDriver): Page(driver) {
    @FindBy(css = ".business-posts-list-post-view__date")
    lateinit var date: WebElement

    @FindBy(css = ".business-posts-list-post-view__text")
    lateinit var text: WebElement
}
