@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.mashup.dorabangs"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mashup.dorabangs"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            buildConfigField("Boolean", "DEBUG_MODE", "true")
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":core:coroutine"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:navigation"))
    implementation(project(":feature:home"))
    implementation(project(":feature:storage"))
    implementation(project(":feature:classification"))

    implementation(libs.core.ktx)
    implementation(libs.kotlin.android)
    implementation(libs.kotlin.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Compose
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.material3)

    // Flipper
    implementation(libs.flipper)
    implementation(libs.soloader)
    implementation(libs.flipper.network)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Network
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.serialization)
}
