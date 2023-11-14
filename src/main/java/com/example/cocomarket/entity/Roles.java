package com.example.cocomarket.entity;
import lombok.*;

import javax.persistence.*;



@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private int id;
    @Enumerated(EnumType.STRING)
    private ROLE role;


}
