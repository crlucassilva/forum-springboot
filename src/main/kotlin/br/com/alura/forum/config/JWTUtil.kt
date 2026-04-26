package br.com.alura.forum.config

import br.com.alura.forum.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil(
    private val usuarioService: UsuarioService
) {

    private val expiration: Long = 60000

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    private val key by lazy {
        Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String? {
        return Jwts.builder()
            .subject(username)
            .claim("role", authorities)
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key)
            .compact()
    }

    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
            true
        } catch (e: IllegalStateException) {
            false
        }
    }

    fun getAuthentication(jwt: String?): UsernamePasswordAuthenticationToken {
        val username = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        val user = usuarioService.loadUserByUsername(username.payload.subject)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }
}