plugins {
	java
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "no.hvl.dat250"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
    implementation("org.hibernate.orm:hibernate-core:7.1.1.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    implementation("com.h2database:h2:2.3.232")
    implementation("redis.clients:jedis:6.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
