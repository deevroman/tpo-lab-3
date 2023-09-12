package base

import org.openqa.selenium.WebDriver
import java.util.*
import java.util.concurrent.Executors
import kotlin.io.path.Path
import kotlin.io.path.bufferedWriter
import kotlin.io.path.createFile

fun runTest(driverList: Map<String, WebDriver>, testFun: (WebDriver) -> Unit) {

    val executor = Executors.newFixedThreadPool(driverList.size)
    val results = driverList.map {
        val sessionId = "fake-id-" + UUID.randomUUID()

        Pair("${it.key}-$sessionId", it.value) to executor.submit {
            testFun(it.value)
        }
    }
    executor.shutdown()
    results.forEach { (namedDriver, result) ->
        try {
            result.get()
        } catch (e: Exception) {
            tryToSavePageSource(namedDriver)

            throw e.cause ?: e
        }
    }
}

private fun tryToSavePageSource(namedDriver: Pair<String, WebDriver>) {
    try {
        Path("build/tmp/test/${namedDriver.first}.html")
            .createFile()
            .bufferedWriter()
            .use { it.write(namedDriver.second.pageSource) }
    } catch (_: Exception) { }
}
