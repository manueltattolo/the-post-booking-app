package the.postbooking.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import the.postbooking.app.entity.TableEntity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 05/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Repository
public interface TableRepository extends JpaRepository<TableEntity, UUID> {
    @Query(value = "select * from erest.table inner join erest.user on erest.user.ID = :customerId", nativeQuery = true)
    List<TableEntity> findByCustomerId(@Param("customerId")UUID id);

    @Query(value = "select * from erest.table inner join erest.restaurant on erest.restaurant.ID = :fromString", nativeQuery = true)
    List<TableEntity> findByRestaurantId(UUID fromString);
}
