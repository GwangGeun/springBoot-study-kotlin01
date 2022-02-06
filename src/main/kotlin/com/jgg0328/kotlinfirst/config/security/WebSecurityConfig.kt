package com.jgg0328.kotlinfirst.config.security

import org.springframework.context.annotation.Bean
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity
class WebSecurityConfig(
    val jwtTokenProvider: JwtTokenProvider
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
//        super.configure(http)
        http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .csrf { it.disable() }
//            .cors { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                /**
                 * role 관련 설정을 하기 위해서는 .authenticated() 앞단에 지정해줘야 한다.
                 * ex) it.antMatchers("/api/v1/test02").hasRole("USER")
                 */
                it.antMatchers("/api/v1/**").authenticated()
                it.anyRequest().permitAll()
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .exceptionHandling {
                it.authenticationEntryPoint(CustomAuthenticationEntryPoint())
                it.accessDeniedHandler(CustomAccessDeniedHandler())
            }

    }

    @Bean
    fun passwordEncoder():PasswordEncoder{
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}

// 이하 참고 : https://beemiel.tistory.com/11, https://jsonobject.tistory.com/477

/**
 * 401 error handling : 유효하지 않은 토큰으로 요청했을 경우 호출 됨
 */
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        req: HttpServletRequest,
        res: HttpServletResponse,
        authException: AuthenticationException
    ) {
        println("CustomAuthenticationEntryPoint is called")
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED)
    }
}

/**
 * 403 error handling : 역할,권한 등이 거부 되었을 때 호출 됨
 */
class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        req: HttpServletRequest,
        res: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        println("CustomAccessDeniedHandler is called")
        res.sendError(HttpServletResponse.SC_FORBIDDEN)
    }
}