package the.postbooking.app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import static java.util.stream.Collectors.toList;
import static the.postbooking.app.security.Constants.EXPIRATION_TIME;
import static the.postbooking.app.security.Constants.ROLE_CLAIM;

/**
 * Created by Emanuele Tattolo on 23/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Component
public class JwtManager {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JwtManager(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String create(UserDetails principal) {
        final long now = System.currentTimeMillis();
        return JWT.create()
              .withIssuer("The Post Booking-App")
              .withSubject(principal.getUsername())
              .withClaim(ROLE_CLAIM,
                    principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                          .collect(toList()))
              .withIssuedAt(new Date(now))
              .withExpiresAt(new Date(now + EXPIRATION_TIME))
              //.sign(Algorithm.HMAC512(SECRET_KEY.getBytes(StandardCharsets.UTF_8)));
              .sign(Algorithm.RSA256(publicKey, privateKey));
    }
}
