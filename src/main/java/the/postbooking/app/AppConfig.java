package the.postbooking.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import java.util.Map;
import static the.postbooking.app.security.Constants.ENCODER_ID;


/**
 * Created by Emanuele Tattolo on 02/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Configuration
public class AppConfig {

  @Bean
  public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
    return new ShallowEtagHeaderFilter();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    // Supports other password encoding, a must for existing applications.
    // However, uses BCrypt for new passwords. This will allow to use new or future encoders
    Map<String, PasswordEncoder> encoders = Map.of(
          ENCODER_ID, new BCryptPasswordEncoder(),
          "pbkdf2", new Pbkdf2PasswordEncoder(),
          "scrypt", new SCryptPasswordEncoder());
    return new DelegatingPasswordEncoder(ENCODER_ID, encoders);
  }

}