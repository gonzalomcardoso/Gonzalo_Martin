package com.mercadona.test.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "worker_section_assignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerSectionAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(1)
    private Integer horasAsignadas;


    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "worker")
    private List<WorkerSectionAssignment> assignments;

}
