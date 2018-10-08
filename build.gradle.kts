import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.jforex"

plugins {
    kotlin("jvm") version "1.2.71"
    jacoco
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "4.0.1"
}

repositories {
    jcenter()
    maven(url = "https://www.dukascopy.com/client/jforexlib/publicrepo")
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.dukascopy.api:JForex-API:2.13.56")
    compile("io.reactivex.rxjava2:rxkotlin:2.3.0")

    testCompile("io.kotlintest:kotlintest-runner-junit5:3.1.10")
    testCompile("io.mockk:mockk:1.8.9")
    testCompile("org.slf4j:slf4j-simple:1.7.25")
}

jacoco {
    toolVersion = "0.8.2"
}

tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

tasks.getByName("check") {
    dependsOn("jacocoTestReport")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    repositories {
        mavenLocal()
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
}
