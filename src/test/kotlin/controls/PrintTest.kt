package controls

import base.BaseTest
import org.junit.jupiter.api.Test
import org.openqa.selenium.By

class PrintTest: BaseTest() {
    @Test
    fun print() = runTest { driver ->
        val mapControls = MapControls(driver)

        mapControls.clickExtButton()
        driver.findElement(By.cssSelector(".list-item-view:nth-child(1) > .list-item-view__content")).click()

        assertWindowSwitched(driver)

        driver.findElement(
            By.cssSelector(
                ".print-controls-view__page-controls:nth-child(1) > .print-controls-view__control .button__text"
            )
        )
    }
}