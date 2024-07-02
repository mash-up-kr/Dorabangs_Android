import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.mashup.dorabangs.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "SERVER_BASE_URL", "\"${gradleLocalProperties(rootDir).getProperty("server_base_url") ?: ""}\"")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
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
    kapt(libs.hilt.compiler)

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
}

fun getSeverBaseUrl(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}
