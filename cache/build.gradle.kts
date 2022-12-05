plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
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

    testOptions.unitTests {
        isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(project(":data"))

    implementation(CoroutineConfig.CORE)

    RoomConfig.run {
        implementation(ROOM_RUNTIME)
        kapt(ROOM_COMPILER)
        implementation(ROOM_KTX)
    }

    implementation(ConverterConfig.GSON)

    HiltConfig.run {
        implementation(ANDROID)
        kapt(COMPILER)
    }

    UnitTestConfig.run {
        testImplementation(JUNIT)
        testImplementation(ANDROIDX_JUNIT_KTX)
        testImplementation(ANDROIDX_CORE_KTX)
        testImplementation(ROBOLECTRIC)
        testImplementation(TRUTH)
        testImplementation(COROUTINE_TEST)
        testImplementation(MOCKK)
    }
}
