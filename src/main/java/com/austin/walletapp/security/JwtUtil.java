package com.austin.walletapp.security;

import com.austin.walletapp.exceptions.ValidationException;
import com.austin.walletapp.models.User;
import com.austin.walletapp.repositories.UserRepository;
import com.austin.walletapp.utils.LocalMemStorage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUtil {
    private final LocalMemStorage memStorage;
    private final UserRepository userRepository;

    @Value("${app.jwtsecret}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ValidationException("error generating token"));
        Map<String, Object> claims = new HashMap<>();
        claims.put("uuid", user.getUuid());
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ValidationException("error generating token"));
        Map<String, Object> claims = new HashMap<>();
        claims.put("uuid", user.getUuid());
        return createRefreshToken(claims, userDetails.getUsername());
    }

    private String createRefreshToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        if (memStorage.keyExist("Blacklist")) {
            String[] blacklistTokens = memStorage.getValueByKey("Blacklist").split(" ,");
            Set<String> blacklists = Arrays.stream(blacklistTokens).collect(Collectors.toSet());
            if (blacklists.contains(token)) {
                return false;
            }
        }

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
