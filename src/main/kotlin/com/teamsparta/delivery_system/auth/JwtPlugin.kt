package com.teamsparta.delivery_system.plugin

import com.teamsparta.delivery_system.domain.enums.MemberRole
import com.teamsparta.delivery_system.web.response.ApiResponseCode
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtPlugin(
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
    @Value("\${jwt.expiration-hours}")
    private val expirationHours: Long,
    @Value("\${jwt.issuer}")
    private val issuer: String
) {
    private var SECRET_KEY: SecretKey? = null
    init {
        SECRET_KEY = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
            ?: throw IllegalStateException("Token을 발급하기 위한 Key가 적절하게 생성되지 않음")
    }

    fun generateToken(memberId: String): String {
        return Jwts.builder()
            .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
            .setSubject(memberId)   // JWT 토큰 제목
            .setIssuer(issuer)    // JWT 토큰 발급자
            .setIssuedAt(Date.from(Instant.now()))    // JWT 토큰 발급 시간
            .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))    // JWT 토큰의 만료시간 설정
            .compact()!!    // JWT 토큰 생성
    }

    fun validateTokenAndGetSubject(token: String): String {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .body
                .subject
        } catch (e: ExpiredJwtException) {
            e.printStackTrace()
            throw JwtException(ApiResponseCode.EXPIRED_TOKEN.message)
        } catch (e: SignatureException) {
            e.printStackTrace()
            throw JwtException(ApiResponseCode.WRONG_TYPE_TOKEN.message)
        } catch (e: MalformedJwtException) {
            e.printStackTrace()
            throw JwtException(ApiResponseCode.UNSUPPORTED_TOKEN.message)
        } catch (e: Exception) {
            e.printStackTrace()
            throw JwtException(ApiResponseCode.INTERNAL_SERVER_ERROR.message)
        }
    }
}