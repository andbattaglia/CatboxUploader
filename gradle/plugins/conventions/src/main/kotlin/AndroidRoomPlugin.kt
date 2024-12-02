import com.battman.gradle.plugins.extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * A custom Gradle plugin for setting up Room in Android projects.
 * This plugin automates the process of adding Room dependencies, setting up annotation processors,
 * and performing any necessary configurations for Room database support.
 *
 */
class AndroidRoomPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        with(target) {
            apply {
                plugin("com.google.devtools.ksp")
            }

            dependencies {
                "implementation"(versionCatalog().findLibrary("androidx.room.runtime").get())
                "implementation"(versionCatalog().findLibrary("androidx.room.ktx").get())
                "ksp"(versionCatalog().findLibrary("androidx.room.compiler").get())
            }
        }
}
