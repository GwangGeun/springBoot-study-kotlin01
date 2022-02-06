package com.jgg0328.kotlinfirst.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun customException(customException: CustomException): ResponseEntity<CustomExceptionResponseDto> {
        val customExceptionResponseDto =
            CustomExceptionResponseDto(customException.errorList.errorCode, customException.errorList.name)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customExceptionResponseDto)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun customException(methodArgumentNotValidException: MethodArgumentNotValidException): ResponseEntity<CustomExceptionResponseDto> {
        val customExceptionResponseDto =
            CustomExceptionResponseDto(400, methodArgumentNotValidException.bindingResult.allErrors.get(0).defaultMessage)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customExceptionResponseDto)
    }

}