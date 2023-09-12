package controls
import base.BaseTest
import base.wait
import org.junit.jupiter.api.Test

class TrafficTest : BaseTest() {
    @Test
    fun traffic() = runTest { driver ->
        val mapControls = MapControls(driver)

        val trafficPanel = mapControls.openTrafficPanel()
        val trafficText = trafficPanel.trafficInfo.text

        driver.wait {
            trafficText.matches("Пробки \\d{1,2} балл(|а|ов)".toRegex())
        }
    }
}
