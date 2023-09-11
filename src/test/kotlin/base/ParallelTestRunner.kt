package base

import org.junit.jupiter.api.Assertions
import org.openqa.selenium.WebDriver
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

fun runTest(driverList: Map<String, WebDriver>, testFun: (WebDriver) -> Unit) {

    val executor = Executors.newFixedThreadPool(driverList.size)
    val results = driverList.map {
        executor.submit {
            testFun(it.value)
        }
    }
    executor.shutdown()
    results.forEach {
        it.get()
    }
}
