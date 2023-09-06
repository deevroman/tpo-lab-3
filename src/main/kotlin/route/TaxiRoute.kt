package route

import Page
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class TaxiRoute(driver: WebDriver): Page(driver) {
    @FindBy(xpath = "//span[contains(.,'Выбрать тариф')]")
    val peekTariffButton: WebElement? = null
}