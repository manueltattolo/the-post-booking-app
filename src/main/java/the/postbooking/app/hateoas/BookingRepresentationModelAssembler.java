package the.postbooking.app.hateoas;

import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import postbookingapp.api.Booking;
import the.postbooking.app.controller.BookingController;
import the.postbooking.app.entity.BookingEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * Created by Emanuele Tattolo on 02/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Component
public class BookingRepresentationModelAssembler extends
      RepresentationModelAssemblerSupport<BookingEntity, Booking> {
    final ZoneId zone = ZoneId.of("Europe/Paris");
    LocalDateTime localDateTime = LocalDateTime.now();
    ZoneOffset zoneOffSet = zone.getRules().getOffset(localDateTime);

    private UserRepresentationModelAssembler uAssembler;
    private RestaurantRepresentationModelAssembler rAssembler;

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     */
    public BookingRepresentationModelAssembler(@Lazy UserRepresentationModelAssembler uAssembler, @Lazy RestaurantRepresentationModelAssembler rAssembler) {
        super(BookingController.class, Booking.class);
        this.uAssembler = uAssembler;
        this.rAssembler = rAssembler;
    }

    /**
     * Coverts the Booking entity to resource
     *
     * @param entity
     */
    @Override
    public Booking toModel(BookingEntity entity) {
        String uid = Objects.nonNull(entity.getUser()) ? entity.getUser().getId().toString() : null;
        String bid = Objects.nonNull(entity.getId()) ? entity.getId().toString() : null;
        Booking resource = new Booking();
        resource.setUser(uAssembler.toModel(entity.getUser()));
        resource.setDate(entity.getDateTime().atOffset(zoneOffSet));
        resource.setRestaurant(rAssembler.toModel(entity.getRestaurant()));
        resource.setId(entity.getId().toString());
        resource.setSeatsNo(entity.getSeatsNo());

        resource.add(linkTo(methodOn(BookingController.class).getBookingByBookingId(bid)).withSelfRel());
        resource.add(linkTo(methodOn(BookingController.class).getBookingsByCustomerId(uid)).withSelfRel());
        resource.add(linkTo(methodOn(BookingController.class).getAllBookings()).withRel("bookings"));
        return resource;
    }

    /**
     * Coverts the collection of Product entities to list of resources.
     *
     * @param entities
     */
    public List<Booking> toListModel(List<BookingEntity> entities) {
        if(Objects.isNull(entities)) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
              .collect(toList());
    }
}
