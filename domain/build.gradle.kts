@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    id("jacoco")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlin.core)
    implementation(libs.javax.inject)
    implementation(libs.paging.common)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.test)

    implementation(libs.serialization)
}

tasks.register("domainCoverage") {
    group = "verification"
    description = "Run tests and generate jacoco report"
    dependsOn("test", "jacocoTestReport")
}
