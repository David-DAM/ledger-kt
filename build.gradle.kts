plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "2.2.21"
}

group = "com.davinchicoder"
version = "0.0.1-SNAPSHOT"
description = "ledger-kt"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.h2.console)
    implementation(libs.actuator)
    implementation(libs.jpa)
    implementation(libs.opentelemetry)
    implementation(libs.webmvc)
    implementation(libs.kotlin.reflect)
    implementation(libs.jackson.kotlin)
    implementation(libs.corutines)

    runtimeOnly(libs.h2)

    testImplementation(libs.actuator.test)
    testImplementation(libs.jpa.test)
    testImplementation(libs.opentelemetry.test)
    testImplementation(libs.webmvc.test)

    testImplementation(libs.mockk)
    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)

    testRuntimeOnly(libs.junit.launcher)

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
