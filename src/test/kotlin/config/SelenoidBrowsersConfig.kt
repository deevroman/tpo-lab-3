package config

data class SelenoidBrowsersConfig(
    val chrome: SelenoidBrowserConfig,
    val firefox: SelenoidBrowserConfig,
)

data class SelenoidBrowserConfig(
    val default: String // default version in X.Y.Z format
)
