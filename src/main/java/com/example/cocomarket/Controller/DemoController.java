package com.example.cocomarket.Controller;

import com.example.cocomarket.Entity.Autority;
import com.example.cocomarket.Entity.User;
import com.example.cocomarket.Repository.AuthorityRepository;
import com.example.cocomarket.Repository.User_Repository;
import com.example.cocomarket.Services.User_Service;
import com.example.cocomarket.config.JwtService;
import com.example.cocomarket.token.Token;
import com.example.cocomarket.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

  @Autowired
  private AuthorityRepository AuhtRepo;
  @Autowired
  private TokenRepository tokenRepo;

  @Autowired
  private User_Service userService;
  @Autowired
  private User_Repository UserRepo;


  @GetMapping
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello from secured endpoint");
  }

  @GetMapping("/profile")
  @PreAuthorize("hasAuthority('USER')")
  public String profile(){
    return "You are on PROFILE page";
  }

  @GetMapping("/Dashboard")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String Dashboard(){
    return "You are on Dashboard page";
  }

  @Autowired
  private JwtService jwtUtils;
  //--------------------- SESSION ----------------------------
  @GetMapping("/user")
  public User getUserDetails(HttpServletRequest request) {
    String token = getTokenFromRequest(request);
    String username = jwtUtils.getUsernameFromToken(token);
    // fetch user details using the username
    User user = UserRepo.FoundAcountBYMail(username);
    return user;
  }
  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
  //--------------------- ^^^^  SESSION ^^^^^^   --------------------






  @GetMapping("/GetNbrUserByRole/{role}")
  //@PreAuthorize("hasAuthority('ADMIN')")
  public List<User> NbrUsers(@PathVariable String role){
    return userService.GetUserByRole(role);
  }

  @GetMapping("/GetUserConnecterByRole/{role}")
  // @PreAuthorize("hasAuthority('ADMIN')")
  public List<User> GetTokenV(@PathVariable String role){
    List<Token> TokenValide= tokenRepo.findAllValidToken();

    List<User> UserConnecter=new ArrayList<>();
    for (Token T:TokenValide){
      System.out.println("Token Valide :"+T.getToken());
      if(T.revoked == false && T.expired==false){
        for( Autority Auth : T.getUser().getAutority()){
          System.out.println("Auth :"+Auth.getName());
          if (  Auth.getName().equals(role)){
            UserConnecter.add(T.getUser());
          }
        }}
    }
    return UserConnecter;
  }
}
