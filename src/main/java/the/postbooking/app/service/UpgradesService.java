package the.postbooking.app.service;

import postbookingapp.api.Upgrades;
import the.postbooking.app.entity.UpgradesEntity;

import javax.validation.Valid;

/**
 * Created by Emanuele Tattolo on 28/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
public interface UpgradesService {
    public UpgradesEntity addUpgradesByTableId(String tableId, @Valid Upgrades upgrades);
    public UpgradesEntity addOrReplaceUpgradesByTableId(String tableId, @Valid Upgrades upgrades);
    public void deleteAllUpgradesByTableId(String tableId);
    public UpgradesEntity getUpgradesByTableId(String tableId);
    public UpgradesEntity toEntity(Upgrades upgrades);
}
