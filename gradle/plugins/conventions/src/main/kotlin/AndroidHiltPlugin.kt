import com.battman.gradle.plugins.extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Gradle plugin class for configuring Hilt dependency injection in an Android project.
 *
 * This plugin extends the functionality of the Android Gradle Plugin to set up and configure
 * Hilt for dependency injection in an Android project.
 *
 * After applying the plugin, it configures Hilt for dependency injection in the Android project.
 */
class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        with(target) {
            apply {
                plugin("dagger.hilt.android.plugin")
                plugin("com.google.devtools.ksp")
            }

            dependencies {
                "implementation"(versionCatalog().findLibrary("dagger.hilt.android").get())
                "ksp"(versionCatalog().findLibrary("dagger.hilt.compiler").get())
            }
        }
}
