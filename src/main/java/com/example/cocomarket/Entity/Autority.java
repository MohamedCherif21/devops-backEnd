package com.example.cocomarket.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Autority {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    private String email;
    private String name;
    // @ManyToMany(mappedBy = "autoritys")
    // private Set<Roles> RolesAutority;
    @ManyToOne
    @JsonIgnore
    private User UserAuth;

}
