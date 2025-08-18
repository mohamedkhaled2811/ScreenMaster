package com.gr74.ScreenMaster.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gr74.ScreenMaster.enums.ScreenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screens")
public class Screen extends BaseEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Integer totalSeats;

    private ScreenType screenType;

    private boolean is_active;


    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;



    @ManyToOne
    @JoinColumn(name="theater_id")
    @JsonBackReference
    private Theater theater;


}
