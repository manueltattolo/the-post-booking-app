package the.postbooking.app.service;

import org.springframework.stereotype.Service;
import postbookingapp.api.RestTable;
import the.postbooking.app.entity.TableEntity;
import the.postbooking.app.exception.ResourceNotFoundException;
import the.postbooking.app.repository.RestaurantRepository;
import the.postbooking.app.repository.TableRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 14/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Service
public class TableServiceImpl implements TableService {

    private TableRepository repository;
    private RestaurantRepository restaRepo;

    public TableServiceImpl(TableRepository repository, RestaurantRepository restaRepo) {
        this.repository = repository;
        this.restaRepo = restaRepo;
    }

    @Override
    public void deleteTableFromTableId(String tableId) {
        repository.deleteById(UUID.fromString(tableId));
    }

    @Override
    public List<TableEntity> getTablesByCustomerId(String customerId) {
        return repository.findByCustomerId(UUID.fromString(customerId));
    } //not used and not on the API

    @Override
    public List<TableEntity> getTablesByRestaurantId(String restaurantId) {
        return null;//repository.findByRestaurantId(UUID.fromString(restaurantId));
    }

    @Override
    public List<TableEntity> getTablesByRestName(String restName) {
        return repository.findByRestaurantName(restName);
    }

    @Override
    public RestTable addTableByRestaurantName(String restaurantName, @Valid RestTable table) {
        if (Objects.isNull(table.getTableSeats())) {
            throw new ResourceNotFoundException("No seats number on the table.");
        }

        repository.save(toEntity(restaurantName, table));
        return table;
    }
    //modify table or service?
    //getTablesByServices??

    @Override
    public TableEntity toEntity(String restaurantName, RestTable t) {
        TableEntity entity = new TableEntity();
        entity.setRestaurant(restaRepo.findByRestName(restaurantName));
        entity.setTable_seats(t.getTableSeats());
        System.out.print(entity.getRestaurant().getRestName() + " and " + entity.getRestaurant().getId());
        return entity;
    }
}