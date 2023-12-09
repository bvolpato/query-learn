/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.4/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application

    id("com.diffplug.spotless") version "6.23.3"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("org.apache.calcite:calcite-core:1.36.0")
    implementation("com.google.guava:guava:32.1.1-jre")
    implementation("org.xerial:sqlite-jdbc:3.44.0.0")

    testImplementation("junit:junit:4.13.2")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("org.brunovolpato.calcite.learn.App")
}

spotless {
    java {
        googleJavaFormat()
        formatAnnotations()
    }
}