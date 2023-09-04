package config

data class DriversConfig(
    val browsers: Set<String>,
    val useSelenoid: Boolean,
)