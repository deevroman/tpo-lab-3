package route

import base.wait
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import page.Page

class RoutePanel(private val driver: WebDriver): Page(driver) {
    @FindBy(xpath = "//div[2]/div/div/span/span/input")
    val routeFromInput: WebElement? = null

    @FindBy(xpath = "//div[2]/div/div/div[2]/div/div/span/span/input")
    val routeToInput: WebElement? = null

    @FindBy(css = "._mode_masstransit")
    private val massTransitModeButton: WebElement? = null

    @FindBy(css = "._mode_bicycle")
    private val bicycleModeButton: WebElement? = null

    @FindBy(css = "._mode_auto")
    private val autoModeButton: WebElement? = null

    @FindBy(css = "._mode_scooter")
    private val scooterModeButton: WebElement? = null

    @FindBy(css = "._mode_taxi")
    private val taxiModeButton: WebElement? = null

    fun openTaxiRoute(): TaxiRoute {
        elementToBeClickable(taxiModeButton).wait(driver)
        taxiModeButton!!.click()
        return TaxiRoute(driver)
    }

    fun getAllModes() = listOf(
        massTransitModeButton,
        bicycleModeButton,
        autoModeButton,
        scooterModeButton,
        taxiModeButton
    )
}