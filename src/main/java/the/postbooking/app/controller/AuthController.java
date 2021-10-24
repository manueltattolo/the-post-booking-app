package the.postbooking.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import postbookingapp.api.*;
import the.postbooking.app.entity.UserEntity;
import the.postbooking.app.exception.InvalidRefreshTokenException;
import the.postbooking.app.service.UserService;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.*;



/**
 * Created by Emanuele Tattolo on 24/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@RestController
public class AuthController implements UserApi {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<SignedInUser> signIn(@Valid SignInReq signInReq) {
        UserEntity userEntity = service.findUserByUsername(signInReq.getUsername());
        if (passwordEncoder.matches(
              signInReq.getPassword(),
              userEntity.getPassword())) {
            return ok(service.getSignedInUser(userEntity));
        }
        throw new InsufficientAuthenticationException("Unauthorized.");
    }

    @Override
    public ResponseEntity<Void> signOut(@Valid RefreshToken refreshToken) {
        service.removeRefreshToken(refreshToken);
        return accepted().build();
    }

    @Override
    public ResponseEntity<SignedInUser> signUp(@Valid User user) {
        return status(HttpStatus.CREATED).body(service.createUser(user).get());
    }

    @Override
    public ResponseEntity<SignedInUser> getAccessToken(@Valid RefreshToken refreshToken) {
        return ok(service.getAccessToken(refreshToken)
              .orElseThrow(InvalidRefreshTokenException::new));
    }

}