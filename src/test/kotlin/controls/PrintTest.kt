package controls

import base.BaseTest
import org.junit.jupiter.api.Test

class PrintTest: BaseTest() {
    @Test
    fun `can print map`() = runTest { driver ->
        val mapControls = MapControls(driver)
        val extControls = mapControls.openExtControls()

        extControls.openPrintPage()

        assertWindowSwitched(driver)
    }
}
