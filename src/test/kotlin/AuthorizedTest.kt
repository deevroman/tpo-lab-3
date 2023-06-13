import org.junit.jupiter.api.Test
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.WebDriver

class AuthorizedTest: BaseTest() {
    @Test
    fun `5 star`() {
        runTest(::oneDriver5Star, drivers)
    }

    private fun oneDriver5Star(d: WebDriver) {
        val mainPage = MainPage(d)
        mainPage.inputQuery("университет итмо")

        mainPage.driver.findElement(cssSelector(".scroll__container")).click()
        mainPage.driver.findElement(cssSelector(".business-rating-edit-view__star:nth-child(5) g > path")).click()
        mainPage.driver.findElement(cssSelector(".textarea__control")).click()
        mainPage.inputQuery("норм")
        mainPage.driver.findElement(cssSelector("._view_primary > .button__text:nth-child(1)"))
        mainPage.driver.findElement(cssSelector(".close-button svg")).click()
        mainPage.driver.findElement(cssSelector(".business-user-review-card-view__review .business-review-view__author-row svg")).click()
        mainPage.driver.findElement(cssSelector("._theme_red > .list-item-view__content")).click()
        mainPage.driver.findElement(cssSelector("._view_primary > .button__text:nth-child(1)")).click()



    }
}