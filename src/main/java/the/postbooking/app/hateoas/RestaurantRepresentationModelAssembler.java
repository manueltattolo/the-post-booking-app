package the.postbooking.app.hateoas;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import postbookingapp.api.Restaurant;
import the.postbooking.app.controller.RestaurantController;
import the.postbooking.app.entity.RestaurantEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by Emanuele Tattolo on 11/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@Component
public class RestaurantRepresentationModelAssembler extends RepresentationModelAssemblerSupport<RestaurantEntity, Restaurant> {

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     */
    public RestaurantRepresentationModelAssembler() {
        super(RestaurantController.class, Restaurant.class);
    }

    /**
     * Coverts the Restaurant entity to resource
     *
     * @param entity
     */
    @Override
    public Restaurant toModel(RestaurantEntity entity) {
        String rid = Objects.nonNull(entity.getId()) ? entity.getId().toString() : null;
        Restaurant resource = new Restaurant();
        BeanUtils.copyProperties(entity, resource);
        resource.add(linkTo(methodOn(RestaurantController.class).getAllRestaurant()).withRel("restaurants"));
        return resource;
    }

    /**
     * Coverts the collection of Restaurant entities to list of resources.
     *
     * @param entities
     */
    public List<Restaurant> toListModel(List<RestaurantEntity> entities) {
        if(Objects.isNull(entities)) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
              .collect(toList());
    }
}