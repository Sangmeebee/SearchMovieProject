plugins {
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    HiltConfig.run {
        implementation(CORE)
        kapt(COMPILER)
    }

    implementation(CoroutineConfig.CORE)
    implementation(PagingConfig.PAGING_COMMON)

    UnitTestConfig.run {
        testImplementation(JUNIT)
        testImplementation(TRUTH)
        testImplementation(COROUTINE_TEST)
        testImplementation(MOCKK)
    }
}
