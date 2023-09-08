package place

import base.waitClickableAndClick
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import page.Page

class Gallery(private val driver: WebDriver): Page(driver) {
    @FindBy(css = ".carousel__item:nth-child(2) .button__text")
    private val videosTab: WebElement? = null

    @FindBy(css = ".carousel__item:nth-child(3) .button__text")
    private val photosInsideTab: WebElement? = null

    fun openVideos() = waitClickableAndClick(driver, videosTab)

    fun openPhotosInside() = waitClickableAndClick(driver, photosInsideTab)
}