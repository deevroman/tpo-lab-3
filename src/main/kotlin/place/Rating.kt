package place

import base.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import page.Page

class Rating(private val driver: WebDriver): Page(driver) {
    fun setRating(rating: Int): LoginDialog {
        elementToBeClickable(
            By.cssSelector(".business-rating-edit-view__star:nth-child($rating) path")
        ).wait(driver)
        driver.findElement(By.cssSelector(".business-rating-edit-view__star:nth-child($rating) path")).click()

        return LoginDialog(driver)
    }
}