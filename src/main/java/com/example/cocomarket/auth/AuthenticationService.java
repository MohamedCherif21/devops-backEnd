package com.example.cocomarket.auth;


import com.example.cocomarket.entity.Autority;
import com.example.cocomarket.entity.User;
import com.example.cocomarket.repository.AuthorityRepository;
import com.example.cocomarket.repository.UserRepository;
import com.example.cocomarket.config.JwtService;
import com.example.cocomarket.token.Token;
import com.example.cocomarket.token.TokenRepository;
import com.example.cocomarket.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
  private final UserRepository repository;
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
        String filename = StringUtils.cleanPath(image.getOriginalFilename());
        if(filename.contains("..")){
        }
        user.setImg(Base64.getEncoder().encodeToString(image.getBytes()));
        user.setPassword( passwordEncoder.encode(user.getPassword()));

        user.setNbrtentatives(0);

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
            user.setImg(Base64.getEncoder().encodeToString(image.getBytes()));
            userExicte.setImg(user.getImg());}

        userExicte.setEmail(user.getEmail());
        userExicte.setFirstname(user.getFirst_name());
        userExicte.setLastname(user.getLast_name());
        userExicte.setRegion(user.getRegion());
        userExicte.setNumphone(user.getNum_phone());
        userExicte.setAssosiationInfo(user.getAssosiation_info());

        repository.save(userExicte);
        return "Updated";
    }


    public JwtResponse authenticate(AuthenticationRequest request) {
        User u= repository.FoundAcountBYMail(request.getEmail());
        if (u != null  ){
            if (! passwordEncoder.matches(request.getPassword() , u.getPassword()  ) && u.getNbrtentatives()< 5){

                u.setNbrtentatives(u.getNbrtentatives()+1);
                repository.save(u);

                if(u.getNbrtentatives() >= 5){

                    u.setEnabled(Boolean.TRUE);
                    u.setSleeptime(new Date(System.currentTimeMillis()));
                    repository.save(u);
                }
            }else if(passwordEncoder.matches(request.getPassword() , u.getPassword()  ) && u.getNbrtentatives()< 5) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

                var user = repository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("I Cant found this User- "));

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
