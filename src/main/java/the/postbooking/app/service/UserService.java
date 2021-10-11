package the.postbooking.app.service;

import postbookingapp.api.User;
import the.postbooking.app.entity.UserEntity;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Emanuele Tattolo on 14/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
public interface UserService {

    public User createCustomer(@Valid User user);

    public void deleteCustomerById(String customerId);

    public List<UserEntity> getAllCustomers();

    public UserEntity getCustomerById(String id);

    public UserEntity toEntity(User user);
}
