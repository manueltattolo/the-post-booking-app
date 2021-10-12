package the.postbooking.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import postbookingapp.api.Waiter;
import postbookingapp.api.WaiterApi;
import the.postbooking.app.hateoas.WaiterRepresentationModelAssembler;
import the.postbooking.app.service.WaiterService;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by Emanuele Tattolo on 08/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@RestController
public class WaiterController implements WaiterApi {

    private WaiterService service;
    private final WaiterRepresentationModelAssembler assembler;

    public WaiterController(WaiterService service, WaiterRepresentationModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<Waiter> createWaiterForGivenRestaurant(@Valid Waiter waiter) {
        return ok(service.createWaiterForGivenRestaurant(waiter));
    }

    @Override
    public ResponseEntity<Void> deleteWaiterById(String waiterId) {
        service.deleteWaiterById(waiterId);
        return accepted().build();
    }

    @Override
    public ResponseEntity<List<Waiter>> getAllWaitersForGivenRestaurant() {
        return ok(assembler.toListModel(service.getAllWaitersForGivenRestaurant()));
    }
}