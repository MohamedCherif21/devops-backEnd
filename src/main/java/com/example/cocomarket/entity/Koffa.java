package com.example.cocomarket.entity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Koffa {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    private String nomassociation;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    private User userKoffa ;
    @OneToMany
    private Set<Produit> produitsKoffa;

}
