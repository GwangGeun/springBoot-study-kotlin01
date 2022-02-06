package com.jgg0328.kotlinfirst.config.security

import com.jgg0328.kotlinfirst.domain.repository.UserRepository
import org.springframework.data.annotation.Immutable
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * [참고]
 * - "역할"은 ROLE_ 의 접두사가 붙는 반면 "권한" TEXT 그대로 검사한다 ( spring security 는 양자를 시스템적으로 구분짓지는 않으려는 목적에서 이 정도 기능만 제공 )
 * - 역할과 권한은 대개 1:N 의 구조
 */
@Service
class CustomUserDetailService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        userRepository.findByEmail(email)?.let{
            return User(it.email, it.password, AuthorityUtils.createAuthorityList("ROLE_USER"))
        }
        throw UsernameNotFoundException("NOT_EXIST_ACCOUNT")
    }

}