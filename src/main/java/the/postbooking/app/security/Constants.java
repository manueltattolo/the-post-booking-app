package the.postbooking.app.security;

/**
 * Created by Emanuele Tattolo on 23/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
public class Constants {

    public static final String ENCODER_ID = "bcrypt";
    public static final String API_URL_PREFIX = "/api/v1/**";
    public static final String H2_URL_PREFIX = "/h2-console/**";
    public static final String SIGNUP_URL = "/api/v1/users";
    public static final String TOKEN_URL = "/api/v1/auth/token";
    public static final String REFRESH_URL = "/api/v1/auth/token/refresh";
    public static final String RESTAURANT_URL = "/api/v1/restaurants/**";
    public static final String SERVICES_URL = "/api/v1/services";
    public static final String BOOKINGS_URL = "/api/v1/booking";
    public static final String CUSTOMERS_URL = "/api/v1/customers";
    public static final String AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECRET_KEY = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final String ROLE_CLAIM = "roles";
    public static final String AUTHORITY_PREFIX = "ROLE_";

}