package com.example.cocomarket.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Categorie {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private  Integer id;
    private String type;

    public Categorie(String type) {
        this.type=type ;
        subCatergorie=new ArrayList<>() ;

    }
    public List<Categorie> subCatergories () {return  subCatergorie ;}

    //public void addsubCatergorie( Categorie categorie){ subCatergorie.add(categorie) ; }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="parent_is")
    private List<Categorie> subCatergorie =new ArrayList<>() ;



    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="Categories")
    private Set<Produit> produits;
}
