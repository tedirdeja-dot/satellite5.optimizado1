plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.satellite.wallpaper"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.satellite.wallpaper"
        minSdk = 23
        targetSdk = 35
        versionCode = 21
        versionName = "4.2"
    }

    val signingEnvironment = listOf(
        "ANDROID_KEYSTORE_PATH",
        "ANDROID_KEYSTORE_PASSWORD",
        "ANDROID_KEY_ALIAS",
        "ANDROID_KEY_PASSWORD"
    ).associateWith { System.getenv(it) }
    val hasAnySigningValue = signingEnvironment.values.any { !it.isNullOrBlank() }
    val hasAllSigningValues = signingEnvironment.values.all { !it.isNullOrBlank() }
    check(!hasAnySigningValue || hasAllSigningValues) {
        "Configura las cuatro variables de firma de Android o ninguna."
    }

    signingConfigs {
        if (hasAllSigningValues) {
            create("ci") {
                storeFile = file(signingEnvironment.getValue("ANDROID_KEYSTORE_PATH")!!)
                storePassword = signingEnvironment.getValue("ANDROID_KEYSTORE_PASSWORD")
                keyAlias = signingEnvironment.getValue("ANDROID_KEY_ALIAS")
                keyPassword = signingEnvironment.getValue("ANDROID_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        debug {
            if (hasAllSigningValues) signingConfig = signingConfigs.getByName("ci")
        }
        release {
            isMinifyEnabled = false
            if (hasAllSigningValues) signingConfig = signingConfigs.getByName("ci")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
