package com.dentflow.tooth.model;

import com.dentflow.description.model.Description;
import com.dentflow.patient.model.Patient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "teeth")
public class Tooth {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toothId;

    @NotBlank
    private int number;

    @OneToMany
    private List<Description> description;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


}

