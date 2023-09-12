package place

import base.waitClickableAndClick
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page
import route.RoutePanel

open class Business(private val driver: WebDriver) : Page(driver) {
    @FindBy(css = ".\\_view_primary > .button__icon > .inline-image")
    private lateinit var routeToPlace: WebElement

    @FindBy(css = "._name_reviews")
    private lateinit var ratingButton: WebElement

    fun openRouteToPlace(): RoutePanel {
        waitClickableAndClick(driver, routeToPlace)
        return RoutePanel(driver)
    }

    fun openRatingView(): Rating {
        waitClickableAndClick(driver, ratingButton)
        return Rating(driver)
    }

    fun openPosts() {
        waitClickableAndClick(driver, cssSelector("._name_posts"))
    }

    fun openPost(i: Int): Post {
        check(i == 1)
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
