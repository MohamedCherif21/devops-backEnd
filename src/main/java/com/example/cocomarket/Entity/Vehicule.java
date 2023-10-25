package com.example.cocomarket.Entity;

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
    private CarType Type;
    private String description;
    private Float Wheight;
    private Float Volume;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Livraison> liv_car;
    @OneToOne(mappedBy = "car")
    @JsonIgnore
    private User user_car;

}
