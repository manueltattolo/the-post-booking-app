package the.postbooking.app.service;

import org.springframework.stereotype.Service;
import postbookingapp.api.Restaurant;
import the.postbooking.app.entity.RestaurantEntity;
import the.postbooking.app.exception.ResourceNotFoundException;
import the.postbooking.app.repository.RestaurantRepository;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Emanuele Tattolo on 15/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository repository;

    public RestaurantServiceImpl(RestaurantRepository repository) { this.repository = repository; }

//    @PostConstruct
//    private void setup() {
//        Restaurant restaurant = new Restaurant();
//
//        this.createRestaurant(restaurant);
//    }

    @Override
    public Restaurant createRestaurant(@Valid Restaurant restaurant) {
             if ((restaurant.getTablesNo()) == null) {
                 throw new ResourceNotFoundException("Invalid Tables number.");
             }
             repository.save(toEntity(restaurant));
             return restaurant;
    }

    @Override
    public List<RestaurantEntity> getAllRestaurants() {
        return repository.findAll();
    }

    @Override
    public RestaurantEntity toEntity(Restaurant restaurant) {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setTablesNo(restaurant.getTablesNo());
        entity.setHistory(restaurant.getHistory());
        entity.setAddress(restaurant.getAddress());
        entity.setCity(restaurant.getCity());
        entity.setEmail(restaurant.getEmail());
        entity.setPhone(restaurant.getPhone());
        return entity;
    }
}