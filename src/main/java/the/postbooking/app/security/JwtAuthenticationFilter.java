package the.postbooking.app.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import the.postbooking.app.entity.UserEntity;
import the.postbooking.app.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Emanuele Tattolo on 30/11/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Order(0)
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtManager jwtManager, UserService userService) {
        this.jwtManager = jwtManager;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = auth.split("\\s")[1]; // Bearer token

//        UserDetails user = User.builder().username("prova").password("pwd")
//              .authorities("USER")
//              .build();
//        String s = jwtManager.create(user);

        try {
            DecodedJWT jwt = jwtManager.verify(token);
            String username = jwt.getSubject();
            // load user from db
            UserEntity userFromDb = userService.findUserByUsername(username);
            request.setAttribute("PRINCIPAL", userFromDb);
            System.out.println();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        filterChain.doFilter(request, response);

    }
}//class