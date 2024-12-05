import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

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


// Add Logback as your SLF4J implementation

// If you have other dependencies that bring in slf4j-reload4j, exclude it like this:


repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    maven("https://jitpack.io")

}

dependencies {
    // Use JUnit test framework.
    testImplementation(libs.junit)

    // This dependency is used by the application.
    implementation(libs.guava)
    implementation("org.xerial:sqlite-jdbc:3.46.1.3")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    implementation("io.github.cdimascio:dotenv-java:2.3.0")
    implementation("com.github.Mokulu:discord-oauth2-api:1.0.4")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.mockito:mockito-core:4.0.0")
}

// Apply a specific Java toolchain to ease working on different environments.
java { toolchain { languageVersion = JavaLanguageVersion.of(23) } }

application { mainClass = "org.server.Main" }

tasks.withType<Test> { useJUnitPlatform() }
