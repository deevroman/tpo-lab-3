package route

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page
import route.Mode.TAXI

class TaxiRoute(driver: WebDriver): Page(driver), Route {
    override val mode: Mode = TAXI

    @FindBy(xpath = "//span[contains(.,'Выбрать тариф')]")
    lateinit var peekTariffButton: WebElement

    @FindBy(css = "._mode_taxi")
    override lateinit var modeButton: WebElement

    @FindBy(css = ".taxi-route-snippet-view__title")
    override lateinit var title: WebElement
    override val titlePattern: String = "\\~\\d{2,} ₽\\d{1,2} мин"

    @FindBy(css = ".taxi-route-snippet-view__duration")
    override lateinit var  duration: WebElement

    @FindBy(css = ".taxi-route-snippet-view__price")
    val price: WebElement? = null
}
