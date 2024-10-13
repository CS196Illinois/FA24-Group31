/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.10.2/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.graalvm.buildtools.native") version "0.10.3"
    id("com.adarshr.test-logger") version "4.0.0"
    id("com.gradleup.shadow") version "8.3.3"
}
version "1.0-SNAPSHOT"


repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit test framework.
    testImplementation(libs.junit)

    // This dependency is used by the application.
    implementation(libs.guava)
    implementation("org.xerial:sqlite-jdbc:3.46.1.3")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.mockito:mockito-core:4.0.0")
}

// Apply a specific Java toolchain to ease working on different environments.
java { toolchain { languageVersion = JavaLanguageVersion.of(23) } }

application { mainClass = "org.server.Main" }

tasks.withType<Test> { useJUnitPlatform() }
