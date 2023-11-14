package com.example.cocomarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    private String adresse;

    private String nom;
    private String img;
    private String description;
    private Integer iban;
    private Integer bic;
    @Enumerated(EnumType.STRING)
    private Status traitement;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private User userShop;
    @OneToMany(mappedBy = "Shopes",cascade = CascadeType.ALL)
    private Set<Produit> produitShopes;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Contrat contratShop;

    @Lob
    @Column(name = "qr_code_shop", columnDefinition="VARBINARY(5000)")
    private byte[] qrCodeShop;
    private String url;



}
