package com.battman.gradle.plugins.extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import java.io.FileInputStream
import java.util.Properties

/**
 * Retrieves the version catalog for managing project dependencies.
 *
 * This method accesses the `VersionCatalogsExtension` in the project’s extensions and returns
 * the catalog named "libs". The version catalog allows for central management of dependency versions.
 *
 * @return The `VersionCatalog` named "libs" from the project extensions.
 */
internal fun Project.versionCatalog(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

/**
 * Reads a properties file and loads its contents into a [Properties] object.
 *
 * This method reads the specified properties file from the project directory
 * and loads its key-value pairs into a [Properties] object, which can then
 * be accessed within the project.
 *
 * @param filename The name of the properties file to be read, relative to the project directory.
 * @return A [Properties] object containing the key-value pairs from the specified file.
 *
 * @throws FileNotFoundException If the specified file does not exist.
 * @throws IOException If an error occurs while reading the file.
 */
fun Project.readProperties(filename: String): Properties {
    val propertiesFile = file(filename)
    return Properties().apply {
        load(FileInputStream(propertiesFile))
    }
}
