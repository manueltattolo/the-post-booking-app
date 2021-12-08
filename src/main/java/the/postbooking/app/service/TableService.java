package the.postbooking.app.service;

import postbookingapp.api.RestTable;
import the.postbooking.app.entity.TableEntity;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Emanuele Tattolo on 14/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
public interface TableService {
    public void deleteTableFromTableId(String tableId);
    public Iterable<TableEntity> getTablesByCustomerId(String customerId);
    public List<TableEntity> getTablesByRestaurantId(String restaurantId);
    public List<TableEntity> getTablesByRestName(String restName);
    public @Valid RestTable addTableByRestaurantName(String restaurantName, @Valid RestTable table);
    public TableEntity toEntity(String restaurantId, RestTable t);
}
