package the.postbooking.app.service;

import postbookingapp.api.Restaurant;
import the.postbooking.app.entity.RestaurantEntity;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Emanuele Tattolo on 15/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
public interface RestaurantService {
    public RestaurantEntity createRestaurant(@Valid Restaurant restaurant);
    public List<RestaurantEntity> getAllRestaurants();
    public RestaurantEntity toEntity(Restaurant restaurant);
}
