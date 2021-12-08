package the.postbooking.app.hateoas;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import postbookingapp.api.User;
import the.postbooking.app.controller.UserController;
import the.postbooking.app.entity.UserEntity;
import the.postbooking.app.repository.BookingRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by Emanuele Tattolo on 10/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@Component
public class UserRepresentationModelAssembler extends RepresentationModelAssemblerSupport<UserEntity, User> {

    private BookingRepresentationModelAssembler bAssembler;
    private BookingRepository bRepo;

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     */
    public UserRepresentationModelAssembler(BookingRepresentationModelAssembler bAssembler, BookingRepository bRepo) {
        super(UserController.class, User.class);
        this.bAssembler = bAssembler;
        this.bRepo = bRepo;
    }

    /**
     * Coverts the User entity to resource
     *
     * @param entity
     */
    @Override
    public User toModel(UserEntity entity) {
        User resource = createModelWithId(entity.getId(), entity);
        resource.setId(entity.getId().toString());
        resource.setUsername(entity.getUsername());
        resource.setPassword(entity.getPassword());
        //resource.setBookings(bAssembler.toListModel(bRepo.findByCustomerId(entity.getId())));
        resource.setFirstname(entity.getFirstName());
        resource.setLastname(entity.getLastName());
        resource.setEmail(entity.getEmail());
        resource.setPhone(entity.getPhone());

        resource.add(
              linkTo(methodOn(UserController.class).getCustomerById(entity.getId().toString()))
                    .withSelfRel());
        resource.add(
              linkTo(methodOn(UserController.class).getAllCustomers()).withRel("customers"));
        return resource;
    }

    /**
     * Coverts the collection of User entities to list of resources.
     *
     * @param entities
     */
    public List<User> toListModel(List<UserEntity> entities) {
        if(Objects.isNull(entities)) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
              .collect(toList());
    }
}