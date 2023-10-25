package com.example.cocomarket.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {

  private Integer id;
  private String last_name;
  private String first_name;
  private Boolean premium;//par default 0
  private String email;
  private Float loyalty_point;
  private String Assosiation_info;
  private String Files;
  @Lob
  // @JsonIgnore
  private String img;
  private String region;
  private Boolean enabled;
  private Integer nbr_tentatives;
  private Boolean availability;
  private Date sleep_time;
  private String num_phone;
}
