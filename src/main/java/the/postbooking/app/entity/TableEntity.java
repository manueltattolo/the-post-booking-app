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
@Table(name = "rest_table")
public class TableEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "TABLE_SEATS", nullable = false)
    private int table_seats;

    @Column(name = "NAME")
    private String name;

    /*@OneToMany(mappedBy = "rest_table", fetch = FetchType.LAZY)
    private List<ServiceEntity> services;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID", nullable = false)
    private RestaurantEntity restaurant;

}