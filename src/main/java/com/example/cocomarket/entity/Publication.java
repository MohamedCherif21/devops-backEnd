package com.example.cocomarket.entity;

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
    private String publications;
    private LocalDate datePub;


@JsonIgnore

    @ManyToOne
    private User userPublication;
@JsonIgnore
    @OneToMany
    private Set<Comentaire> comentaireDuPublication;
@JsonIgnore

    @OneToMany(mappedBy = "publication")
    private Set<Reaction> reactions;


}
