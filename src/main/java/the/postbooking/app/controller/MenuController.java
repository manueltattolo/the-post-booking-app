package the.postbooking.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import postbookingapp.api.Menu;
import postbookingapp.api.MenuApi;
import the.postbooking.app.hateoas.MenuRepresentationModelAssembler;
import the.postbooking.app.service.MenuService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by Emanuele Tattolo on 08/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@RestController
public class MenuController implements MenuApi {

    private MenuService service;
    private final MenuRepresentationModelAssembler assembler;

    public MenuController(MenuService service, MenuRepresentationModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<List<Menu>> addMenuByRestaurantId(String restaurantId, @Valid Menu menu) {
        return ok(service.addMenuByRestaurantId(restaurantId, menu));
    }

    @Override
    public ResponseEntity<Void> deleteMenuByRestaurantId(String restaurantId) {
        service.deleteMenuByRestaurantId(restaurantId);
        return accepted().build();
    }

    @Override
    public ResponseEntity<Menu> getMenuByRestaurantId(String restaurantId) {
        return ok(assembler.toModel(service.getMenuByRestaurantId(restaurantId)));
    }

    @Override
    public ResponseEntity<List<Menu>> replaceMenuByRestaurantId(String restaurantId, @Valid Menu menu) {
        return ok(service.replaceMenuByRestaurantId(restaurantId, menu));
    }

}