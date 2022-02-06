package com.jgg0328.kotlinfirst.dto.user

import com.jgg0328.kotlinfirst.domain.enums.Gender
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

// validation kotlin : https://bit.ly/2ZgJ1SB
data class SignUpRequestDto(
    val name: String,
    val age: Int,
    @field:Pattern(regexp = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+",
        message = "check the email regex")
    @field:NotBlank(message = "email required")
    val email: String,
    val password: String,
    val gender: Gender,
    val image: String?
)

data class SignInRequestDto(val email: String, val password: String)

data class ModifyRequestDto(val email: String, val name:String)