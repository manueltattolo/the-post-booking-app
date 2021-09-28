package the.postbooking.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import the.postbooking.app.entity.WaiterEntity;

import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 05/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Repository
public interface WaiterRepository extends JpaRepository<WaiterEntity, UUID> {
    Object findByFullname(String fullname);
}
