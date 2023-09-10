package controls

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page

class TrafficPanel(driver: WebDriver): Page(driver) {
    @FindBy(css = ".traffic-panel-view__dropdown-title")
    lateinit var trafficInfo: WebElement
}