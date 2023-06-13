import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.WebDriver

class TrafficTest: BaseTest() {
    @Test
    fun traffic() {
        runTest(::trafficOneDriver, drivers)
    }

    private fun trafficOneDriver(d: WebDriver) {
        val mainPage = MainPage(d)

        mainPage.driver.findElement(cssSelector(".traffic-raw-icon__text")).click()
        val trafficText = mainPage.driver.findElement(cssSelector(".traffic-panel-view__dropdown-title"))
            .text.split(" ")

        println(trafficText) // fixes index out of bound
        assertEquals(trafficText[0], "Пробки")
        assertTrue(trafficText[1].toInt() in 1..10)
        assertEquals(trafficText[2], "балла")
        // Click on canvas?
        // mainPage.driver.findElement(linkText("ГАТИ Правительства Санкт-Петербурга")).click()
        // assertWindowSwitched(d)
    }
}