package com.jgg0328.kotlinfirst.utils

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime

@Configuration
class AwsFeature(
    val amazonS3: AmazonS3
) {

    val log = LogExtension.log(this.javaClass)

    @Value("\${cloud.aws.s3.bucket}")
    lateinit var bucket: String

    /**
     * outer logic ( It will be used from service layer )
     * 1. s3 upload
     * 2. s3 download
     * 3. s3 delete
     */
    @Throws(IOException::class)
    fun upload(file: MultipartFile, dirName: String): String {
        convert(file)?.let {
            return upload(it, dirName);
        }
        throw IllegalArgumentException("MultipartFile -> File failed")
    }

    fun download(fileName: String): ByteArray {
        val s3Object = amazonS3.getObject(bucket, fileName)
        val inputStream = s3Object.objectContent
        return try {
            IOUtils.toByteArray(inputStream)
        } catch (ex: IOException) {
            throw IOException("s3 IOException occurred")
        }
    }

    fun delete(fileName: String):String{
        amazonS3.deleteObject(bucket, fileName)
        return "$fileName removed..."
    }

    fun exist(fileName: String):String{
        return if(amazonS3.doesObjectExist(bucket, fileName))
            fileName
        else
            "not_exist"
    }

    /**
     * 이하 inner logic - s3 upload
     */
    fun upload(uploadFile: File, dirName: String): String {

        val fileName = LocalDateTime.now().nano.toString() + uploadFile.name
//        val fileName = dirName + "/" + uploadFile.name
        val uploadImageUrl = putS3(uploadFile, fileName)
        removeNewFile(uploadFile)
        return uploadImageUrl
    }

    fun putS3(uploadFile: File, fileName: String): String {
        amazonS3.putObject(
            PutObjectRequest(
                bucket,
                fileName,
                uploadFile
            ).withCannedAcl(CannedAccessControlList.PublicReadWrite)
        )
        return amazonS3.getUrl(bucket, fileName).toString()
    }

    fun removeNewFile(targetFile: File) {
        if (targetFile.delete()) {
            log.info("delete file success")
        } else {
            log.info("delete file fail")
        }
    }

    @Throws(IOException::class)
    fun convert(file: MultipartFile): File? {
        file.originalFilename?.let {
            val convertFile = File("./src/main/resources/static/${it}")
            if (convertFile.createNewFile()) {
                val fos = FileOutputStream(convertFile)
                fos.write(file.bytes);
            }
            return convertFile
        }
        return null
    }

}