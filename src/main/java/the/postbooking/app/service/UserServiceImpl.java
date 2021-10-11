package the.postbooking.app.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import postbookingapp.api.User;
import the.postbooking.app.entity.UserEntity;
import the.postbooking.app.exception.ResourceNotFoundException;
import the.postbooking.app.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 14/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createCustomer(@Valid User user) {
        if (Strings.isEmpty(user.getEmail())) {
            throw new ResourceNotFoundException("Invalid email.");
        }
        if (Strings.isEmpty(user.getUsername())) {
            throw new ResourceNotFoundException("Invalid username.");
        }
        if (Strings.isEmpty(user.getPassword())) {
            throw new ResourceNotFoundException("Invalid password.");
        }
        if (Strings.isEmpty(user.getPhone())) {
            throw new ResourceNotFoundException("Invalid phone.");
        }
        if (Strings.isEmpty(user.getLastname())) {
            throw new ResourceNotFoundException("Invalid last name.");
        }
        repository.save(toEntity(user));
        return user;
    }

    @Override
    public void deleteCustomerById(String customerId) {
        repository.deleteById(UUID.fromString(customerId));
    }

    @Override
    public List<UserEntity> getAllCustomers() {
        return repository.findAll();
    }

    @Override
    public UserEntity getCustomerById(String id) {
        return repository.findById(UUID.fromString(id)).get();
    }

    @Override
    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(UUID.fromString(user.getId()));
        entity.setEmail(user.getEmail());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setPhone(user.getPhone());
        entity.setFirstName(user.getFirstname());
        entity.setLastName(user.getLastname());
        return entity;
    }
}