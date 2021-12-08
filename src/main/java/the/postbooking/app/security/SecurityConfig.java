package the.postbooking.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static the.postbooking.app.security.Constants.*;

/**
 * Created by Emanuele Tattolo on 23/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private UserDetailsService userService;
    private PasswordEncoder bCryptPasswordEncoder;
    private ObjectMapper mapper;

    /*@Value("${app.security.jwt.keystore-location}")
    private String keyStorePath;
    @Value("${app.security.jwt.keystore-password}")
    private String keyStorePassword;
    @Value("${app.security.jwt.key-alias}")
    private String keyAlias;
    @Value("${app.security.jwt.private-key-passphrase}")
    private String privateKeyPassphrase;*/

    public SecurityConfig(UserDetailsService userService,
                          PasswordEncoder bCryptPasswordEncoder, ObjectMapper mapper) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable().formLogin().disable()
              .csrf().ignoringAntMatchers(API_URL_PREFIX, H2_URL_PREFIX)
              .and()
              .headers().frameOptions().sameOrigin() // for H2 Console
              .and()
              .cors()
              .and()
              .authorizeRequests()
              .antMatchers(HttpMethod.GET, RESTAURANT_URL).permitAll()
              .antMatchers(HttpMethod.POST, RESTAURANT_URL).permitAll()
              .antMatchers(HttpMethod.POST, TOKEN_URL).permitAll()
              .antMatchers(HttpMethod.DELETE, TOKEN_URL).permitAll()
              .antMatchers(HttpMethod.POST, SIGNUP_URL).permitAll()
              .antMatchers(HttpMethod.POST, REFRESH_URL).permitAll()
              .antMatchers(HttpMethod.GET, SERVICES_URL).permitAll()
              .antMatchers(HttpMethod.POST, SERVICES_URL).permitAll()
              .antMatchers(HttpMethod.GET, BOOKINGS_URL).permitAll()
              .antMatchers(HttpMethod.POST, BOOKINGS_URL).permitAll()
              .antMatchers(HttpMethod.GET, CUSTOMERS_URL).permitAll()
              .antMatchers(HttpMethod.POST, CUSTOMERS_URL).permitAll()
//              .anyRequest().authenticated()
//              .mvcMatchers(HttpMethod.POST, "/api/v1/restaurants**")
//              .hasAuthority(RoleEnum.ADMIN.getAuthority())
              .and()
              /* Filter based security configuration
              .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
              .and()
              .httpBasic()
              .authenticationEntryPoint(authenticationEntryPoint)
              .and()
              .addFilterBefore(failureHandler , BearerTokenAuthenticationFilter.class)
              .addFilter(new LoginFilter(super.authenticationManager(), mapper))
              */
//                .addFilterBefore(new JwtAuthenticationFilter(new JwtManager(), (UserService) userService), UsernamePasswordAuthenticationFilter.class)
//              .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
//                    jwt -> jwt.jwtAuthenticationConverter(getJwtAuthenticationConverter())))
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

//    private Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter authorityConverter = new JwtGrantedAuthoritiesConverter();
//        authorityConverter.setAuthorityPrefix(AUTHORITY_PREFIX);
//        authorityConverter.setAuthoritiesClaimName(ROLE_CLAIM);
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(authorityConverter);
//        return converter;
//    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        //configuration.setAllowCredentials(true);
        // For CORS response headers
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    public KeyStore keyStore() {
//        try {
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
//                  .getResourceAsStream(keyStorePath);
//            keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
//            return keyStore;
//        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
//            LOG.error("Unable to load keystore: {}", keyStorePath, e);
//        }
//        throw new IllegalArgumentException("Unable to load keystore");
//    }
//
//    @Bean
//    public RSAPrivateKey jwtSigningKey(KeyStore keyStore) {
//        try {
//            Key key = keyStore.getKey(keyAlias, privateKeyPassphrase.toCharArray());
//            if (key instanceof RSAPrivateKey) {
//                return (RSAPrivateKey) key;
//            }
//        } catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
//            LOG.error("Unable to load private key from keystore: {}", keyStorePath, e);
//        }
//        throw new IllegalArgumentException("Unable to load private key");
//    }
//
//    @Bean
//    public RSAPublicKey jwtValidationKey(KeyStore keyStore) {
//        try {
//            Certificate certificate = keyStore.getCertificate(keyAlias);
//            PublicKey publicKey = certificate.getPublicKey();
//            if (publicKey instanceof RSAPublicKey) {
//                return (RSAPublicKey) publicKey;
//            }
//        } catch (KeyStoreException e) {
//            LOG.error("Unable to load private key from keystore: {}", keyStorePath, e);
//        }
//        throw new IllegalArgumentException("Unable to load RSA public key");
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder(RSAPublicKey rsaPublicKey) {
//        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
//    }
}

