package com.gr74.ScreenMaster.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seat_types",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"name"},
                name = "uk_seat_type_name"
        )
)
@EntityListeners(AuditingEntityListener.class)
public class SeatType extends BaseEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private double priceMultiplier;
    private String description;
    private boolean is_active;



}
