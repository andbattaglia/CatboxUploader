package com.battman.gradle.plugins.conventions

import com.battman.gradle.plugins.extensions.versionCatalog
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotless() {
    with(pluginManager) {
        apply("com.diffplug.spotless")
    }

    val ktlintVersion =
        versionCatalog()
            .findVersion("ktlint")
            .get()
            .requiredVersion

    extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            ktlint(ktlintVersion)
                .editorConfigOverride(
                    mapOf(
                        "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                    ),
                )
            trimTrailingWhitespace()
            endWithNewline()
        }

        kotlinGradle {
            ktlint(ktlintVersion)
            trimTrailingWhitespace()
            endWithNewline()
        }

        yaml {
            target("**/*.yaml")
            prettier()
        }

        format("misc") {
            target("*.json", "*.md", ".gitignore", ".sh")

            trimTrailingWhitespace()
            endWithNewline()
            prettier()
        }
    }
}
