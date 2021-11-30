package the.postbooking.app.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postbookingapp.api.RefreshToken;
import postbookingapp.api.SignedInUser;
import postbookingapp.api.User;
import the.postbooking.app.entity.UserEntity;
import the.postbooking.app.entity.UserTokenEntity;
import the.postbooking.app.exception.GenericAlreadyExistsException;
import the.postbooking.app.exception.InvalidRefreshTokenException;
import the.postbooking.app.exception.ResourceNotFoundException;
import the.postbooking.app.repository.UserRepository;
import the.postbooking.app.repository.UserTokenRepository;
import the.postbooking.app.security.JwtManager;

import javax.validation.Valid;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Emanuele Tattolo on 14/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtManager tokenManager;


//    public UserServiceImpl(UserRepository repository, UserTokenRepository userTokenRepository,
//                           PasswordEncoder bCryptPasswordEncoder, JwtManager tokenManager) {
//        this.repository = repository;
//        this.userTokenRepository = userTokenRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.tokenManager = tokenManager;
//    }

    @Override
    public User createCustomer(@Valid User user) {
        if (Strings.isEmpty(user.getEmail())) {
            throw new ResourceNotFoundException("Invalid email.");
        }
        if (Strings.isEmpty(user.getUsername())) {
            throw new ResourceNotFoundException("Invalid username.");
        }
        if (Strings.isEmpty(user.getPassword())) {
            throw new ResourceNotFoundException("Invalid password.");
        }
        if (Strings.isEmpty(user.getPhone())) {
            throw new ResourceNotFoundException("Invalid phone.");
        }
        if (Strings.isEmpty(user.getLastname())) {
            throw new ResourceNotFoundException("Invalid last name.");
        }
        repository.save(toEntity(user));
        return user;
    }

    @Override
    public void deleteCustomerById(String customerId) {
        repository.deleteById(UUID.fromString(customerId));
    }

    @Override
    public List<UserEntity> getAllCustomers() {
        Iterable<UserEntity> allCustomers = repository.findAll();
        return StreamSupport.stream(allCustomers.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public UserEntity getCustomerById(String id) {
        return repository.findById(UUID.fromString(id)).get();
    }

    @Override
    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(UUID.fromString(user.getId()));
        entity.setEmail(user.getEmail());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setPhone(user.getPhone());
        entity.setFirstName(user.getFirstname());
        entity.setLastName(user.getLastname());
        return entity;
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        if (Strings.isBlank(username)) {
            throw new UsernameNotFoundException("Invalid user.");
        }
        final String uname = username.trim();
        Optional<UserEntity> oUserEntity =  repository.findByUsername(uname);
        UserEntity userEntity = oUserEntity.orElseThrow(() ->
              new UsernameNotFoundException(
                    String.format("Given user(%s) not found.",                               uname)));
        return userEntity;    }

    @Override
    @Transactional
    public Optional<SignedInUser> createUser(User user) {
        Integer count = repository.findByUsernameOrEmail(
              user.getUsername(), user.getEmail());
        if (count > 0) {
            throw new GenericAlreadyExistsException("Use different username and email.");
        }
        UserEntity userEntity = repository.save(toEntity(user));
        return Optional.of(createSignedUserWithRefreshToken(userEntity));
    }

    private SignedInUser createSignedUserWithRefreshToken(UserEntity userEntity) {
        return createSignedInUser(userEntity).refreshToken(createRefreshToken(userEntity));
    }

    private SignedInUser createSignedInUser(UserEntity userEntity) {
        String token = tokenManager.create(
              org.springframework.security.core.userdetails.User.
                    builder()
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .authorities(Objects.nonNull(userEntity.getRole()) ?
                          userEntity.getRole().name() : "")
                    .build());
        return new SignedInUser().username(
              userEntity.getUsername())
              .accessToken(token)
              .userId(userEntity.getId().toString());
    }

    private String createRefreshToken(UserEntity user) {
        String token = RandomHolder.randomKey(128);
        userTokenRepository.save(new UserTokenEntity().setRefreshToken(token).setUser(user));
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    // https://stackoverflow.com/a/31214709/109354
    // or can use org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(n)
    private static class RandomHolder {
        static final Random random = new SecureRandom();
        public static String randomKey(int length) {
            return String.format("%" + length + "s", new BigInteger(length * 5/*base 32,2^5*/, random).toString(32)).replace('\u0020', '0');
        }
    }

    @Override
    @Transactional
    public SignedInUser getSignedInUser(UserEntity userEntity) {
        userTokenRepository.deleteByUserId(userEntity.getId());
        return createSignedUserWithRefreshToken(userEntity);
    }

    @Override
    public Optional<SignedInUser> getAccessToken(RefreshToken refreshToken) {
        return userTokenRepository
              .findByRefreshToken(refreshToken.getRefreshToken())
              .map(ut -> Optional.of(createSignedInUser(ut.getUser())
                    .refreshToken(refreshToken.getRefreshToken())))
              .orElseThrow(() -> new InvalidRefreshTokenException("Invalid token."));
    }

    @Override
    public void removeRefreshToken(RefreshToken refreshToken) {
        userTokenRepository.findByRefreshToken(refreshToken.getRefreshToken()).
            ifPresentOrElse(userTokenRepository::delete, () -> {
             throw new InvalidRefreshTokenException("Invalid token.");
            });
    }
}