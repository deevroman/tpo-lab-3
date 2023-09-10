package base

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

fun <T> ExpectedCondition<T>.wait(driver: WebDriver) {
    WebDriverWait(
        driver,
        Duration.ofSeconds(10)
    ).until(this)
}

fun waitClickableAndClick(d: WebDriver, by: By) {
    Thread.sleep(Duration.ofSeconds(2).toMillis())
    elementToBeClickable(by).wait(d)
    d.findElement(by).click()
}

fun waitClickableAndClick(d: WebDriver, element: WebElement) {
    Thread.sleep(Duration.ofSeconds(2).toMillis())
    elementToBeClickable(element).wait(d)
    element.click()
}