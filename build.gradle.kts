import org.jetbrains.kotlin.contracts.model.structure.UNKNOWN_COMPUTATION.effects
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.jforex"

plugins {
    kotlin("jvm") version "1.3.11"
    jacoco
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "4.0.1"
}

repositories {
    jcenter()
    maven(url = "https://www.dukascopy.com/client/jforexlib/publicrepo")
}

val arrowVersion = "0.8.1"
dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.dukascopy.api:JForex-API:2.13.62")
    compile("io.reactivex.rxjava2:rxkotlin:2.3.0")
    compile("org.apache.logging.log4j:log4j-api:2.11.1")
    compile("org.apache.logging.log4j:log4j-core:2.11.1")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.1")
    compile("org.aeonbits.owner:owner:1.0.10")
    compile("com.jakewharton.rxrelay2:rxrelay:2.1.0")
    compile("com.github.dpaukov:combinatoricslib3:3.3.0")
    compile("io.arrow-kt:arrow-core:$arrowVersion")
    compile("io.arrow-kt:arrow-syntax:$arrowVersion")
    compile("io.arrow-kt:arrow-typeclasses:$arrowVersion")
    compile("io.arrow-kt:arrow-data:$arrowVersion")
    compile("io.arrow-kt:arrow-instances-core:$arrowVersion")
    compile("io.arrow-kt:arrow-instances-data:$arrowVersion")
    compile("io.arrow-kt:arrow-effects:$arrowVersion")
    compile("io.arrow-kt:arrow-effects-instances:$arrowVersion")
    compile("io.arrow-kt:arrow-effects-rx2:$arrowVersion")
    compile("io.arrow-kt:arrow-effects-kotlinx-coroutines:$arrowVersion")
    compile("io.arrow-kt:arrow-optics:$arrowVersion")
    compile("io.arrow-kt:arrow-recursion:$arrowVersion")
    compile("org.jetbrains.kotlin:kotlin-reflect:1.3.0")

    kapt("io.arrow-kt:arrow-annotations-processor:$arrowVersion")

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

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-proc:none")
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
