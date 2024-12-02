import com.battman.gradle.plugins.conventions.configureAndroidApplication
import com.battman.gradle.plugins.conventions.configureKotlinOptions
import com.battman.gradle.plugins.conventions.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle plugin class for configuring an Android application project.
 *
 * This plugin extends the functionality of the Android Gradle Plugin to set up and configure
 * a project for Android application development.
 *
 * After applying the plugin, it configures the project for Android application development.
 */
class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        with(target) {
            apply {
                plugin("com.android.application")
                plugin("kotlin-android")
                plugin("org.jetbrains.kotlin.plugin.compose")
            }

            configureAndroidApplication()
            configureKotlinOptions()
            configureSpotless()
        }
}
