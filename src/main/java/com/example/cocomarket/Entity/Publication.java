package com.example.cocomarket.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Publication {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    private String Publications;
    private LocalDate datePub;


@JsonIgnore

    @ManyToOne
    private User userPublication;
@JsonIgnore
    @OneToMany
    private Set<Comentaire> Comentaire_Du_Publication;
@JsonIgnore

    @OneToMany(mappedBy = "publication")
    private Set<Reaction> reactions;


}
