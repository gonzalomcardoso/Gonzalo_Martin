package com.mercadona.test.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellidos;

    private String dni;

    private Integer horasDisponibles;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

}
