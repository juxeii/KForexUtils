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

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.dukascopy.api:JForex-API:2.13.56")

    testCompile(kotlin("stdlib-jdk8"))
    testCompile("io.kotlintest:kotlintest-runner-junit5:3.1.10")
    testCompile("io.mockk:mockk:1.8.9")
    testCompile("org.slf4j:slf4j-simple:1.7.25")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
