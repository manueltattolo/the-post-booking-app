package the.postbooking.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 03/09/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service")
public class ServiceEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKING_ID", referencedColumnName = "ID", nullable = false)
    private BookingEntity booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REST_TABLE_ID", referencedColumnName = "ID", nullable = false)
    private TableEntity rest_table;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAITER_ID", referencedColumnName = "ID")
    private WaiterEntity waiter;
}