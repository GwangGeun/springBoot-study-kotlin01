package com.jgg0328.kotlinfirst.controller

import com.jgg0328.kotlinfirst.dto.user.ModifyRequestDto
import com.jgg0328.kotlinfirst.dto.user.SignInRequestDto
import com.jgg0328.kotlinfirst.dto.user.SignUpRequestDto
import com.jgg0328.kotlinfirst.service.UserService
import com.jgg0328.kotlinfirst.utils.LogExtension
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class UserController(
    val userService: UserService
) {

    val log = LogExtension.log(this.javaClass)

    @PostMapping("/auth/signUp")
    fun signUp(@RequestBody @Valid requestDto: SignUpRequestDto): Long {
        log.info("signUp {}", requestDto)
        return userService.signup(requestDto)
    }

    @PostMapping("/auth/signIn")
    fun signIn(@RequestBody requestDto: SignInRequestDto): String {
        log.info("signIn {}", requestDto)
        return userService.signIn(requestDto)
    }

    @PutMapping("/api/v1/modify")
    fun modify(@RequestBody requestDto: ModifyRequestDto): Long{
        log.info("modify {}", requestDto)
        return userService.modify(requestDto)
    }

}