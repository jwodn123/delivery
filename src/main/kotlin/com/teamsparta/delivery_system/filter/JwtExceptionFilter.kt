package com.teamsparta.delivery_system.filter

import com.teamsparta.delivery_system.web.response.ApiResponseCode
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtExceptionFilter : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            chain.doFilter(request, response)
        } catch (ex: JwtException) {
            if (ApiResponseCode.WRONG_TYPE_TOKEN.message == ex.message) {
                setResponse(response, ApiResponseCode.WRONG_TYPE_TOKEN)
            } else if (ApiResponseCode.EXPIRED_TOKEN.message == ex.message) {
                setResponse(response, ApiResponseCode.EXPIRED_TOKEN)
            } else if (ApiResponseCode.UNSUPPORTED_TOKEN.message == ex.message) {
                setResponse(response, ApiResponseCode.UNSUPPORTED_TOKEN)
            } else {
                setResponse(response, ApiResponseCode.INTERNAL_SERVER_ERROR)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    @Throws(RuntimeException::class, IOException::class)
    private fun setResponse(response: HttpServletResponse, errorCode: ApiResponseCode) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = errorCode.status.value()
        response.writer.print(errorCode.message)
    }
}