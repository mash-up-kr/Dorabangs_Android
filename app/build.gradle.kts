import java.io.ByteArrayOutputStream

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.google.gms.service)
}

android {
    namespace = "com.mashup.dorabangs"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    val gitCommitCount = getGitCommitCount()

    defaultConfig {
        applicationId = "com.mashup.dorabangs"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = gitCommitCount
        versionName = libs.versions.versionName.get()

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
    implementation(project(":core:webview"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:home"))
    implementation(project(":feature:storage"))
    implementation(project(":feature:classification"))
    implementation(project(":feature:save"))
    implementation(project(":feature:share"))

    implementation(libs.core.ktx)
    implementation(libs.kotlin.android)
    implementation(libs.kotlin.core)
    implementation(libs.androidx.splash)
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

    // Firebase
    implementation(platform(libs.firebase.bom))
}

fun getGitCommitCount(): Int {
    return try {
        val stdout = ByteArrayOutputStream()
        exec {
            commandLine = listOf("git", "rev-list", "--count", "HEAD")
            standardOutput = stdout
        }
        stdout.toString().trim().toInt()
    } catch (e: Exception) {
        1 // 예외가 발생할 경우 기본값으로 1을 반환
    }
}
