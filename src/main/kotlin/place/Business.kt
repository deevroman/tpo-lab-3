package place

import base.wait
import base.waitClickableAndClick
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import page.Page
import route.RoutePanel

open class Business(private val driver: WebDriver): Page(driver) {
    @FindBy(css = ".\\_view_primary > .button__icon > .inline-image")
    private val routeToPlace: WebElement? = null

    @FindBy(css = ".business-header-rating-view__text")
    private val ratingButton: WebElement? = null

    fun openRouteToPlace(): RoutePanel {
        elementToBeClickable(routeToPlace).wait(driver)
        // TODO: #5
        routeToPlace!!.click()
        return RoutePanel(driver)
    }

    fun openRatingView(): Rating {
        elementToBeClickable(ratingButton).wait(driver)
        ratingButton!!.click()
        return Rating(driver)
    }

    fun openPosts() = waitClickableAndClick(driver, cssSelector(".\\_name_posts"))

    fun openPost(i: Int) = waitClickableAndClick(
        driver,
        cssSelector(".business-posts-list-post-view:nth-child($i) > .business-posts-list-post-view__read-more")
    )


    fun openGallery(): Gallery {
        waitClickableAndClick(driver, cssSelector("._name_gallery"))
        return Gallery(driver)
    }

    fun openFeatures() = waitClickableAndClick(driver, cssSelector("._name_features"))

    fun asBar() = Bar(driver)
}
