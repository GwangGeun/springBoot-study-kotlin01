package com.jgg0328.kotlinfirst.controller

import com.jgg0328.kotlinfirst.utils.AwsFeature
import com.jgg0328.kotlinfirst.service.TestService
import com.jgg0328.kotlinfirst.utils.LogExtension
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class TestController(
    val testService: TestService,
    val awsConfig: AwsFeature
){

    val log = LogExtension.log(this.javaClass)

    @GetMapping("/ping")
    fun pong() = testService.pong("pong")

    @GetMapping("/log/test")
    fun logTest(){
        log.debug("this is debug")
        log.info("this is info")
    }

    @PostMapping("/s3")
    fun s3TestUpload(@RequestParam("file") file: MultipartFile):String{
        return testService.uploadeS3(file)
    }

    @GetMapping("/s3")
    fun s3TestDownload(@RequestParam fileName:String):ResponseEntity<ByteArray>{
        log.info("fileName : $fileName")
        val data = testService.downloadS3(fileName)
        return ResponseEntity.ok()
            .contentLength(data.size.toLong())
            .header("Content-type","application/octet-stream")
            .header("Content-disposition","attachment; filename=${fileName}")
            .body(data)
    }

    @DeleteMapping("/s3")
    fun s3TestDelete(@RequestParam fileName:String):String{
        return testService.deleteS3(fileName)
    }

    @GetMapping("/s3/exist")
    fun s3TestExist(@RequestParam fileName:String):String{
        return testService.existS3Object(fileName)
    }

//    @GetMapping("/s3")
//    fun s3Test(){
//        log.info("hello ${awsConfig.bucket}")
//    }

}