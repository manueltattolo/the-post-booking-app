package the.postbooking.app.hateoas;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import postbookingapp.api.Menu;
import the.postbooking.app.controller.MenuController;
import the.postbooking.app.entity.MenuEntity;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by Emanuele Tattolo on 12/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@Component
public class MenuRepresentationModelAssembler extends RepresentationModelAssemblerSupport<MenuEntity, Menu> {

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     * @param controllerClass must not be {@literal null}.
     * @param resourceType    must not be {@literal null}.
     */
    public MenuRepresentationModelAssembler(Class<?> controllerClass, Class<Menu> resourceType) {
        super(controllerClass, resourceType);
    }

    /**
     * Coverts the Menu entity to resource
     *
     * @param entity
     */
    @Override
    public Menu toModel(MenuEntity entity) {
        String rid = Objects.nonNull(entity.getRestaurant()) ? entity.getRestaurant().getId().toString() : null;
        Menu resource = new Menu();
        BeanUtils.copyProperties(entity, resource);
        resource.add(linkTo(methodOn(MenuController.class).getMenuByRestaurantId(rid)).withSelfRel());
        return resource;
    }
}
