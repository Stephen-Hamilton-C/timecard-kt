/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.4.2/userguide/building_java_projects.html
 */

fun runCommand(command: String): String {
	val process = ProcessBuilder()
		.command(command.split(" "))
		.directory(rootProject.projectDir)
		.start()
	return process.inputStream.bufferedReader().readText()
}

fun isAppRelease(): Boolean = runCommand("git log -n 1 --pretty=%d HEAD").contains("main")
fun getCommit(): String = runCommand("git rev-parse --short HEAD")

val appBaseVersion = "1.0.0"
val appVersion = if(isAppRelease()) { appBaseVersion } else { "$appBaseVersion+SNAPSHOT.${getCommit()}" }

tasks.create("example") {
	doLast {
		println(isAppRelease())
		println(getCommit())
		println(appVersion)
	}
}

plugins {
	// Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
	id("org.jetbrains.kotlin.jvm") version "1.7.0"

	// Kotlinx Serialization
	kotlin("plugin.serialization") version "1.7.0"

	// Apply the application plugin to add support for building a CLI application in Java.
	application

	// Windows EXE build
	id("edu.sc.seis.launch4j") version "2.5.3"
}

repositories {
	// Use Maven Central for resolving dependencies.
	mavenCentral()
	maven("https://jitpack.io")
}

dependencies {
	// Align versions of all Kotlin components
	implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

	// Use the Kotlin JDK 8 standard library.
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Kotlinx JSON
	// https://github.com/Kotlin/kotlinx.serialization/
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

	// Kaml - YAML Support for kotlinx.serialization
	// https://github.com/charleskorn/kaml
	implementation("com.charleskorn.kaml:kaml:0.45.0")

	// KAppDirs
	// Better than Harawata's AppDirs as KAppDirs doesn't import JNA
	// https://github.com/erayerdin/kappdirs
	implementation("com.github.erayerdin:kappdirs:0.3.1-alpha")

	// Use the Kotlin test library.
	testImplementation("org.jetbrains.kotlin:kotlin-test")

	// Use the Kotlin JUnit integration.
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
	// Define the main class for the application.
	mainClass.set("app.shamilton.timecardkt.AppKt")
}

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

// Create Windows EXE build
tasks.withType<edu.sc.seis.launch4j.tasks.DefaultLaunch4jTask> {
	outfile = "timecard-kt.exe"
	mainClassName = "app.shamilton.timecardkt.AppKt"
	productName = "timecard-kt"
}
