package the.postbooking.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import the.postbooking.app.entity.BookingEntity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 05/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
    @Query(value = "select * from erest.booking b join erest.user where user.id = :customerId", nativeQuery = true)
    List<BookingEntity> findByCustomerId(@Param("customerId") UUID customerId);
}
