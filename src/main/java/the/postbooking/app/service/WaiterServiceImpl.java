package the.postbooking.app.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import postbookingapp.api.Waiter;
import the.postbooking.app.entity.WaiterEntity;
import the.postbooking.app.exception.GenericAlreadyExistsException;
import the.postbooking.app.exception.ResourceNotFoundException;
import the.postbooking.app.repository.RestaurantRepository;
import the.postbooking.app.repository.WaiterRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 28/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Service
public class WaiterServiceImpl implements WaiterService {

    private WaiterRepository repository;
    private RestaurantRepository restRep;

    public WaiterServiceImpl(WaiterRepository repository, RestaurantRepository restRep) {
        this.repository = repository;
        this.restRep = restRep;
    }

    @Override
    public Waiter createWaiterForGivenRestaurant(@Valid Waiter waiter) {
        if (Strings.isEmpty(waiter.getFullname())) {
            throw new ResourceNotFoundException("Invalid name.");
        }
        if (Objects.isNull(waiter.getRestaurant())) {
            throw new ResourceNotFoundException("Invalid restaurant id.");
        }
        if (Objects.nonNull(repository.findByFullname(toEntity(waiter).getFullname()))) {
            throw new GenericAlreadyExistsException("Waiter already in the system.");
        }
        repository.save(toEntity(waiter));
        return waiter;
    }

    @Override
    public void deleteWaiterById(String waiterId) {
        WaiterEntity entity = repository.getOne(UUID.fromString(waiterId));
        if (Objects.isNull(entity)) {
            throw new ResourceNotFoundException("Invalid waiter id.");
        }
        repository.delete(entity);
    }

    @Override
    public List<WaiterEntity> getAllWaitersForGivenRestaurant() {
        return repository.findAll();
    }

    public WaiterEntity toEntity(Waiter waiter) {
        WaiterEntity entity = new WaiterEntity();
        entity.setFullname(waiter.getFullname());
        entity.setRestaurant(restRep.getOne(UUID.fromString(waiter.getRestaurant().getId())));
        return entity;
    }
}