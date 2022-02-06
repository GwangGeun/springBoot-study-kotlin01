import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.21"
//	https://kotlinlang.org/docs/all-open-plugin.html#spring-support, https://cheese10yun.github.io/spring-kotlin/#null
	kotlin("plugin.spring") version "1.5.21"
//	https://kotlinlang.org/docs/no-arg-plugin.html#jpa-support
	kotlin("plugin.jpa") version "1.5.21"
}

group = "com.jgg0328"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

// lazy 시에 proxy object 를 가져오는데. 이 부분 대응을 위해 선언 ( 없을 경우 eager 처럼 작동 )
allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// spring cloud aws
	// https://github.com/spring-cloud/spring-cloud-aws, https://docs.awspring.io/spring-cloud-aws/docs/current/reference/html/index.html
	implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

	// https://github.com/jwtk/jjwt
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")


	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
