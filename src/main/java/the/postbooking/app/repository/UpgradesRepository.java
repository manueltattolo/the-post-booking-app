package the.postbooking.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import the.postbooking.app.entity.UpgradesEntity;

import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 05/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Repository
public interface UpgradesRepository extends JpaRepository<UpgradesEntity, UUID> {
    @Query(value = "select * from erest.upgrades inner join erest.service on erest.table.ID = :fromString", nativeQuery = true)
    UpgradesEntity findByServiceId(UUID fromString);
}
