import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.2.71"
    application
}

application{
    mainClassName="com.xbh.game.tank.LaunchKt"
}

group = "com.xbh.game"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url="https://jitpack.io")

}

dependencies {
    //compile 'com.github.shaunxiao:kotlinGameEngine:v0.0.4'

    compile("com.github.shaunxiao:kotlinGameEngine:v0.0.4")

    compile(kotlin("stdlib-jdk8"))

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
