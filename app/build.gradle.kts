plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.greendefend"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.greendefend"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{dataBinding=true
        viewBinding=true
        mlModelBinding = true

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
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.window:window:1.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.4.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")
    //retrofit and converter
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.5.0")

    //lifecycle viewmodel
    val lifecycleVersion = "2.7.0"
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-livedata:$lifecycleVersion")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    //pinview
    implementation ("io.github.chaosleung:pinview:1.4.4")
    //countrycode
    implementation ("com.hbb20:ccp:2.6.0")

    //ask permission
    implementation ("com.karumi:dexter:6.2.3")

    implementation("com.google.android.material:material:1.11.0")

    //spalsh api
    implementation("androidx.core:core-splashscreen:1.0.1")

//data store prefrences
    implementation("androidx.datastore:datastore-preferences:1.0.0-alpha04")

    //courtine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    //glide
implementation("com.github.bumptech.glide:glide:4.16.0")

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}