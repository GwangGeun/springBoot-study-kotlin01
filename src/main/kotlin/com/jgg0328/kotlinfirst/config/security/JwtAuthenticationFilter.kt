package com.jgg0328.kotlinfirst.config.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        jwtTokenProvider.resolveToken(request)?.let {
            if (jwtTokenProvider.validateToken(it)) {
                try {
                    val authentication = jwtTokenProvider.getAuthentication(it)
                    SecurityContextHolder.getContext().authentication = authentication
                } catch (e: UsernameNotFoundException) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.message)
                    return
                }
            }
        }

        filterChain.doFilter(request, response)

    }

}