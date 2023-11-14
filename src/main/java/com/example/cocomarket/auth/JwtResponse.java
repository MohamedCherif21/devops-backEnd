package com.example.cocomarket.auth;

import com.example.cocomarket.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private User user;
    private String jwtToken;
    private String msg;
}
