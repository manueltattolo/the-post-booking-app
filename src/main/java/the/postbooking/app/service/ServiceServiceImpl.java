package the.postbooking.app.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import the.postbooking.app.entity.ServiceEntity;
import the.postbooking.app.entity.TableEntity;
import the.postbooking.app.exception.GenericAlreadyExistsException;
import the.postbooking.app.exception.ResourceNotFoundException;
import the.postbooking.app.repository.ServiceRepository;
import the.postbooking.app.repository.TableRepository;
import the.postbooking.app.repository.WaiterRepository;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Emanuele Tattolo on 15/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Service
public class ServiceServiceImpl implements ServiceService {

    private ServiceRepository repository;
    private TableRepository tableRep;
    private BookingService bookSer;
    private WaiterRepository waitRep;

    public ServiceServiceImpl(@Lazy ServiceRepository repository, @Lazy TableRepository tableRep,
                              @Lazy BookingService bookSer, @Lazy WaiterRepository waitRep) {
        this.repository = repository;
        this.tableRep = tableRep;
        this.bookSer = bookSer;
        this.waitRep = waitRep;
    }

    @Override //method to assign services by frontend with bookingId
    public List<ServiceEntity> addServicesByBookingId(String bookingId, @Valid postbookingapp.api.Service service) {
        List<ServiceEntity> services = getServicesByBookingId(bookingId);
        //checking if service is already in the system
        long count = services.stream()
              .filter(s -> s.getId().equals(UUID.fromString(service.getId()))).count();
        if (count > 0) {
            throw new GenericAlreadyExistsException(
                  String.format("Service with Id (%s) already exists. You can update it.", service.getId()));
        }
        //adding service to the service list
        services.add(toEntity(service));

        return repository.saveAll(services);
    }

    @Override
    public List<ServiceEntity> addOrReplaceServicesByBookingId(String bookingId, @Valid postbookingapp.api.Service service) {
        List<ServiceEntity> services = getServicesByBookingId(bookingId);
        services = Objects.nonNull(services) ? services : Collections.emptyList();
        AtomicBoolean serviceExists = new AtomicBoolean(false);
        services.forEach(s -> {
            if (s.getId().equals(UUID.fromString(service.getId()))) {
                s.setRest_table(tableRep.findById(UUID.fromString(service.getTable().getId()))
                      .orElseThrow(() -> new ResourceNotFoundException(String.format(" - %s", service.getTable().getId()))));
                s.setWaiter(waitRep.findById(UUID.fromString(service.getWaiter().getId()))
                      .orElseThrow(() -> new ResourceNotFoundException(String.format(" - %s", service.getWaiter().getId()))));
                serviceExists.set(true);
            }
        });
        if (!serviceExists.get()) {
            services.add(toEntity(service));
        }
        return repository.saveAll(services);
    }

    @Override
    public void deleteServicesByBookingId(String bookingId) {
        List<ServiceEntity> services = getServicesByBookingId(bookingId);
        repository.deleteAll(services);
    }

    @Override
    public List<ServiceEntity> getServicesByBookingId(String bookingId) {
        return repository.findByBookingId(UUID.fromString(bookingId));
    }

    @Override
    public List<ServiceEntity> getServicesByDateTime(Timestamp dateTime){
        return repository.getServicesByDateTime(dateTime);
    }

    @Override //method to automatically assigned services and tables once booking is created
    public ServiceEntity addServiceByBookingId(String bookingId, TableEntity table) {

        ServiceEntity newService = new ServiceEntity();
        newService.setBooking(bookSer.getBookingByBookingId(bookingId));
        newService.setRest_table(table);
        //newService.setWaiter();
        return repository.save(newService);
    }

    public ServiceEntity toEntity(postbookingapp.api.Service s) {
        ServiceEntity entity = new ServiceEntity();
        entity.setBooking(bookSer.getBookingByBookingId(s.getBooking().getId()));
        entity.setRest_table(tableRep.findById(UUID.fromString(s.getTable().getId()))
              .orElseThrow(() -> new ResourceNotFoundException(String.format(" - %s", s.getTable().getId()))));
        entity.setWaiter(waitRep.findById(UUID.fromString(s.getTable().getId()))
              .orElseThrow(() -> new ResourceNotFoundException(String.format(" - %s", s.getWaiter().getId()))));
        return entity;
    }
}