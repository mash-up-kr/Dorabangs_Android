import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("jacoco")
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.mashup.dorabangs.data"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "SERVER_BASE_URL", getSeverBaseUrl("server_base_url"))
        }
        release {
            isMinifyEnabled = true
            buildConfigField("String", "SERVER_BASE_URL", getSeverBaseUrl("server_base_url"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }

    buildFeatures {
        buildConfig = true
    }
}

jacoco {
    toolVersion = "0.8.11" // JaCoCo의 적절한 버전 선택
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("testDebugUnitTest"))

    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.required.set(true)
        html.outputLocation.set(file("$buildDir/reports/jacoco/jacocoTestReport"))
    }

    // `sourceDirectories` 설정
    sourceDirectories.setFrom(
        files(
            "src/main/kotlin",
            "src/test/kotlin",
        ),
    )

    // `classDirectories` 설정
    classDirectories.setFrom(
        fileTree(
            mapOf(
                "dir" to "$buildDir/tmp/kotlin-classes/debug", // 실제 테스트 클래스가 위치한 경로를 확인
                "exclude" to listOf(
                    "**/R.class",
                    "**/R$*.class",
                    "**/BuildConfig.*",
                    "**/Manifest*.*",
                    "**/*Test*.*",
                    "android/**/*.*",
                    "androidx/**/*.*",
                    "**/*\$ViewInjector*.*",
                    "**/*Dagger*.*",
                    "**/*MembersInjector*.*",
                    "**/*_Factory.*",
                    "**/*_Provide*Factory*.*",
                    "**/*_ViewBinding*.*",
                    "**/AutoValue_*.*",
                    "**/R2.class",
                    "**/R2$*.class",
                    "**/*Directions$*",
                    "**/*Directions.*",
                    "**/*Binding.*",
                    "**/*\$Lambda$*.*",
                    "**/*\$inlined$*.*",
                ),
            ),
        ),
    )

    // `executionData` 설정
    executionData.setFrom(
        fileTree(
            mapOf(
                "dir" to "$buildDir/outputs/unit_test_code_coverage/debugUnitTest", // 실제 exec 파일 위치 확인
                "includes" to listOf("**/*.exec"),
            ),
        ),
    )
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.kotlin.core)
    implementation(libs.kotlin.android)

    implementation(libs.bundles.okhttp)
    implementation(libs.retrofit)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.test)

    // Network
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.serialization)

    // Flipper
    implementation(libs.flipper)
    implementation(libs.soloader)
    implementation(libs.flipper.network)

    implementation(libs.androidx.datastore)

    // HTML
    implementation(libs.jsoup)

    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.paging)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.gson)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.test)
}

fun getSeverBaseUrl(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey).orEmpty()
}

tasks.register("dataCoverage") {
    group = "verification"
    description = "Run tests and generate jacoco report"
    dependsOn("testDebugUnitTest", "jacocoTestReport")
}
