package controls
import base.BaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Duration

class TrafficTest : BaseTest() {
    @Test
    fun traffic() = runTest { driver ->
        val mapControls = MapControls(driver)

        val trafficPanel = mapControls.openTrafficPanel()
        Thread.sleep(Duration.ofSeconds(5).toMillis())
        val trafficText = trafficPanel.trafficInfo.text

        assertThat(trafficText).matches("Пробки \\d{1,2} балл(|а|ов)")
        // Click on canvas?
        // mainPage.driver.findElement(linkText("ГАТИ Правительства Санкт-Петербурга")).click()
        // assertWindowSwitched(d)
    }
}
