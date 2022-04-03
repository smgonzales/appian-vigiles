@file:Suppress("GradlePackageUpdate")

import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val kotlinSerializationVersion = "1.3.2"
val junitVersion = "5.8.2"
val detektVersion = "1.19.0"

plugins {
    val kotlinVersion = "1.6.10"
    val detektVersion = "1.19.0"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("io.gitlab.arturbosch.detekt") version detektVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-bom")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}

detekt {
    config = files("$rootDir/detekt-config.yml")
    buildUponDefaultConfig = true
}

java.sourceCompatibility = JavaVersion.VERSION_11

tasks {
    withType<Detekt> {
        autoCorrect = true
        enabled = false
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    register<Exec>("viewDetektReport") {
        commandLine("open", "build/reports/detekt/detekt.html")
    }

    register<Exec>("viewTestReport") {
        commandLine("open", "build/reports/tests/test/index.html")
    }

    jar {
        enabled = false
    }
}

defaultTasks("clean", "build")

