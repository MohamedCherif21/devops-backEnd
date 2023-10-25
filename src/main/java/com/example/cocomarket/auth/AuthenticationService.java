package com.example.cocomarket.auth;


import com.example.cocomarket.Entity.Autority;
import com.example.cocomarket.Entity.User;
import com.example.cocomarket.Repository.AuthorityRepository;
import com.example.cocomarket.Repository.User_Repository;
import com.example.cocomarket.config.JwtService;
import com.example.cocomarket.token.Token;
import com.example.cocomarket.token.TokenRepository;
import com.example.cocomarket.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.var;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final User_Repository repository;
    private final AuthorityRepository Auhtropo;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    public AuthenticationResponse register(String  userr, MultipartFile image) throws IOException {
        User user = objectMapper.readValue(userr, User.class);


        Set<Autority> auths=  user.getAutority();

        //   System.out.println("User A AJouter : "+user );
   /* var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .autority(naa)//autority(na)
        .build();*/
        String filename = StringUtils.cleanPath(image.getOriginalFilename());
        if(filename.contains("..")){
            System.out.println("!!! Not a valid File");
        }
        user.setImg(Base64.getEncoder().encodeToString(image.getBytes()));
        user.setPassword( passwordEncoder.encode(user.getPassword()));

        user.setNbr_tentatives(0);

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        if(auths !=null){
            auths.stream()
                    .forEach(obj -> {
                        obj.setUserAuth(user);
                        Auhtropo.save(obj);
                    });}
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public String update(String  userr, MultipartFile image) throws IOException {
        UpdateRequest user = objectMapper.readValue(userr, UpdateRequest.class);//dto

        User userExicte=repository.findById(user.getId()).orElse(null);//real object-->save()

        if (image !=null){
            String filename = StringUtils.cleanPath(image.getOriginalFilename());
            if(filename.contains("..")){
                System.out.println("!!! Not a valid File");
            }
            user.setImg(Base64.getEncoder().encodeToString(image.getBytes()));
            userExicte.setImg(user.getImg());}

        userExicte.setEmail(user.getEmail());
        userExicte.setFirst_name(user.getFirst_name());
        userExicte.setLast_name(user.getLast_name());
        userExicte.setRegion(user.getRegion());
        userExicte.setNum_phone(user.getNum_phone());
        userExicte.setAssosiation_info(user.getAssosiation_info());
        // userExicte.set(user.getLast_name());

        repository.save(userExicte);
        return "Updated";
    }


    public JwtResponse authenticate(AuthenticationRequest request) {
        User u= repository.FoundAcountBYMail(request.getEmail());
        System.out.println("User mail :"+u.getEmail());
        if (u != null  ){
            if (! passwordEncoder.matches(request.getPassword() , u.getPassword()  ) && u.getNbr_tentatives()< 5){
                System.out.println("Rahi lpassword Ghalta");
                System.out.println("Tentative_Numbger :"+u.getNbr_tentatives());
                u.setNbr_tentatives(u.getNbr_tentatives()+1);
                repository.save(u);
                System.out.println("I finish Adding");
                if(u.getNbr_tentatives() >= 5){
                    System.out.println("Try to Change True");
                    u.setEnabled(Boolean.TRUE);
                    u.setSleep_time(new Date(System.currentTimeMillis()));
                    repository.save(u);
                }
            }else if(passwordEncoder.matches(request.getPassword() , u.getPassword()  ) && u.getNbr_tentatives()< 5) {
                System.out.println("◓◓  ALL GOOD Password And Adresse   ◓◓ !!");
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

                var user = repository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("I Cant found this User- "));
                System.out.println("Hello 2!!");

                List<GrantedAuthority> authorities = getAuthorities(user.getAuthFromBase());
                var jwtToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, jwtToken);
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword(),authorities);
                AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
                return  new JwtResponse(user, jwtToken,null);
            }else {
                return  new JwtResponse(null, null," !!Your acount is disabled cause  of your SPAM \n" +
                        "  !! Retry Affter 30min ");
            }

        }
        System.out.println("Password Or Email Incorrect ");
        return  new JwtResponse(null, null,"  Password Or Email Incorrect !!");




    }

    private List<GrantedAuthority> getAuthorities(Set<Autority> autoritys) {
        List<GrantedAuthority> list = new ArrayList<>();
        for (Autority auth : autoritys){
            list.add(new SimpleGrantedAuthority(auth.getName()));
        }
        return list;
    }
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
