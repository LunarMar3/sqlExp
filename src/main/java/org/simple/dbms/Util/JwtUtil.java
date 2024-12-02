package org.simple.dbms.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.simple.dbms.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "MinecraftIsTheBestIReallyDoNotKnowHowLongTheSecretKeyNeeds";
    private static final long EXPIRATION_TIME = 3600000;

    @Autowired
    private StringRedisTemplate redisTemplate;

     public String generateToken(Account account) {
        String token = Jwts.builder()
                .setSubject(account.getUsername())
                .claim("role", account.getRole())
                .claim("accountId", account.getAccountID())
                .claim("patientID", account.getPatientID())
                .claim("doctorID", account.getDoctorID())
                .claim("adminID", account.getAdminID())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        redisTemplate.opsForValue().set(token, "valid", EXPIRATION_TIME, TimeUnit.MILLISECONDS);
        return token;
    }

    // 校验JWT的方法
    public boolean validateToken(String token) {
        try {
            String redisValue = redisTemplate.opsForValue().get(token);
            if (redisValue == null) {
                return false;
            }

            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }
    public String getPaitentID(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("patientID", String.class);
    }
    public String getDoctorID(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("doctorID", String.class);
    }
    public String getAdminID(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("adminID", String.class);
    }


    public void invalidateToken(String token) {
        redisTemplate.delete(token);
    }
}
