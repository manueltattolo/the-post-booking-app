package the.postbooking.app.service;

import postbookingapp.api.Service;
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
    public List<ServiceEntity> addServicesByBookingId(String bookingId, @Valid Service service);
    public List<ServiceEntity> addOrReplaceServicesByBookingId(String bookingId, @Valid Service service);
    public void deleteServicesByBookingId(String bookingId);
    public List<ServiceEntity> getServicesByBookingId(String bookingId);
    List<ServiceEntity> getServicesByDateTime(Timestamp dateTime);
    ServiceEntity addServiceByBookingId(String bookingId, TableEntity table);
}
