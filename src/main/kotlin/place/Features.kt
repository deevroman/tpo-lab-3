package place

import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.WebDriver
import page.Page

class Features(private val driver: WebDriver): Page(driver) {

    private val featureTitleBy = cssSelector(".business-features-view__valued-title")
    private val featureValueBy = cssSelector(".business-features-view__valued-value")

    fun getFeatureTitles(limit: Int) = driver
        .findElements(featureTitleBy)
        .map { it.text }
        .filter { it.isNotBlank() }
        .take(limit)

    fun getFeatureValues(limit: Int) = driver
        .findElements(featureValueBy)
        .map { it.text }
        .filter { it.isNotBlank() }
        .take(limit)
}
