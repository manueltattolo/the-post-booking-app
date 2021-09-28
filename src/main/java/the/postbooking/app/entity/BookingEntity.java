package the.postbooking.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by Emanuele Tattolo on 31/08/2021
 * UPDATE PROGRAM COMMENTS ABOUT PROGRAM HERE
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ServiceEntity> services;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private UserEntity user;

    @Column(name = "SEATS_NUM", nullable = false)
    private int seatsNo;

    @Column(name = "BOOKING_DATE", unique = true, nullable = false)
    private LocalDateTime dateTime;

}