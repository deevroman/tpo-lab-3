package place

import base.wait
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import page.Page
import route.RoutePanel

class Business(private val driver: WebDriver): Page(driver) {
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
}