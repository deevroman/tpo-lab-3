package base

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


fun WebDriver.waitReady(seconds: Long): Boolean = wait(Duration.ofSeconds(seconds)) {
    "complete" == (this as JavascriptExecutor).executeScript(
        "return document.readyState"
    )
}

fun <T> ExpectedCondition<T>.wait(driver: WebDriver) {
    WebDriverWait(
        driver,
        Duration.ofSeconds(10)
    ).until(this)
}

fun WebDriver.wait(duration: Duration = Duration.ofSeconds(10), condition: () -> Boolean) =
    WebDriverWait(this, duration).until { condition() }

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
