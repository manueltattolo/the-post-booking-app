package the.postbooking.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import the.postbooking.app.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 05/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    @Query(value = "select count(u.*) from erest.user u where u.username = :username or u.email = :email", nativeQuery = true)
    Integer findByUsernameOrEmail(String username, String email);
}
