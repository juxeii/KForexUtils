import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.71"
}

group = "com.jforex"
version = "0.1.0"

repositories {
    jcenter()
    maven(url = "https://www.dukascopy.com/client/jforexlib/publicrepo")
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.dukascopy.api:JForex-API:2.13.56")

    testCompile("io.kotlintest:kotlintest-runner-junit5:3.1.10")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
