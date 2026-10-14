package com.example.fpsarena.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "fpsArenaSecretKey2026MuitoSeguraParaJWT123456";

    private static final long EXPIRATION_TIME =
            1000L * 60 * 60 * 24;

    private final SecretKey key;

    public JwtService() {
        this.key = Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String gerarToken(Long usuarioId, String nome, String role) {

        return Jwts.builder()
                .subject(nome)
                .claim("usuarioId", usuarioId)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(key)
                .compact();
    }

    public String extrairNome(String token) {

        return extrairClaims(token).getSubject();
    }

    public String extrairRole(String token) {

        return extrairClaims(token)
                .get("role", String.class);
    }

    public Long extrairUsuarioId(String token) {
        Object valor = extrairClaims(token).get("usuarioId");
        return ((Number) valor).longValue();
    }
    public boolean tokenValido(String token) {

        try {
            extrairClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extrairClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}