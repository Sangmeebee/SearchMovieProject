plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
        viewBinding = true
        dataBinding = true
    }
    testOptions.unitTests {
        isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(project(":domain"))

    AndroidConfig.run {
        implementation(CORE_KTX)
        implementation(APPCOMPAT)
        implementation(MATERIAL)
        implementation(CONSTRAINT_LAYOUT)
        implementation(SWIPE_REFRESH_LAYOUT)
        implementation(FRAGMENT_KTX)
    }

    NavigationConfig.run {
        implementation(FRAGMENT_KTX)
        implementation(UI_KTX)
    }

    HiltConfig.run {
        implementation(ANDROID)
        kapt(COMPILER)
    }

    implementation(CoilConfig.COIL)

    implementation(PagingConfig.PAGING_RUNTIME)

    UnitTestConfig.run {
        testImplementation(JUNIT)
        testImplementation(TRUTH)
        testImplementation(MOCKK)
        testImplementation(COROUTINE_TEST)
    }

    UITestConfig.run {
        androidTestImplementation(JUNIT)
        androidTestImplementation(ESPRESSO_CORE)
    }
}
