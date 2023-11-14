package com.example.cocomarket.auth;

import com.example.cocomarket.entity.Autority;
import com.example.cocomarket.entity.User;
import com.example.cocomarket.repository.AuthorityRepository;
import com.example.cocomarket.repository.UserRepository;
import com.example.cocomarket.config.EmailSenderService;
import com.example.cocomarket.config.JwtService;
import com.example.cocomarket.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  @Autowired
  private PasswordEncoder passwordEncoder;
  private static String CodeRecived;
  @Autowired
  private UserRepository UserRepo;
  @Autowired
  private EmailSenderService service;

  private final AuthenticationService serviceAuth;

  @PostMapping(path = "/register",consumes = {MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<AuthenticationResponse> register(@RequestParam String request,@RequestParam MultipartFile file) throws IOException {
    return ResponseEntity.ok(serviceAuth.register(request,file));
  }
  @PutMapping(path = "/update",consumes = {MULTIPART_FORM_DATA_VALUE})
  public String update(@RequestParam String request,@RequestParam(required = false) MultipartFile file) throws IOException {
    return serviceAuth.update(request,file);
  }

  @PostMapping("/authenticate")
  public JwtResponse authenticate(
          @RequestBody AuthenticationRequest request
  ) {
    return serviceAuth.authenticate(request);
  }

  @GetMapping("/SendMailForgetPswd/{mail}")
  //@PreAuthorize("hasAuthority('ADMIN')")
  public String SendMail(@PathVariable String mail){
    service.sendSimpleEmail(mail,"This Is the code :"+ this.CodeRecived,"Security Alert ");
    return "Email receved";

  }

  @GetMapping("/Verifier/{mail}/{code}/{newPsw}")
  //@PreAuthorize("hasAuthority('ADMIN')")
  public String SendMail(@PathVariable String mail,@PathVariable String code,@PathVariable String newPsw){
    System.out.println("Code Envoyer est ="+this.CodeRecived);
    System.out.println("Code saiser est ="+ code);

    if (this.CodeRecived.compareTo(code) == 0 ){
      User u= UserRepo.findByEmail(mail).get();
      System.out.println("UserName ="+ u.getLastname()  );

      u.setPassword( passwordEncoder.encode(newPsw));

      UserRepo.save(u);
      return "Code Correct Password has been Update Succeful";
    }
    else
      return "-______-  ' Failed to Update Psw of this account '   -_______-";
  }
  @GetMapping("/VerifierCode/{code}")
  public boolean coparison(@PathVariable String code){
    if (CodeRecived.compareTo(code) == 0 )
      return true;
    return false;
  }
  public static Random random = new Random();
  public static String getRandomNumberString() {
    int number = random.nextInt(999999);
    return String.format("%06d", number);
  }
  //---------------------------------------
  @Autowired
  private AuthorityRepository AuhtRepo;
  @Autowired
  private TokenRepository tokenRepo;
  @Autowired
  private JwtService jwtService;
  @GetMapping("/GetNbrUserByRole/{role}")
  public List<Autority> NbrUsers(@PathVariable String role){
    return AuhtRepo.findAllUserByRole(role);
  }

  @GetMapping("/GetConnectedUserNow")
  public List<User> GetConnectedNow(){
    List<Integer> TokenMriglin=tokenRepo.retrieveIdUserConecter();
    List<User> userconnects=new ArrayList<>();
    for (Integer T : TokenMriglin){
      userconnects.add(UserRepo.findById(T).orElse(null));
      System.out.println("ID USER Connected :"+ T);
    }
    for (User u : userconnects){
      System.out.println("Connected :"+u.getEmail());
    }
    return userconnects;
  }

  @GetMapping("/GetConnectedUserNowWithRole/{role}")
  public List<User> GetConnectedRole(@PathVariable String role){
    List<Integer> TokenMriglin=tokenRepo.retrieveIdUserConecter();
    List<User> userconnects=new ArrayList<>();
    List<User> userconnectswithRole=new ArrayList<>();
    for (Integer T : TokenMriglin){
      userconnects.add(UserRepo.findById(T).orElse(null));
      System.out.println("ID USER Connected :"+ T);
    }
    for (User u : userconnects){
      if(u!= null){
        System.out.println("Connected :"+u.getEmail());
        System.out.println("Connected Has Role :"+HasThisRole(u,role));
        if (HasThisRole(u,role)){
          userconnectswithRole.add(u);

        }}

    }
    return userconnectswithRole;
  }

  public static boolean HasThisRole(User u,String Role){
    boolean test=false;
    Set<Autority> auths=u.getAutority();
    for(Autority a : auths){
      System.out.println("♥Here Is the Role :"+a.getName());
      if (a.getName().compareTo(Role) == 0 )
        return true;
    }
    return test;
  }
  // @Scheduled(cron="*/5 * * * * *")
  //@Scheduled(fixedRate = 30000)
  @PutMapping(value = "/WakeUpAccount")
  public void retrieveAndUpdateStatusContrat(){
    Date d = new Date(System.currentTimeMillis());

    List<User> Users=UserRepo.findAll();
    for ( User u: Users)
      if(u.getSleeptime() !=null) {
        long elapsedms = Math.abs(d.getTime() - u.getSleeptime().getTime());
        long diff = TimeUnit.MINUTES.convert(elapsedms, TimeUnit.MILLISECONDS);
        System.out.println("Diference  :" + diff);
        if (diff >= 30) {
          u.setEnabled(false);
          u.setNbrtentatives(0);
          u.setSleeptime(null);
          UserRepo.save(u);
        }
      }
  }
  @GetMapping("/GetAllUser")
  public List<User> GetAllUsers(){
    return UserRepo.findAll();
  }
  @GetMapping("/GetthisUser/{id}")
  public User GetAllUsers(@PathVariable int id){
    return UserRepo.findById(id).orElse(null);
  }
  @PutMapping("/DisableUnDisabe/{id}")
  public String DisabelAccount(@PathVariable int id){
    User u= UserRepo.findById(id).orElse(null);
    u.setEnabled(!u.getEnabled());
    UserRepo.save(u);
    return "Update";
  }

  @GetMapping("/GetbyMail/{email}")
  public User GetUserByMail(@PathVariable  String email){
    return UserRepo.FoundAcountBYMail(email);
  }



}
