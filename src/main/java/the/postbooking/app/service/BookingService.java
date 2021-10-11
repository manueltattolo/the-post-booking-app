package the.postbooking.app.service;

import postbookingapp.api.Booking;
import the.postbooking.app.entity.BookingEntity;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Emanuele Tattolo on 10/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
public interface BookingService {

    public Booking addBooking(@Valid Booking booking);

    public void deleteBooking(String customerId);

    public Iterable<BookingEntity> getBookingsByCustomerId(String customerId);

    public List<BookingEntity> getAllBookings();

    public BookingEntity getBookingByBookingId(String id);

    public BookingEntity modifyBooking(String id, @Valid Booking booking);

    public BookingEntity toEntity(Booking booking);


}