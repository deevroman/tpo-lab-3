package base

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.time.Duration.ofSeconds

private const val WAIT_SECONDS = 10L
private const val WAIT_CLICKABLE_SECONDS = 2L


fun WebDriver.waitReady(seconds: Long): Boolean = wait(ofSeconds(seconds)) {
    "complete" == (this as JavascriptExecutor).executeScript(
        "return document.readyState"
    )
}

fun <T> ExpectedCondition<T>.wait(driver: WebDriver) {
    WebDriverWait(
        driver,
        ofSeconds(WAIT_SECONDS)
    ).until(this)
}

fun WebDriver.wait(duration: Duration = ofSeconds(WAIT_SECONDS), condition: () -> Boolean) =
    WebDriverWait(this, duration).until { condition() }

fun waitClickableAndClick(d: WebDriver, by: By) {
    Thread.sleep(ofSeconds(WAIT_CLICKABLE_SECONDS).toMillis())
    elementToBeClickable(by).wait(d)
    d.findElement(by).click()
}

fun waitClickableAndClick(d: WebDriver, element: WebElement) {
    Thread.sleep(ofSeconds(WAIT_CLICKABLE_SECONDS).toMillis())
    elementToBeClickable(element).wait(d)
    element.click()
}
