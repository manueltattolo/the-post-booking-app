package the.postbooking.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import postbookingapp.api.Booking;
import postbookingapp.api.BookingApi;
import the.postbooking.app.entity.BookingEntity;
import the.postbooking.app.hateoas.BookingRepresentationModelAssembler;
import the.postbooking.app.service.BookingService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by Emanuele Tattolo on 23/08/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@RestController
public class BookingController implements BookingApi {

    private BookingService service;

    private BookingRepresentationModelAssembler assembler;

    public BookingController(BookingService service, BookingRepresentationModelAssembler assembler) {
        this.assembler = assembler;
        this.service = service;
    }

    @Override
    public ResponseEntity<Booking> addBooking(@Valid Booking booking) {
        return ok(service.addBooking(booking));
    }

    @Override
    public ResponseEntity<Void> deleteBooking(String customerId) {
        service.deleteBooking(customerId);
        return accepted().build();
    }

    @Override
    public ResponseEntity<Booking> getBookingByBookingId(String bookingId) {
        return ok(assembler.toModel(service.getBookingByBookingId(bookingId)));
    }

    @Override
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ok(assembler.toListModel(service.getAllBookings()));
    }

    @Override
    public ResponseEntity<List<Booking>> getBookingsByCustomerId(String customerId) {
        return ok(assembler.toListModel((List<BookingEntity>) service.getBookingsByCustomerId(customerId)));
    }

    @Override
    public ResponseEntity<Void> modifyBooking(String bookingId, @Valid Booking booking) {
        service.modifyBooking(bookingId, booking);
        return accepted().build();
    }

}