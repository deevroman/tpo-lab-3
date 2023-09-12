package route

import base.wait
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import page.Page

class RoutePanel(private val driver: WebDriver): Page(driver) {
    @FindBy(css = "[placeholder=\"Откуда\"]")
    lateinit var routeFromInput: WebElement

    @FindBy(css = "[placeholder=\"Куда\"]")
    lateinit var routeToInput: WebElement

    private val routes = listOf<Route>(
        AutoRoute(driver),
        BicycleRoute(driver),
        MassTransitRoute(driver),
        PedestrianRoute(driver),
        ScooterRoute(driver),
        TaxiRoute(driver)
    )

    private val routesToViews = routes.associateBy { it.mode }

    fun openRoute(mode: Mode): Route {
        val route = routesToViews[mode] ?: throw IllegalArgumentException("Mode: $mode")
        elementToBeClickable(route.modeButton).wait(driver)
        route.modeButton.click()
        return route
    }

    fun isDisplayed() = try {
        listOf(
            routeToInput,
            routeFromInput
        )
            .any { it.isDisplayed }
    } catch (_: NoSuchElementException) {
        false
    }
}
