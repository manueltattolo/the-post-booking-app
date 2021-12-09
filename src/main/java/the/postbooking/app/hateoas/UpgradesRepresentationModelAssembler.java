package the.postbooking.app.hateoas;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import postbookingapp.api.Upgrades;
import the.postbooking.app.controller.UpgradesController;
import the.postbooking.app.entity.UpgradesEntity;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by Emanuele Tattolo on 12/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@Component
public class UpgradesRepresentationModelAssembler extends RepresentationModelAssemblerSupport<UpgradesEntity, Upgrades> {

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     */
    public UpgradesRepresentationModelAssembler() {
        super(UpgradesController.class, Upgrades.class);
    }

    /**
     * Coverts the Upgrades entity to resource
     *
     * @param entity
     */
    @Override
    public Upgrades toModel(UpgradesEntity entity) {
        if (Objects.nonNull(entity)) {
            String sid = Objects.nonNull(entity.getService()) ? entity.getService().getId().toString() : null;
            Upgrades resource = new Upgrades();
            BeanUtils.copyProperties(entity, resource);
            resource.add(linkTo(methodOn(UpgradesController.class).getUpgradesByServiceId(sid)).withSelfRel());
            return resource;
        } else {
            return null;
        }
    }
}