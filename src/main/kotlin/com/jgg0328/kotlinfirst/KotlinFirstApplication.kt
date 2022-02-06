package com.jgg0328.kotlinfirst

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinFirstApplication

fun main(args: Array<String>) {

//	val a = "spring.config.location=" + "classpath:application.yml,"+ "classpath:aws.yml"
	runApplication<KotlinFirstApplication>(*args)
}
