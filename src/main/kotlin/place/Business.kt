package place

import base.waitClickableAndClick
import base.waitReady
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page
import route.RoutePanel
import java.time.Duration

open class Business(private val driver: WebDriver) : Page(driver) {
    @FindBy(css = ".\\_view_primary > .button__icon > .inline-image")
    private lateinit var routeToPlace: WebElement

    @FindBy(css = ".business-header-rating-view__text")
    private lateinit var ratingButton: WebElement

    fun openRouteToPlace(): RoutePanel {
        Thread.sleep(Duration.ofSeconds(1).toMillis())
        routeToPlace.click()
        return RoutePanel(driver)
    }

    fun openRatingView(): Rating {
        Thread.sleep(Duration.ofSeconds(1).toMillis())
        ratingButton.click()
        return Rating(driver)
    }

    fun openPosts() {
        waitClickableAndClick(driver, cssSelector(".\\_name_posts"))
        driver.waitReady(2)
    }

    fun openPost(i: Int): Post {
        waitClickableAndClick(
            driver,
            cssSelector(".business-posts-list-post-view:nth-child($i) > .business-posts-list-post-view__read-more")
        )
        return Post(driver)
    }


    fun openGallery(): Gallery {
        waitClickableAndClick(driver, cssSelector("._name_gallery"))
        return Gallery(driver)
    }

    fun openFeatures(): Features {
        waitClickableAndClick(driver, cssSelector("._name_features"))
        return Features(driver)
    }

    fun asBar() = Bar(driver)
}
