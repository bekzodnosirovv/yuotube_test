package com.example.util;

import com.example.dto.JwtDTO;
import com.example.exp.UnAuthorizedException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String tokenSecretKey = "@youtube_test@";
    private final int tokenLifeTime = 1000 * 3600 * 24*7;

    public String encode(Integer id, String email) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenSecretKey);
        jwtBuilder.claim("id", id);
        jwtBuilder.claim("email", email);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + tokenLifeTime));
        jwtBuilder.setIssuer("youtube_test");
        return jwtBuilder.compact();
    }

    public JwtDTO decode(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(tokenSecretKey);
            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            Integer id = (Integer) claims.get("id");
            String email = (String) claims.get("email");
            return new JwtDTO(id, email);
        } catch (JwtException e) {
            throw new UnAuthorizedException("Your session expired");
        }
    }
}
