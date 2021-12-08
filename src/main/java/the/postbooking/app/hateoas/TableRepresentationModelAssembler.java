package the.postbooking.app.hateoas;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import postbookingapp.api.RestTable;
import the.postbooking.app.controller.TableController;
import the.postbooking.app.entity.TableEntity;

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
public class TableRepresentationModelAssembler
      extends RepresentationModelAssemblerSupport<TableEntity, RestTable> {

    private UpgradesRepresentationModelAssembler uAssembler;
    private RestaurantRepresentationModelAssembler rAssembler;

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     */
    public TableRepresentationModelAssembler(UpgradesRepresentationModelAssembler uAssembler, RestaurantRepresentationModelAssembler rAssembler) {
        super(TableController.class, RestTable.class);
        this.uAssembler = uAssembler;
        this.rAssembler = rAssembler;
    }

    /**
     * Coverts the Table entity to resource
     *
     * @param entity
     */
    @Override
    public RestTable toModel(TableEntity entity) {
        String rid = Objects.nonNull(entity.getRestaurant()) ? entity.getRestaurant().getId().toString() : null;
        RestTable resource = new RestTable();
        resource.setId(entity.getId().toString());
        resource.setRestaurant(rAssembler.toModel(entity.getRestaurant()));
        resource.setName(entity.getName());
        resource.setTableSeats(entity.getTable_seats());
        resource.setUpgrades(uAssembler.toModel(entity.getUpgrades()));
        resource.add(linkTo(methodOn(TableController.class).getTablesByRestaurantId(rid)).withRel("tables"));
        return resource;
    }

    /**
     * Coverts the collection of Table entities to list of resources.
     *
     * @param entities
     */
    public List<RestTable> toListModel(List<TableEntity> entities) {
        if(Objects.isNull(entities)) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
              .collect(toList());
    }
}
