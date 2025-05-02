plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "tech.sourceid.assessment.authsdk"
    compileSdk = 35

    defaultConfig {
        applicationId = "tech.sourceid.assessment.authsdk"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"  // The same version as above
        kotlinCompilerVersion = "1.8.0"  // Or the latest stable version
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.security.crypto)

    // Jetpack Compose dependencies
    implementation(libs.androidx.ui) // UI components
    implementation(libs.androidx.material3) // Material Design 3 components
    implementation(libs.androidx.runtime) // Compose runtime
    implementation(libs.androidx.foundation) // Foundation components
    implementation(libs.androidx.activity.compose) // For `setContent` and activity-compose functions


}