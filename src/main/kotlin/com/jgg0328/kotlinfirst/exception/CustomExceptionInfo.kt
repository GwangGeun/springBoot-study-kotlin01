package com.jgg0328.kotlinfirst.exception

import java.lang.RuntimeException

class CustomException(
    val errorList: CustomExceptionErrorList
) : RuntimeException()

enum class CustomExceptionErrorList(val errorCode:Int) {
    DUPLICATE_USER(errorCode = 100),
    NOT_EXIST_USER(errorCode = 101);
}

data class CustomExceptionResponseDto(val errorCode:Int, val errorMsg:String?)

