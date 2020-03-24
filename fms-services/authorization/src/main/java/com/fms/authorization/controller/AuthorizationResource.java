package com.fms.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.authorization.bean.AuthRequest;
import com.fms.authorization.bean.AuthResponse;
import com.fms.authorization.security.TokenProvider;
import com.fms.authorization.service.EmployeeService;



/**
 * @author Kesavalu
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthorizationResource {
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private EmployeeService employeeService;
	
    
	@PostMapping("/token")
	public ResponseEntity<?> login(@RequestBody AuthRequest request){

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		request.getUsername(),
                		request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token,null));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> getTokenAndUser(@RequestBody AuthRequest request){

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		request.getUsername(),
                		request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(jwtTokenUtil.generateTokenWithUser(authentication));
	}

}
