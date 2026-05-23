package com.mercadona.test.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer horasNecesarias;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
