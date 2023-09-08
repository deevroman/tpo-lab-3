package controls
import base.BaseTest
import base.wait
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class TrafficTest : BaseTest() {
    @Test
    fun traffic() = runTest { driver ->
        val mapControls = MapControls(driver)

        mapControls.clickTrafficButton()
        Thread.sleep(Duration.ofSeconds(5).toMillis())
        ExpectedConditions.elementToBeClickable(cssSelector(".traffic-panel-view__dropdown-title"))
            .wait(driver)
        val trafficText = driver.findElement(cssSelector(".traffic-panel-view__dropdown-title"))
            .text.split(" ")

        println(trafficText) // fixes index out of bound
        assertEquals(trafficText[0], "Пробки")
        assertTrue(trafficText[1].toInt() in 1..10)
        assertThat(trafficText[2]).isIn("балла", "баллов")
        // Click on canvas?
        // mainPage.driver.findElement(linkText("ГАТИ Правительства Санкт-Петербурга")).click()
        // assertWindowSwitched(d)
    }
}