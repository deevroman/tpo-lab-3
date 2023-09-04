package config

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.io.path.Path
import kotlin.io.path.bufferedReader


class Config {
    private val path = Path("conf/drivers.json")
    private val gson = Gson()
    private val typeToken = object: TypeToken<DriversConfig>() {}

    val driversConfig: DriversConfig = path.bufferedReader().use {
        gson.fromJson(it, typeToken)
    }

    val baseUrl: String = "https://yandex.ru/maps"
}