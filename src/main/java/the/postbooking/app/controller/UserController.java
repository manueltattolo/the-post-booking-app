package the.postbooking.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import postbookingapp.api.CustomerApi;
import postbookingapp.api.User;
import the.postbooking.app.hateoas.UserRepresentationModelAssembler;
import the.postbooking.app.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by Emanuele Tattolo on 08/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@RestController
public class UserController implements CustomerApi {

    private UserService service;
    private final UserRepresentationModelAssembler assembler;

    public UserController(UserService service, UserRepresentationModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<User> createCustomer(@Valid User user) {
        return ok(service.createCustomer(user));
    }

    @Override
    public ResponseEntity<Void> deleteCustomerById(String customerId) {
        service.deleteCustomerById(customerId);
        return accepted().build();
    }

    @Override
    public ResponseEntity<List<User>> getAllCustomers() {
        return ok(assembler.toListModel(service.getAllCustomers()));
    }

    @Override
    public ResponseEntity<User> getCustomerById(String customerId) {
        return ok(assembler.toModel(service.getCustomerById(customerId)));
    }

}
