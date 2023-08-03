package com.springjwtauthorization.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Primary
public class JwtServiceImpl implements JwtService {
    @Value("${app.jwt.secret}")
    private String jwtSigningKey;

    @Override
    public String extractUserName(String token) {
        return getDecode(token).getSubject();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withIssuer("jwt-spring-security")
                .withSubject(userDetails.getUsername())
                .withClaim("password", userDetails.getPassword())
                .withClaim("roles", getRoleNames(userDetails))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .sign(Algorithm.HMAC512(jwtSigningKey));
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSigningKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("jwt-spring-security") // Verifica el emisor del token
                .build();
        verifier.verify(token);
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).get("exp").asDate();
    }

    private Map<String, Claim> extractAllClaims(String token) {
        return getDecode(token).getClaims();
    }

    private static DecodedJWT getDecode(String token) {
        return JWT.decode(token);
    }

    private List<String> getRoleNames(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
