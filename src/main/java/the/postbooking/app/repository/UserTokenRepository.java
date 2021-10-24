package the.postbooking.app.repository;

import org.springframework.data.repository.CrudRepository;
import the.postbooking.app.entity.UserTokenEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 23/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
public interface UserTokenRepository extends CrudRepository<UserTokenEntity, UUID> {

    Optional<UserTokenEntity> findByRefreshToken(String refreshToken);
    Optional<UserTokenEntity> deleteByUserId(UUID userId);

}