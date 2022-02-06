package com.jgg0328.kotlinfirst.service

import com.jgg0328.kotlinfirst.utils.AwsFeature
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Service
@Transactional
class TestService(
    val awsFeature: AwsFeature
){

    fun pong(str: String) = str

    fun uploadeS3(file: MultipartFile):String{
        return awsFeature.upload(file, "static")
    }

    fun downloadS3(file:String):ByteArray{
        return awsFeature.download(file)
    }

    fun deleteS3(file: String):String{
        return awsFeature.delete(file)
    }

    fun existS3Object(file: String):String{
        return awsFeature.exist(file)
    }


}