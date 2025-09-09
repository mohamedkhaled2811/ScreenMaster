package com.gr74.ScreenMaster.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seats",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"screen_id", "seat_number", "row_number"},
                name = "uk_screen_seat_row"
        )
)
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer seatNumber;
    private String rowNumber;
    private boolean is_available;
    private boolean is_accessible;

    @ManyToOne
    @JoinColumn(name = "seat_type_id")
    private SeatType seatType;


    @ManyToOne
    @JoinColumn(name = "screen_id")
    @JsonBackReference
    private Screen screen;


}
