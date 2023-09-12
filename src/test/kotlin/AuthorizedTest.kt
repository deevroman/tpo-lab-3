
import base.BaseTest
import org.junit.jupiter.api.Test


class AuthorizedTest : BaseTest() {
    @Test
    fun `5 star`() = runTest { driver ->
        val sidebar = Sidebar(driver)
        sidebar.inputQuery("университет итмо")

        val businessView = sidebar.openBusinessFromResult()
        val ratingView = businessView.openRatingView()
        val loginDialog = ratingView.setRating(5)

        loginDialog.login()
    }
}
