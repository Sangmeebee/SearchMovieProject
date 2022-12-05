plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.sangmeebee.searchmovieproject"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
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
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":remote"))
    implementation(project(":cache"))

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
