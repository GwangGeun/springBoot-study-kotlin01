package com.jgg0328.kotlinfirst.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    val TOKEN_PREFIX: String = "Bearer ",
    val HEADER_STRING: String = "Authorization",
    val tokenValidTime: Long = 30 * 60 * 1000L,
    val customUserDetailService: CustomUserDetailService,
) {

    private final val secretKey: String = "auth-jwt-secretKey-ax3et!@5gg5dsf@m3sdf3!!45%%"
    val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    // JWT 토큰 생성
    fun createToken(email: String, name: String): String {
        val claims: Claims = Jwts.claims();
        // 정보는 key, value 쌍으로 저장된다.
        claims["email"] = email
        claims["name"] = name
        val now = Date()
        return TOKEN_PREFIX + Jwts.builder()
            .setClaims(claims) // 정보 저장
            .setIssuedAt(now) // 토큰 발행 시간 정보
            .setExpiration(Date(now.time + tokenValidTime)) // Expire Time 지정
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    // JWT 토큰에서 인증 정보 조회
    fun getAuthentication(token: String): Authentication {
        val userDetails = customUserDetailService.loadUserByUsername(getEmailFromToken(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getEmailFromToken(token: String): String {
        return Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).body["email"].toString()
    }

    // Request Header 에서 token 추출
    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader(HEADER_STRING)
    }

    // 토큰의 유효성 + 만료일자 확인
    fun validateToken(token: String): Boolean {
        return try {
            val parseClaimsJws =
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
            // 마지막 식이 리턴 됨
            !parseClaimsJws.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

}