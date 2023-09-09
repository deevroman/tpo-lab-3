package route

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page
import route.Mode.TAXI

class TaxiRoute(driver: WebDriver): Page(driver), Route {
    override val mode: Mode = TAXI

    @FindBy(xpath = "//span[contains(.,'Выбрать тариф')]")
    val peekTariffButton: WebElement? = null

    @FindBy(css = "._mode_taxi")
    override val modeButton: WebElement? = null

    @FindBy(css = ".taxi-route-snippet-view__title")
    override val title: WebElement? = null
    override val titlePattern: String = "\\~\\d{2,} ₽\\d{1,2} мин"

    @FindBy(css = ".taxi-route-snippet-view__duration")
    override val duration: WebElement? = null

    @FindBy(css = ".taxi-route-snippet-view__price")
    val price: WebElement? = null
}