package the.postbooking.app.hateoas;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import postbookingapp.api.RestService;
import the.postbooking.app.controller.ServiceController;
import the.postbooking.app.entity.ServiceEntity;

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
public class ServiceRepresentationModelAssembler extends
      RepresentationModelAssemblerSupport<ServiceEntity, RestService> {
    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     */
    public ServiceRepresentationModelAssembler() {
        super(ServiceController.class, RestService.class);
    }

    /**
     * Coverts the Service entity to resource
     *
     * @param entity
     */
    @Override
    public RestService toModel(ServiceEntity entity) {
        String bid = Objects.nonNull(entity.getId()) ? entity.getId().toString() : null;
        RestService resource = new RestService();
        BeanUtils.copyProperties(entity, resource);
        resource.add(linkTo(methodOn(ServiceController.class).getServicesByBookingId(bid)).withRel("services"));
        return resource;
    }

    /**
     * Coverts the collection of Service entities to list of resources.
     *
     * @param entities
     */
    public List<RestService> toListModel(List<ServiceEntity> entities) {
        if(Objects.isNull(entities)) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
              .collect(toList());
    }
}
