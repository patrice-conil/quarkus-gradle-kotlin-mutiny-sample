plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    id("org.openapi.generator") version "6.2.1"
    id("io.quarkus")
    idea
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("$quarkusPlatformGroupId:$quarkusPlatformArtifactId:$quarkusPlatformVersion"))
    implementation("io.quarkus:quarkus-smallrye-opentracing")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-jackson")

    implementation("io.quarkus:quarkus-smallrye-metrics")
    implementation("io.quarkus:quarkus-rest-client-reactive-jackson")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-resteasy-reactive")

    implementation("org.apache.commons:commons-lang3:3.12.0")

    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("io.quarkus:quarkus-hibernate-validator")

    implementation("io.swagger:swagger-annotations:1.6.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3")
    // implementation("io.quarkus:quarkus-redis-client")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
    testImplementation("io.rest-assured:rest-assured:5.1.1")
    testImplementation("io.quarkus:quarkus-jacoco")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.33.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}

group = "conil.patrice"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

allOpen {
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs += listOf("-Xjvm-default=all")
        jvmTarget = JavaVersion.VERSION_11.toString()
        javaParameters = true
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

