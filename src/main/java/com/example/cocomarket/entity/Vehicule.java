package com.example.cocomarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    @Enumerated(EnumType.STRING)
    private CarType type;
    private String description;
    private Float wheight;
    private Float volume;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Livraison> livcar;
    @OneToOne(mappedBy = "car")
    @JsonIgnore
    private User usercar;

}
