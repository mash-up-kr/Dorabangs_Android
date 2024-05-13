@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    id("jacoco")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
}

dependencies {
    implementation(libs.kotlin.core)
    implementation(libs.javax.inject)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.test)
}

tasks.register("dorabangs") {
    group = "verification"
    description = "Run tests and generate jacoco report"
    dependsOn("test", "jacocoTestReport")
}
