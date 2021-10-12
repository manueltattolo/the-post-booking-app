package the.postbooking.app.service;

import org.springframework.stereotype.Service;
import postbookingapp.api.Menu;
import the.postbooking.app.entity.MenuEntity;
import the.postbooking.app.exception.ResourceNotFoundException;
import the.postbooking.app.repository.MenuRepository;
import the.postbooking.app.repository.RestaurantRepository;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 28/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Service
public class MenuServiceImpl implements MenuService {

    private MenuRepository repository;
    private RestaurantRepository restRep;

    public MenuServiceImpl(MenuRepository repository, RestaurantRepository restRep) {
        this.repository = repository;
        this.restRep = restRep;
    }

    @Override
    public List<Menu> addMenuByRestaurantId(String restaurantId, @Valid Menu menu) {
        if (Objects.isNull(menu.getRestaurant())) {
            throw new ResourceNotFoundException("Invalid restaurant id.");
        }
        repository.save(toEntity(menu));
        return Collections.singletonList(menu);
    }

    @Override
    public MenuEntity getMenuByRestaurantId(String restaurantId) {
        return repository.findByRestaurantId(UUID.fromString(restaurantId));
    }

    @Override
    public void deleteMenuByRestaurantId(String restaurantId) {
        repository.delete(getMenuByRestaurantId(restaurantId));
    }

    @Override
    public List<Menu> replaceMenuByRestaurantId(String restaurantId, @Valid Menu menu) {
        MenuEntity ori = getMenuByRestaurantId(restaurantId);
        MenuEntity mod = toEntity(menu);
        ori.setUnitPrice(mod.getUnitPrice());
        ori.setQuantity(mod.getQuantity());
        ori.setDrink(mod.getDrink());
        ori.setFood(mod.getFood());
        ori.setUnitPrice(mod.getUnitPrice());
        repository.save(ori);
        return Collections.singletonList(menu);
    }

    @Override
    public MenuEntity toEntity(Menu menu) {
        MenuEntity entity = new MenuEntity();
        entity.setRestaurant(restRep.getOne(UUID.fromString(menu.getRestaurant().getId())));
        entity.setDrink(menu.getDrink());
        entity.setFood(menu.getFood());
        entity.setQuantity(menu.getQuantity());
        entity.setUnitPrice(menu.getUnitPrice().doubleValue());
        return entity;
    }
}
