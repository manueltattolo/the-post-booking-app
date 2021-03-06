package the.postbooking.app.service;

import postbookingapp.api.Menu;
import the.postbooking.app.entity.MenuEntity;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Emanuele Tattolo on 28/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
public interface MenuService {
    public List<Menu> addMenuByRestaurantId(String restaurantId, @Valid Menu menu);
    public MenuEntity getMenuByRestaurantId(String restaurantId);
    public void deleteMenuByRestaurantId(String restaurantId);
    public List<Menu> replaceMenuByRestaurantId(String restaurantId, @Valid Menu menu);
    public MenuEntity toEntity(Menu menu);
}
