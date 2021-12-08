package the.postbooking.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import postbookingapp.api.RestService;
import postbookingapp.api.ServiceApi;
import the.postbooking.app.hateoas.ServiceRepresentationModelAssembler;
import the.postbooking.app.service.ServiceService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by Emanuele Tattolo on 08/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@RestController
public class ServiceController implements ServiceApi {

    private ServiceService ser;
    private final ServiceRepresentationModelAssembler assembler;

    public ServiceController(ServiceService ser, ServiceRepresentationModelAssembler assembler) {
        this.ser = ser;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<List<RestService>> addOrReplaceServicesByBookingId(String bookingId, @Valid RestService service) {
        return ok(ser.addOrReplaceServicesByBookingId(bookingId, service));
    }

    @Override
    public ResponseEntity<List<RestService>> addServicesByBookingId(String bookingId, @Valid RestService service) {
        return ok(ser.addServicesByBookingId(bookingId, service));
    }

    @Override
    public ResponseEntity<Void> deleteServicesByBookingId(String bookingId) {
        ser.deleteServicesByBookingId(bookingId);
        return accepted().build();
    }

    @Override
    public ResponseEntity<List<RestService>> getServicesByBookingId(String bookingId) {
        return ok(assembler.toListModel(ser.getServicesByBookingId(bookingId)));
    }

    @Override
    public ResponseEntity<List<RestService>> getServicesByGivenTime(OffsetDateTime date) {
        return ok(assembler.toListModel(ser.getServicesByDateTime(Timestamp.valueOf(date.toLocalDateTime()))));
    }
}