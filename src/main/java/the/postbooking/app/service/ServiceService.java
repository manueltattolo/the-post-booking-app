package the.postbooking.app.service;

import postbookingapp.api.RestService;
import the.postbooking.app.entity.ServiceEntity;
import the.postbooking.app.entity.TableEntity;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Emanuele Tattolo on 15/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
public interface ServiceService {
    public List<RestService> addServicesByBookingId(String bookingId, @Valid RestService service);
    public List<RestService> addOrReplaceServicesByBookingId(String bookingId, @Valid RestService service);
    public void deleteServicesByBookingId(String bookingId);
    public List<ServiceEntity> getServicesByBookingId(String bookingId);
    List<ServiceEntity> getServicesByDateTime(Timestamp dateTime);
    ServiceEntity addServiceByBookingId(String bookingId, TableEntity table);
}
