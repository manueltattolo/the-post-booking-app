package the.postbooking.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
@Table(name = "upgrades")
public class UpgradesEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "UNIT_PRICE")
    private double unitPrice;

    @Column(name = "DRINK")
    private String drink;

    @Column(name = "SPECIAL_FOOD")
    private String special_food;

    @Column(name = "DIETARY")
    private String dietary;

    @OneToOne
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "ID")
    private ServiceEntity service;

    /*@OneToMany(mappedBy = "UPGRADES", fetch = FetchType.LAZY)
    private List<WaiterEntity> waiters;*/



}