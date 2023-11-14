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
public class RaitingDelevryMan {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    private String feedBack;
    private Integer score;
    @OneToMany
    @JsonIgnore
    private Set<User> ratingUser;

}
