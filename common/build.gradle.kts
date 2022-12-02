plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Versions.TARGET_ANDROID_SDK

    defaultConfig {
        minSdk = Versions.MIN_ANDROID_SDK
        targetSdk = Versions.TARGET_ANDROID_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"https://soundtweet-370113.et.r.appspot.com/soundtweet/api/\"")
        buildConfigField("String", "USERS_PATH", "\"users/\"")
        buildConfigField("String", "TWEET_PATH", "\"tweet/\"")
        buildConfigField("String", "FILE_STACK_BASE_URL", "\"https://www.filestackapi.com/api/store/\"")
        buildConfigField("String", "FILE_STACK_KEY", "\"AJnKzXBLeRIae871w3xtbz\"")
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
    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    unitTest()

    hilt()
    navigation()
    glide()
    fastAdapter()
}