buildscript {

//    val agp_version by extra("8.3.1")

//    val agp_version1 by extra("8.3.0")
//    val agp_version2 by extra("8.1.3")
    val agp_version by extra("8.3.1")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id( "androidx.navigation.safeargs.kotlin") version "2.7.7" apply false

    id("com.google.dagger.hilt.android") version "2.48" apply false
}