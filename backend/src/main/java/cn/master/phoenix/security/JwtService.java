package cn.master.phoenix.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Created by 11's papa on 2025/4/25
 */
@Service
public class JwtService {
    private static final String SECRET_KEY = "RJ1GhpjrWCBTbo3gfwHNa4dq4eBuSvNtxWx4OwW9K9V87kkML+Ww7vyT1idm+av2";
    // 30分钟
    private static final long ACCESS_TOKEN_EXPIRATION = 1_000L * 60 * 30;
    // 7天
    private static final long REFRESH_TOKEN_EXPIRATION = 1_000L * 60 * 60 * 24 * 7;

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, REFRESH_TOKEN_EXPIRATION);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, ACCESS_TOKEN_EXPIRATION);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
