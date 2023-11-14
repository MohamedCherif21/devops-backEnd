package com.example.cocomarket.entity;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;



@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Contrat {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    private Float commision;
    private String description;
    private LocalDate dateDebutContrat;
    private LocalDate dateFinContrat;
    private Boolean archive;
    @JsonIgnore
    @OneToMany(mappedBy = "Contrat_shop")
    private Set<Shop> shopesContrats;
}
