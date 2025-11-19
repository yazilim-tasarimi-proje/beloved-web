package beloved.beloved.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

//Jwt token oluşturma,şifreleme ve çözme için kullanılır.
@Service
public class JWTUtil {

    // Tokeni şifrelemek ve çözmek için kullanılır.
    // Bu anahtar olmadan token geçersiz olur.
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration = 86400000;

    //Token üretimi ve imzalanması gerçekleştirilir.
    public String generateToken(String email, String role) {
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }
    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new RuntimeException("Authorization header missing or invalid");
    }


    //Token çözümlemesi yapılır, imza doğruysa subject(mail) bilgisi alınır
    public String extractUsername(String token) {
        return JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }

    //Tokendaki role alanı alınır(asmin-user)
    public String extractRole(String token) {
        return JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build()
                .verify(token)
                .getClaim("role")
                .asString();
    }

    //Token bitis zamanı alınır,şu anki zamanla karşılaştırılır. Süresi dolmussa true döner.
    public boolean isTokenExpired(String token) {
        Date expiration = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build()
                .verify(token)
                .getExpiresAt();
        return expiration.before(new Date());
    }

    //Tokendaki username(mail) ile beklenen kullanıcı adı eşleşiyor mu ve token geçerli mi kontrolü yapılır.
    //Her ikisi de doğruysa true döner-> güvenli token
    public Boolean validateToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    //Token decode edilir.Jwt detaylarına ulaşılır.
    public DecodedJWT decodeJWT(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(secret.getBytes()))
                    .build()
                    .verify(token);
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    //Http isteğinden token almayı sağlar.(Postman-post-Bearer eyfjsnks....)
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
