package com.example.cocomarket.config;

import com.example.cocomarket.Entity.Comentaire;
import com.example.cocomarket.Interfaces.IComentaire;
import com.example.cocomarket.Repository.User_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {


  private final User_Repository repository;
  @Bean
  public IComentaire responseService(){
    return new IComentaire() {
      @Override
      public List<Comentaire> getAllResponses() {
        return null;
      }

      @Override
      public Optional<Comentaire> getResponseById(Integer id) {
        return Optional.empty();
      }

      @Override
      public Comentaire addResponse(Comentaire comentaire) {
        return null;
      }

      @Override
      public void deleteResponse(Integer id) {

      }
    };
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> repository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


}
