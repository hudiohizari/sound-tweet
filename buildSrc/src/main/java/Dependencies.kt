import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Sound Tweet -
 *
 * Created by Hudio Hizari on 11/09/2022.
 * https://github.com/hudiohizari
 *
 */

object Dependencies {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Versions.MATERIAL}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"

    const val J_UNIT = "junit:junit:${Versions.J_UNIT}"

    const val J_UNIT_ANDROID = "androidx.test.ext:junit:${Versions.J_UNIT_ANDROID}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_MOSHI = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"

    const val OKHTTP_LOGGER = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP3}"

    const val CHUCK_LOGGER_DEBUG = "com.github.chuckerteam.chucker:library:${Versions.CHUCK}"
    const val CHUCK_LOGGER_RELEASE = "com.github.chuckerteam.chucker:library-no-op:${Versions.CHUCK}"

    const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"

    const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"

    const val FAST_ADAPTER = "com.mikepenz:fastadapter:${Versions.FAST_ADAPTER}"
    const val FAST_ADAPTER_BINDING = "com.mikepenz:fastadapter-extensions-binding:${Versions.FAST_ADAPTER}"
    const val FAST_ADAPTER_DIFF = "com.mikepenz:fastadapter-extensions-diff:${Versions.FAST_ADAPTER}"
    const val FAST_ADAPTER_UTILS = "com.mikepenz:fastadapter-extensions-utils:${Versions.FAST_ADAPTER}"

    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
}

fun DependencyHandler.unitTest() {
    testImplementation(Dependencies.J_UNIT)
}

fun DependencyHandler.androidTest() {
    androidTestImplementation(Dependencies.J_UNIT_ANDROID)
    androidTestImplementation(Dependencies.ESPRESSO)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.HILT)
    kapt(Dependencies.HILT_COMPILER)
}

fun DependencyHandler.navigation() {
    implementation(Dependencies.NAVIGATION_FRAGMENT)
    implementation(Dependencies.NAVIGATION_UI)
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER_MOSHI)
    implementation(Dependencies.OKHTTP_LOGGER)
    debugImplementation(Dependencies.CHUCK_LOGGER_DEBUG)
    releaseImplementation(Dependencies.CHUCK_LOGGER_RELEASE)
}

fun DependencyHandler.fastAdapter() {
    implementation(Dependencies.FAST_ADAPTER)
    implementation(Dependencies.FAST_ADAPTER_BINDING)
    implementation(Dependencies.FAST_ADAPTER_DIFF)
    implementation(Dependencies.FAST_ADAPTER_UTILS)
}

fun DependencyHandler.glide() {
    implementation(Dependencies.GLIDE)
    annotationProcessor(Dependencies.GLIDE_COMPILER)
}

private fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

private fun DependencyHandler.debugImplementation(depName: String) {
    add("debugImplementation", depName)
}

private fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}

private fun DependencyHandler.releaseImplementation(depName: String) {
    add("releaseImplementation", depName)
}

private fun DependencyHandler.annotationProcessor(depName: String) {
    add("annotationProcessor", depName)
}

private fun DependencyHandler.testImplementation(depName: String) {
    add("testImplementation", depName)
}

private fun DependencyHandler.androidTestImplementation(depName: String) {
    add("androidTestImplementation", depName)
}
