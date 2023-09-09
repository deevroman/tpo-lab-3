package config

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.io.path.Path
import kotlin.io.path.bufferedReader


class Config {
    private val gson = Gson()
    private val driversConfigPath = Path("conf/drivers.json")
    private val browsersConfigPath = Path("conf/browsers.json")

    private val driversConfigTypeToken = object: TypeToken<DriversConfig>() {}
    private val selenoidBrowsersConfigTypeToken = object: TypeToken<SelenoidBrowsersConfig>() {}

    val driversConfig: DriversConfig = driversConfigPath.bufferedReader().use {
        gson.fromJson(it, driversConfigTypeToken)
    }

    val selenoidBrowsersConfig: SelenoidBrowsersConfig = browsersConfigPath.bufferedReader().use {
        gson.fromJson(it, selenoidBrowsersConfigTypeToken)
    }

    val baseUrl: String = "https://yandex.ru/maps"
}
