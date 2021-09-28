package the.postbooking.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 04/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "waiter")
public class WaiterEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "FULLNAME")
    private String fullname;

    @OneToMany(mappedBy = "waiter", fetch = FetchType.LAZY)
    private List<ServiceEntity> services;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID", nullable = false)
    private RestaurantEntity restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPGRADES_ID", referencedColumnName = "ID", nullable = false)
    private UpgradesEntity upgrades;
}