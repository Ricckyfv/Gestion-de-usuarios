package com.GestionUsuariosByLucas.GestionUsuarios.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {


    @Value("${security.jwt.secret}")
    private String key;

    @Value("${security.jwt.ttlMillis}")
    private long ttlMillis;

    public String create(String id, String subject, String role) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + ttlMillis);

        return Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String getValue(String jwt) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
