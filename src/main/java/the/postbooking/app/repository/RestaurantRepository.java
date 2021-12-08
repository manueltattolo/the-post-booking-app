package the.postbooking.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import the.postbooking.app.entity.RestaurantEntity;

import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 05/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, UUID> {

    RestaurantEntity findByRestName(String restName);
}
