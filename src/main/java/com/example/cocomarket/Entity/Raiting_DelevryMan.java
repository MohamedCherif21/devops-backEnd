package com.example.cocomarket.Entity;
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
public class Raiting_DelevryMan {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    private String feedBack;
    private Integer Score;
    @OneToMany
    @JsonIgnore
    private Set<User> Rating_User;

}
