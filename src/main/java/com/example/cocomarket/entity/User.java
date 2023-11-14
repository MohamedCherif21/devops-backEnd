package com.example.cocomarket.entity;

 import com.fasterxml.jackson.annotation.JsonIgnore;
 import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
 import java.util.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    private String lastname;
    private String firstname;
    private Boolean premium;//par default 0
    private String email;
    private String password;
    private Float loyaltypoint;
    private String assosiationInfo;
    private String files;
    @Lob
    private String img;
    private String region;
    private Boolean enabled;
    private Integer nbrtentatives;
    private Boolean availability;
    private Date sleeptime;
    private String numphone;
    @OneToMany(mappedBy = "userPublication" ,cascade = CascadeType.ALL)
    @JsonIgnore
    private transient Set<Publication> publications;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private transient  Set<Comentaire> respons;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private transient  Set<MSG> msgs;
    @OneToMany(mappedBy = "userShop")
    @JsonIgnore
    private transient  Set<Shop> shops;
    @OneToMany(mappedBy = "userKoffa")
    @JsonIgnore
    private transient Set<Koffa> koffas;
    @OneToOne(cascade = CascadeType.ALL)
    private CART cart;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private transient Set<Raiting_Product> raitingproducts;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private transient Vehicule car;
    private String adresseuser;


    @OneToMany(mappedBy = "UserAuth" ,fetch = FetchType.EAGER)

    private transient Set<Autority> autority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>() ;
        for (Autority authority : autority) {
            if (authority !=null)
                authorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return authorities;
    }
    public Set<Autority> getAuthFromBase(){
        return this.autority;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }








}
