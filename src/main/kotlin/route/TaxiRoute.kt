package route

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page

class TaxiRoute(driver: WebDriver): Page(driver) {
    @FindBy(xpath = "//span[contains(.,'Выбрать тариф')]")
    val peekTariffButton: WebElement? = null
}