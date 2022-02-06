package com.jgg0328.kotlinfirst.service

import com.jgg0328.kotlinfirst.config.security.JwtTokenProvider
import com.jgg0328.kotlinfirst.domain.entity.UserEntity
import com.jgg0328.kotlinfirst.domain.repository.UserRepository
import com.jgg0328.kotlinfirst.dto.user.ModifyRequestDto
import com.jgg0328.kotlinfirst.dto.user.SignInRequestDto
import com.jgg0328.kotlinfirst.dto.user.SignUpRequestDto
import com.jgg0328.kotlinfirst.exception.CustomException
import com.jgg0328.kotlinfirst.exception.CustomExceptionErrorList
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    val passwordEncoder: PasswordEncoder,
    val jwtTokenProvider: JwtTokenProvider,
    val userRepository: UserRepository
) {

    fun signup(requestDto: SignUpRequestDto): Long {
        userRepository.findByEmail(requestDto.email)?.let {
            throw CustomException(CustomExceptionErrorList.DUPLICATE_USER)
        }

        val userEntity = UserEntity(
            name = requestDto.name,
            password = passwordEncoder.encode(requestDto.password),
            age = requestDto.age,
            email = requestDto.email,
            gender = requestDto.gender,
            image = requestDto.image
        )

        return userRepository.save(userEntity).id
    }

    fun signIn(requestDto: SignInRequestDto): String {
        userRepository.findByEmail(requestDto.email)?.let {
            return jwtTokenProvider.createToken(it.email, it.name)
        }
        throw CustomException(CustomExceptionErrorList.NOT_EXIST_USER);
    }

    fun modify(requestDto: ModifyRequestDto): Long {
        userRepository.findByEmail(requestDto.email)?.let {
            it.name = requestDto.name
            return it.id
        }
        throw CustomException(CustomExceptionErrorList.NOT_EXIST_USER);
    }


}