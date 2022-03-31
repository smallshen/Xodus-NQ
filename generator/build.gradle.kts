import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
//    Use Graal CE for public builds
//    id("org.endoqa.graal.gradle") version "22.0-JDK18"
}

group = "club.eridani"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.squareup:kotlinpoet:1.11.0")

}

tasks {
    withType(KotlinCompile::class.java) {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xcontext-receivers", "-opt-in=kotlin.RequiresOptIn", "-Xlambdas=indy")
            jvmTarget = "17"
        }
    }
}

