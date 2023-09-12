
import base.BaseTest
import org.junit.jupiter.api.Test


class AuthorizedTest : BaseTest() {
    @Test
    fun `login should be required when setting business rating`() = runTest { driver ->
        val sidebar = Sidebar(driver)
        sidebar.inputQuery("университет итмо")

        val businessView = sidebar.openBusinessFromResult()
        val ratingView = businessView.openRatingView()
        val loginDialog = ratingView.setRating(5)

        loginDialog.login()
    }
}
