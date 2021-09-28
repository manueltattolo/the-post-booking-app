package the.postbooking.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import the.postbooking.app.entity.ServiceEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 05/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {
    @Query(value = "select * from erest.service inner join erest.booking on erest.booking.ID = :bookingId", nativeQuery = true)
    List<ServiceEntity> findByBookingId(@Param("bookingId")UUID id);

    @Query(value = "select * from erest.service inner join erest.booking on erest.booking.booking_date = :booking_date", nativeQuery = true)
    List<ServiceEntity> getServicesByDateTime(@Param("booking_date")Timestamp dateTime);
}
