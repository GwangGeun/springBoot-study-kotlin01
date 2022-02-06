package com.jgg0328.kotlinfirst.config

import com.jgg0328.kotlinfirst.utils.LogExtension
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.security.Principal
import java.util.*


/* @EnableJpaAuditing : 메임 함수에 있을 경우, @WebMvcTest 에 의해 스캔된다.
 * 스캔되면 안되는 이유 : @EnableJpaAuditing 를 사용하기 위해서는 최소 하나의 @Entity 클래스가 필요하지만, test 환경에서는 설정해줄 이유가 없기 때문 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider") // JPA Auditing 활성화
class JpaConfig {

    val log = LogExtension.log(this.javaClass)

    @Bean
    fun auditorProvider(): AuditorAware<String>? {
        return AuditorAware {
            val authentication =
                Optional.ofNullable(SecurityContextHolder.getContext().authentication)
            log.info("auditor {}", authentication.toString())
            authentication.map { obj: Principal ->
                (
                        // 회원 가입시에 anonymousUser 로 createdUser 가 등록되는 것을 방지하기 위한 목적
                        if (obj.name == "anonymousUser") null
                        else obj.name
                        )
            }
        }
    }

}