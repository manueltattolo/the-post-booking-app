package the.postbooking.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 04/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Getter
@Setter
@Entity
@Table(name = "menu")
public class MenuEntity {
    /*    food varchar(32),
    drink varchar(32),
	quantity numeric(8, 0),
    unit_price numeric(16, 4),
    rest_id uuid NOT NULL,*/

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "DRINK")
    private String drink;

    @Column(name = "FOOD")
    private String food;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "UNIT_PRICE")
    private double unitPrice;

    @Column(name = "PICTURE")
    private String picture;

    @OneToOne
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID", nullable = false)
    private RestaurantEntity restaurant;
}
