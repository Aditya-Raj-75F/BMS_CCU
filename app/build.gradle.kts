import java.io.FileInputStream
import java.util.Properties
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // Plugin for ksp annotation processor
    id("com.google.devtools.ksp")

    // UI testing with Junit5
    id("de.mannodermaus.android-junit5") version "1.10.0.0"
    id("org.jetbrains.kotlin.kapt")
}

val configProperties = Properties()
configProperties.load(FileInputStream(rootProject.file("config.properties")))

android {
    namespace = "com.example.bms_ccu"
    compileSdk = 34

    packaging {
        resources.excludes.add("META-INF/*")
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.bms_ccu"
        minSdk = 24
        targetSdk = 33
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
        defaultConfig {
            buildConfigField("String", "OPEN_WEATHER_MAP_API_KEY", "${configProperties["OPEN_WEATHER_MAP_API_KEY"]}")
            buildConfigField("String", "COUNTRY_STATE_CITY_API_KEY", "${configProperties["COUNTRY_STATE_CITY_API_KEY"]}")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // MVVM and Live Data and Fragments
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.activity:activity-ktx:1.8.0")

    // Room Database
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.test.espresso:espresso-contrib:3.5.1")
    ksp("androidx.room:room-compiler:2.6.0")

    // API Calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Android test dependency
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")

        // Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:3.5.1")

        // Others
    androidTestImplementation("androidx.room:room-testing:2.6.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.4")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

        // Junit5
    androidTestImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")

        // Mockito
    androidTestImplementation("org.mockito:mockito-core:5.7.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
    maxParallelForks = 1
}