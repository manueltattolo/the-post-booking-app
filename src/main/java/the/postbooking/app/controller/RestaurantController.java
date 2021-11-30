package the.postbooking.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import postbookingapp.api.Restaurant;
import postbookingapp.api.RestaurantApi;
import the.postbooking.app.hateoas.RestaurantRepresentationModelAssembler;
import the.postbooking.app.service.RestaurantService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


/**
 * Created by Emanuele Tattolo on 08/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@RestController
public class RestaurantController implements RestaurantApi {

    private RestaurantService service;
    private final RestaurantRepresentationModelAssembler assembler;

    public RestaurantController(RestaurantService service, RestaurantRepresentationModelAssembler assembler) {
        this.assembler = assembler;
        this.service = service;
    }

    @Override
    //    @RequestAttribute UserEntity user
    public ResponseEntity<Restaurant> createRestaurant(@Valid Restaurant restaurant) {
        return ok(service.createRestaurant(restaurant));
    }

    @Override
    public ResponseEntity<List<Restaurant>> getAllRestaurant() {
        return ok(assembler.toListModel(service.getAllRestaurants()));
    }

}