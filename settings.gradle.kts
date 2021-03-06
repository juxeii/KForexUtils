rootProject.name = "KForexUtils"
enableFeaturePreview("STABLE_PUBLISHING")

buildscript {
    repositories {
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("gradle.plugin.net.vivin:gradle-semantic-build-versioning:4.0.0")
    }
}

plugins.apply("net.vivin.gradle-semantic-build-versioning")