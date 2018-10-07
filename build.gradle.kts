import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.71"
    jacoco
}

group = "com.jforex"
version = "0.1.0"

repositories {
    jcenter()
    maven(url = "https://www.dukascopy.com/client/jforexlib/publicrepo")
}

jacoco {
    toolVersion = "0.8.2"
}

tasks.withType<JacocoReport> {
    //executionData = fileTree("$buildDir/jacoco")
    reports {
        xml.isEnabled = true

        //xml.destination= file("$buildDir/reports/jacoco/report.xml")
        html.isEnabled = false
        //csv.isEnabled =  false
    }
}

tasks.getByName("check") {
    dependsOn("jacocoTestReport")
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.dukascopy.api:JForex-API:2.13.56")
    compile("io.reactivex.rxjava2:rxkotlin:2.3.0")

    testCompile("io.kotlintest:kotlintest-runner-junit5:3.1.10")
    testCompile("io.mockk:mockk:1.8.9")
    testCompile("org.slf4j:slf4j-simple:1.7.25")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
