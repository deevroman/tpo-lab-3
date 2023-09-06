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
        try {
            it.get()
        } catch (e: ExecutionException) {
            println("ExecutionException: ${e.message}")
            println("ExecutionException: ${e.stackTraceToString()}")
            Assertions.fail<Any>()
        } catch (e: InterruptedException) {
            println("InterruptedException: ${e.message}")
            println("InterruptedException: ${e.stackTraceToString()}")
            Assertions.fail<Any>()
        }
    }
}