package com.fms.authorization.bean;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthResponse {
    

	private @NonNull String token;
	
	private UserDetails user;

    
}