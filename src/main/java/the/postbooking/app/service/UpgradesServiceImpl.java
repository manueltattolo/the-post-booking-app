package the.postbooking.app.service;

import org.springframework.stereotype.Service;
import postbookingapp.api.Upgrades;
import the.postbooking.app.entity.UpgradesEntity;
import the.postbooking.app.exception.ResourceNotFoundException;
import the.postbooking.app.repository.TableRepository;
import the.postbooking.app.repository.UpgradesRepository;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 28/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Service
public class UpgradesServiceImpl implements UpgradesService {

    private UpgradesRepository repository;
    private TableRepository tableRep;

    private UpgradesServiceImpl(UpgradesRepository repository, TableRepository tableRep) {
        this.repository = repository;
        this.tableRep = tableRep;
    }

    @Override
    public UpgradesEntity addUpgradesByTableId(String tableId, @Valid Upgrades upgrades) {
        if (Objects.isNull(upgrades.getTable())) {
            throw new ResourceNotFoundException("Invalid table id.");
        }

        return repository.save(toEntity(upgrades));
    }

    @Override
    public UpgradesEntity addOrReplaceUpgradesByTableId(String tableId, @Valid Upgrades upgrades) {
        UpgradesEntity ori = getUpgradesByTableId(tableId);
        UpgradesEntity mod = toEntity(upgrades);
        ori.setDietary(mod.getDietary());
        ori.setDrink(mod.getDrink());
        ori.setQuantity(mod.getQuantity());
        ori.setRest_table(mod.getRest_table());
        ori.setUnitPrice(mod.getUnitPrice());
        ori.setSpecial_food(mod.getSpecial_food());
        return ori;
    }

    @Override
    public void deleteAllUpgradesByTableId(String tableId) {
        repository.delete(getUpgradesByTableId(tableId));
    }

    @Override
    public UpgradesEntity getUpgradesByTableId(String tableId) {
        return repository.findByTableId(UUID.fromString(tableId));
    }

    @Override
    public UpgradesEntity toEntity(Upgrades upgrades) {
        UpgradesEntity entity = new UpgradesEntity();
        entity.setDietary(upgrades.getDietary());
        entity.setDrink(upgrades.getDrink());
        entity.setQuantity(upgrades.getQuantity());
        entity.setRest_table(tableRep.getOne(UUID.fromString(upgrades.getTable().getId())));
        entity.setUnitPrice(upgrades.getUnitPrice().doubleValue());
        entity.setSpecial_food(upgrades.getSpecialFood());
        return entity;
    }

}