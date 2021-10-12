package the.postbooking.app.service;

import postbookingapp.api.Waiter;
import the.postbooking.app.entity.WaiterEntity;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Emanuele Tattolo on 28/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
public interface WaiterService {
    public Waiter createWaiterForGivenRestaurant(@Valid Waiter waiter);
    public void deleteWaiterById(String waiterId);
    public List<WaiterEntity> getAllWaitersForGivenRestaurant();
}
