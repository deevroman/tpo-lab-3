
import base.BaseTest
import base.wait
import org.junit.jupiter.api.Test
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import java.time.Duration


class AuthorizedTest : BaseTest() {
    @Test
    fun `5 star`() = runTest { driver ->
        val sidebar = Sidebar(driver)
        Thread.sleep(Duration.ofSeconds(2).toMillis())
        sidebar.inputQuery("университет итмо")

        elementToBeClickable(
            cssSelector(".search-snippet-view:nth-child(1) .search-business-snippet-view__head")
        ).wait(driver)
        driver.findElement(
            cssSelector(".search-snippet-view:nth-child(1) .search-business-snippet-view__head")
        ).click()

        Thread.sleep(Duration.ofSeconds(1).toMillis())
        elementToBeClickable(
            cssSelector(".business-header-rating-view__text")
        ).wait(driver)
        driver.findElement(
            cssSelector(".business-header-rating-view__text")
        ).click()

        Thread.sleep(Duration.ofSeconds(1).toMillis())
        elementToBeClickable(
            cssSelector(".business-rating-edit-view__star:nth-child(5) path")
        ).wait(driver)
        driver.findElement(cssSelector(".business-rating-edit-view__star:nth-child(5) path")).click()

        Thread.sleep(Duration.ofSeconds(1).toMillis())
        elementToBeClickable(
            cssSelector(".login-dialog-view")
        ).wait(driver)
    }
}