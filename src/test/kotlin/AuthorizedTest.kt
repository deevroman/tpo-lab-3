
import base.BaseTest
import org.junit.jupiter.api.Test
import java.time.Duration


class AuthorizedTest : BaseTest() {
    @Test
    fun `5 star`() = runTest { driver ->
        val sidebar = Sidebar(driver)
        Thread.sleep(Duration.ofSeconds(2).toMillis())
        sidebar.inputQuery("университет итмо")
        val businessView = sidebar.openBusinessFromResult()

        Thread.sleep(Duration.ofSeconds(1).toMillis())
        val ratingView = businessView.openRatingView()

        Thread.sleep(Duration.ofSeconds(1).toMillis())
        val loginDialog = ratingView.setRating(5)

        Thread.sleep(Duration.ofSeconds(1).toMillis())
        loginDialog.login()

        // assertWindowSwitched(driver)
        // TODO: #8 auth page
    }
}
