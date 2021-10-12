package the.postbooking.app.hateoas;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import postbookingapp.api.Waiter;
import the.postbooking.app.controller.WaiterController;
import the.postbooking.app.entity.WaiterEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by Emanuele Tattolo on 12/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@Component
public class WaiterRepresentationModelAssembler extends RepresentationModelAssemblerSupport<WaiterEntity, Waiter> {

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     * @param controllerClass must not be {@literal null}.
     * @param resourceType    must not be {@literal null}.
     */
    public WaiterRepresentationModelAssembler(Class<?> controllerClass, Class<Waiter> resourceType) {
        super(controllerClass, resourceType);
    }

    /**
     * Coverts the Waiter entity to resource
     *
     * @param entity
     */
    @Override
    public Waiter toModel(WaiterEntity entity) {
        Waiter resource = new Waiter();
        BeanUtils.copyProperties(entity, resource);
        resource.add(linkTo(methodOn(WaiterController.class).getAllWaitersForGivenRestaurant()).withRel("waiters"));
        return resource;
    }

    /**
     * Coverts the collection of Product entities to list of resources.
     *
     * @param entities
     */
    public List<Waiter> toListModel(List<WaiterEntity> entities) {
        if(Objects.isNull(entities)) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
              .collect(toList());
    }
}