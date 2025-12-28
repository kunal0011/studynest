// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
}

// Ensure the Gradle plugin classpath / all project configurations use a single JavaPoet version
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Make JavaPoet available on the buildscript classpath for plugins/tasks that expect a specific API
        classpath("com.squareup:javapoet:1.13.0")
    }
}

// Force a single JavaPoet version across all configurations to avoid mixed-version runtime errors
configurations.all {
    resolutionStrategy {
        force("com.squareup:javapoet:1.13.0")
    }
}
