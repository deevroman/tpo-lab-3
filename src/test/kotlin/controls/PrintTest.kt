package controls

import base.BaseTest
import org.junit.jupiter.api.Test

class PrintTest: BaseTest() {
    @Test
    fun print() = runTest { driver ->
        val mapControls = MapControls(driver)
        val extControls = mapControls.openExtControls()

        val printPage = extControls.openPrintPage()

        assertWindowSwitched(driver)

        // TODO: #5
        printPage.printButton!!.click()
    }
}