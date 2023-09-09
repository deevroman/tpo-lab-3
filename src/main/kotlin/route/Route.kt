package route

import org.openqa.selenium.WebElement

interface Route {
    val mode: Mode
    val modeButton: WebElement?
    val title: WebElement?
    val titlePattern: String?
    val duration: WebElement?
}

enum class Mode {
    AUTO, BICYCLE, MASS_TRANSIT, PEDESTRIAN, SCOOTER, TAXI
}