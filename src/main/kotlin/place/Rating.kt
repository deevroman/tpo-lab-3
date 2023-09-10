package place

import base.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import page.Page

class Rating(private val driver: WebDriver): Page(driver) {
    @FindBy(css = ".business-summary-rating-badge-view__rating")
    lateinit var ratingValue: WebElement

    @FindBy(css = ".business-rating-amount-view")
    lateinit var ratingSummary: WebElement

    fun setRating(rating: Int): LoginDialog {
        elementToBeClickable(
            By.cssSelector(".business-rating-edit-view__star:nth-child($rating) path")
        ).wait(driver)
        driver.findElement(By.cssSelector(".business-rating-edit-view__star:nth-child($rating) path")).click()

        return LoginDialog(driver)
    }
}
