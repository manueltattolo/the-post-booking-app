package the.postbooking.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import postbookingapp.api.Upgrades;
import postbookingapp.api.UpgradesApi;
import the.postbooking.app.hateoas.UpgradesRepresentationModelAssembler;
import the.postbooking.app.service.UpgradesService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by Emanuele Tattolo on 08/10/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/

@RestController
public class UpgradesController implements UpgradesApi {

    private UpgradesService service;
    private final UpgradesRepresentationModelAssembler assembler;

    public UpgradesController(UpgradesService service, UpgradesRepresentationModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<List<Upgrades>> addOrReplaceUpgradesByTableId(String tableId, @Valid Upgrades upgrades) {
        return ok(service.addOrReplaceUpgradesByTableId(tableId, upgrades));
    }

    @Override
    public ResponseEntity<List<Upgrades>> addUpgradesByTableId(String tableId, @Valid Upgrades upgrades) {
        return ok(service.addUpgradesByTableId(tableId, upgrades));
    }

    @Override
    public ResponseEntity<Upgrades> getUpgradesByTableId(String tableId) {
        return ok(assembler.toModel(service.getUpgradesByTableId(tableId)));
    }

    @Override
    public ResponseEntity<Void> deleteAllUpgradesByTableId(String tableId) {
        service.deleteAllUpgradesByTableId(tableId);
        return accepted().build();
    }
}