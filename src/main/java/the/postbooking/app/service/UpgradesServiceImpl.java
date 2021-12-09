package the.postbooking.app.service;

import org.springframework.stereotype.Service;
import postbookingapp.api.Upgrades;
import the.postbooking.app.entity.UpgradesEntity;
import the.postbooking.app.exception.ResourceNotFoundException;
import the.postbooking.app.repository.ServiceRepository;
import the.postbooking.app.repository.UpgradesRepository;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 28/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Service
public class UpgradesServiceImpl implements UpgradesService {

    private UpgradesRepository repository;
    private ServiceRepository serviceRep;

    private UpgradesServiceImpl(UpgradesRepository repository, ServiceRepository serviceRep) {
        this.repository = repository;
        this.serviceRep = serviceRep;
    }

    @Override
    public List<Upgrades> addUpgradesByTableId(String tableId, @Valid Upgrades upgrades) {
        if (Objects.isNull(upgrades.getService())) {
            throw new ResourceNotFoundException("Invalid table id.");
        }
        repository.save(toEntity(upgrades));
        return Collections.singletonList(upgrades);
    }

    @Override
    public List<Upgrades> addOrReplaceUpgradesByServiceId(String serviceId, @Valid Upgrades upgrades) {
        UpgradesEntity ori = getUpgradesByServiceId(serviceId);
        UpgradesEntity mod = toEntity(upgrades);
        ori.setDietary(mod.getDietary());
        ori.setDrink(mod.getDrink());
        ori.setQuantity(mod.getQuantity());
        ori.setService(mod.getService());
        ori.setUnitPrice(mod.getUnitPrice());
        ori.setSpecial_food(mod.getSpecial_food());
        repository.save(ori);
        return Collections.singletonList(upgrades);
    }

    @Override
    public void deleteAllUpgradesByServiceId(String serviceId) {
        repository.delete(getUpgradesByServiceId(serviceId));
    }

    @Override
    public UpgradesEntity getUpgradesByServiceId(String serviceId) {
        return repository.findByServiceId(UUID.fromString(serviceId));
    }

    @Override
    public UpgradesEntity toEntity(Upgrades upgrades) {
        UpgradesEntity entity = new UpgradesEntity();
        entity.setDietary(upgrades.getDietary());
        entity.setDrink(upgrades.getDrink());
        entity.setQuantity(upgrades.getQuantity());
        entity.setService(serviceRep.getOne(UUID.fromString(upgrades.getService().getId())));
        entity.setUnitPrice(upgrades.getUnitPrice().doubleValue());
        entity.setSpecial_food(upgrades.getSpecialFood());
        return entity;
    }

}