// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.spotless) apply true
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.google.gms.service) apply false
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            ktlint()
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }
    }
}
true // Needed to make the Suppress annotation work for the plugins block
