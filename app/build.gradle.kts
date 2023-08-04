plugins {
    id("com.android.application")
    id("kotlin-android")
}

val kotlin_version: String by project
val junit_version: String by project

android {
    compileSdk = 33

    compileOptions {
        sourceCompatibility=JavaVersion.VERSION_11
        targetCompatibility=JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    defaultConfig {
        applicationId = "uk.ac.bmth.aprog.floodit"
        minSdk = 16
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles.add(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFiles.add(file("proguard-rules.pro"))
        }
    }
    namespace = "uk.ac.bournemouth.ap.floodit"
}

dependencies {
    implementation(project(":logic"))

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.vectordrawable:vectordrawable:1.1.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("io.github.pdvrieze.matrixlib:matrixlib:1.0.1")

    testImplementation("org.junit.jupiter:junit-jupiter:${rootProject.extra["junit_version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${rootProject.extra["junit_version"]}")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
