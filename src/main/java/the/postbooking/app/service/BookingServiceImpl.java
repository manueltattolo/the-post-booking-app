package the.postbooking.app.service;

import org.springframework.stereotype.Service;
import postbookingapp.api.Booking;
import the.postbooking.app.entity.BookingEntity;
import the.postbooking.app.entity.ServiceEntity;
import the.postbooking.app.entity.TableEntity;
import the.postbooking.app.entity.UserEntity;
import the.postbooking.app.exception.ResourceNotFoundException;
import the.postbooking.app.repository.BookingRepository;
import the.postbooking.app.repository.RestaurantRepository;
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
    private RestaurantRepository restRep;

    public BookingServiceImpl(BookingRepository repository, UserRepository userRepo,
                              TableService tableSer, ServiceService serviSer, RestaurantRepository restRep) {
        this.repository = repository;
        this.userRepo = userRepo;
        this.tableSer = tableSer;
        this.serviSer = serviSer;
        this.restRep = restRep;
    }

    @Override
    public Booking addBooking(@Valid Booking booking) {
        if (Objects.isNull(booking.getUser())) {
            throw new ResourceNotFoundException("Not user found.");
        }
        if (Objects.isNull(booking.getSeatsNo())) {
            throw new ResourceNotFoundException("Invalid seats number.");
        }
        if (Objects.isNull(booking.getDate().toLocalDateTime())) {
            throw new ResourceNotFoundException("Invalid date.");
        }
        if (booking.getRestaurant().getRestName() == null) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        //verification that restaurant has tables available for that time.
        BookingEntity newBooking = toEntity(booking);
        //calculating tables booked at given time by getting the services and the tables from the services
        List<TableEntity> tablesBookedAtTime = serviSer.getServicesByDateTime(Timestamp.valueOf(newBooking.getDateTime()))
              .stream().map(ServiceEntity::getRest_table).collect(Collectors.toList());
        //calculating tables available by getting the total tables in the restaurant and removing the booked ones
        System.out.print(tablesBookedAtTime.size());

        List<TableEntity> tablesAv = tableSer.getTablesByRestName(newBooking.getRestaurant().getRestName());
        tablesAv.removeAll(tablesBookedAtTime);
        System.out.print(tablesAv.size());
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
        return booking;
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
        ori.setSeatsNo(mod.getSeatsNo());
        repository.save(ori);
        return ori;
    }

    @Override
    public BookingEntity toEntity(Booking m) {
        BookingEntity entity = new BookingEntity();
        UserEntity newUser = new UserEntity();

        if (m.getUser().getId() == null) {
            newUser.setFirstName(m.getUser().getFirstname());
            newUser.setLastName(m.getUser().getLastname());
            newUser.setEmail(m.getUser().getEmail());
            newUser.setPhone(m.getUser().getPhone());
            newUser.setUsername(m.getUser().getUsername());
            newUser.setPassword(m.getUser().getPassword());
        } else {
            newUser = userRepo.findById(UUID.fromString(m.getUser().getId())).orElseThrow(() -> new ResourceNotFoundException("Invalid customer id."));
        }
        userRepo.save(newUser);
        entity.setUser(newUser);
        entity.setSeatsNo(m.getSeatsNo());
        entity.setDateTime(m.getDate().toLocalDateTime());
        entity.setRestaurant(restRep.findByRestName(m.getRestaurant().getRestName()));
        return entity;
    }
}
