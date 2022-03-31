import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
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
            freeCompilerArgs = listOf("-Xcontext-receivers", "-Xopt-in=kotlin.RequiresOptIn", "-Xlambdas=indy")
            jvmTarget = "17"
        }
    }
}

