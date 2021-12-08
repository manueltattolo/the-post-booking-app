package the.postbooking.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import postbookingapp.api.RestTable;
import postbookingapp.api.TableApi;
import the.postbooking.app.hateoas.TableRepresentationModelAssembler;
import the.postbooking.app.service.TableService;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by Emanuele Tattolo on 08/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@RestController
public class TableController implements TableApi {

    private TableService service;
    private final TableRepresentationModelAssembler assembler;

    public TableController(TableService service, TableRepresentationModelAssembler assembler) {
        this.assembler = assembler;
        this.service = service;
    }

    @Override
    public ResponseEntity<RestTable> addTableByRestaurantName(String restaurantName, @Valid RestTable RestTable) {
        return ok(service.addTableByRestaurantName(restaurantName, RestTable));
    }

    @Override
    public ResponseEntity<List<RestTable>> getTablesByRestName(String restName) {
        return ok(assembler.toListModel(service.getTablesByRestName(restName)));
    }

    @Override
    public ResponseEntity<Void> deleteTableFromTableId(String tableId) {
        service.deleteTableFromTableId(tableId);
        return accepted().build();
    }

    @Override
    public ResponseEntity<List<RestTable>> getTablesByRestaurantId(String restaurantId) {
        return ok(assembler.toListModel(service.getTablesByRestaurantId(restaurantId)));
    }
}