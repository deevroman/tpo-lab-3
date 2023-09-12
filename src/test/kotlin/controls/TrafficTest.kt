package controls
import base.BaseTest
import base.wait
import org.junit.jupiter.api.Test

class TrafficTest : BaseTest() {
    @Test
    fun `can see traffic info`() = runTest { driver ->
        val mapControls = MapControls(driver)

        val trafficPanel = mapControls.openTrafficPanel()
        val trafficText = trafficPanel.trafficInfo.text

        driver.wait {
            trafficText.contains("Пробки")
        }
    }
}
