package the.postbooking.app.service;

import postbookingapp.api.Upgrades;
import the.postbooking.app.entity.UpgradesEntity;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Emanuele Tattolo on 28/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
public interface UpgradesService {
    public List<Upgrades> addUpgradesByTableId(String tableId, @Valid Upgrades upgrades);
    public List<Upgrades> addOrReplaceUpgradesByServiceId(String tableId, @Valid Upgrades upgrades);
    public void deleteAllUpgradesByServiceId(String tableId);
    public UpgradesEntity getUpgradesByServiceId(String tableId);
    public UpgradesEntity toEntity(Upgrades upgrades);
}
