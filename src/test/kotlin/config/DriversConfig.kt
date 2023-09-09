package config

data class DriversConfig(
    val browsers: Set<String>,
    val local: Boolean,
    val opts: DriverOpts
)

data class DriverOpts(
    val sessionTimeout: String,
    val env: List<String>,
    val labels: Map<String, String>,
    val enableVideo: Boolean,
    val enableVNC: Boolean,
)
