package the.postbooking.app.service;

import org.springframework.stereotype.Service;
import postbookingapp.api.Booking;
import the.postbooking.app.entity.BookingEntity;
import the.postbooking.app.entity.ServiceEntity;
import the.postbooking.app.entity.TableEntity;
import the.postbooking.app.exception.CustomerNotFoundException;
import the.postbooking.app.exception.ResourceNotFoundException;
import the.postbooking.app.repository.BookingRepository;
import the.postbooking.app.repository.UserRepository;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Emanuele Tattolo on 10/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository repository;
    private UserRepository userRepo;
    private TableService tableSer;
    private ServiceService serviSer;

    public BookingServiceImpl(BookingRepository repository, UserRepository userRepo, TableService tableSer, ServiceService serviSer) {
        this.repository = repository;
        this.userRepo = userRepo;
        this.tableSer = tableSer;
        this.serviSer = serviSer;
    }

    @Override
    public BookingEntity addBooking(@Valid Booking booking) {
        if (Objects.isNull(booking.getUser())) {
            throw new ResourceNotFoundException("Invalid customer id.");
        }
        if (Objects.isNull(booking.getSeatsNo())) {
            throw new ResourceNotFoundException("Invalid seats number.");
        }
        if (Objects.isNull(booking.getDate().toLocalDateTime())) {
            throw new ResourceNotFoundException("Invalid date.");
        }

        //verification that restaurant has tables available for that time.
        BookingEntity newBooking = toEntity(booking);
        //calculating tables booked at given time by getting the services and the tables from the services
        List<TableEntity> tablesBookedAtTime = serviSer.getServicesByDateTime(Timestamp.valueOf(newBooking.getDateTime()))
              .stream().map(ServiceEntity::getRest_table).collect(Collectors.toList());
        //calculating tables available by getting the total tables in the restaurant and removing the booked ones
        List<TableEntity> tablesAv = tableSer.getTablesByRestaurantId(newBooking.getUser().getRestaurant().getId().toString());
        tablesAv.removeAll(tablesBookedAtTime);

        if (tablesAv.size() == 0) {
            throw new ResourceNotFoundException("No table available for this time");
        }
        if (tablesAv.stream().mapToInt(t -> t.getTable_seats()).sum() < newBooking.getSeatsNo()) {
            throw new ResourceNotFoundException("No table available for this number of guests");
        }
        //saving the booking
        repository.save(newBooking);

        //adding services and tables to the booking
        long count = tablesAv.stream().filter(x -> x.getTable_seats() == newBooking.getSeatsNo()).count();
        //if table for the exact number of guests is available
        if (count >= 1) {
            TableEntity table = tablesAv.stream().filter(x -> x.getTable_seats() == newBooking.getSeatsNo()).findFirst().get();
            serviSer.addServiceByBookingId(newBooking.getId().toString(), table);
        }
        //if table is not matching the number of guesting
        if (count == 0) {
            TableEntity table = tablesAv.stream().filter(x -> x.getTable_seats() > newBooking.getSeatsNo()).findFirst().orElse(null);
            if (Objects.isNull(table)) {
                int guestsToSit = newBooking.getSeatsNo();
                while (guestsToSit <= 0) {
                    serviSer.addServiceByBookingId(newBooking.getId().toString(), table);
                    guestsToSit -= table.getTable_seats();
                }
            } else
            {
                serviSer.addServiceByBookingId(newBooking.getId().toString(), table);
            }
        }
        return newBooking;
    }

    @Override
    public void deleteBooking(String customerId) {
        // will throw the error if doesn't exist
        repository.deleteAll(getBookingsByCustomerId(customerId));
    }

    @Override
    public List<BookingEntity> getBookingsByCustomerId(String customerId) {
        return repository.findByCustomerId(UUID.fromString(customerId));
    }

    @Override
    public List<BookingEntity> getAllBookings() {
        return repository.findAll();
    }

    @Override
    public BookingEntity getBookingByBookingId(String id) {
        Optional<BookingEntity> e = repository.findById(UUID.fromString(id));
        return e.get();
    }

    @Override
    public BookingEntity modifyBooking(String id, @Valid Booking b) {
        BookingEntity ori = getBookingByBookingId(id);
        BookingEntity mod = toEntity(b);
        ori.setDateTime(mod.getDateTime());
        ori.setServices(mod.getServices());
        ori.setSeatsNo(mod.getSeatsNo());
        return ori;
    }

    @Override
    public BookingEntity toEntity(Booking m) {
        BookingEntity entity = new BookingEntity();
        entity.setUser(userRepo.findById(UUID.fromString(m.getUser().getId())).orElseThrow(() -> new CustomerNotFoundException(
              String.format(" - %s", m.getId()))));
        entity.setSeatsNo(m.getSeatsNo());
        entity.setDateTime(m.getDate().toLocalDateTime());
        return entity;
    }
}
