package com.fms.employee.security;

import static com.fms.employee.security.SecurityConstants.HEADER_EMPLOYEE_STRING;
import static com.fms.employee.security.SecurityConstants.HEADER_STRING;
import static com.fms.employee.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fms.employee.exception.CustomException;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	private EurekaClient eurekaClient;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		
		String header = req.getHeader(HEADER_STRING) != null ? req.getHeader(HEADER_STRING)
				: req.getHeader("authorization");
		String headerEmployeeId = req.getHeader(HEADER_EMPLOYEE_STRING);
		String username = null;
		String authToken = null;
		if (header != null && header.startsWith(TOKEN_PREFIX)) {
			authToken = header.replace(TOKEN_PREFIX, "");
			try {
				username = jwtTokenUtil.getEmployeeIdFromToken(authToken);
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
				throw new CustomException("an error occured during getting username from token",
						HttpStatus.UNAUTHORIZED);
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
				throw new CustomException("the token is expired and not valid anymore", HttpStatus.UNAUTHORIZED);
			} catch (SignatureException e) {
				logger.error("Authentication Failed. Username or Password not valid.");
				throw new CustomException("Authentication Failed. Username or Password not valid.",
						HttpStatus.UNAUTHORIZED);
			}
		} else {
			Application application = eurekaClient.getApplication("employee-service");
			InstanceInfo instanceInfo = application.getInstances().get(0);
			if (req.getServerName().equalsIgnoreCase(instanceInfo.getHostName())) {
				logger.info("JWT authentication skipped for login");
			} else {
				throw new CustomException("couldn't find bearer string, will ignore the header",
						HttpStatus.UNAUTHORIZED);
			}
		}
		if (username != null && headerEmployeeId != null) {

			UserDetails userDetails = new AuthenticatedUser(username, authToken, null);

			if (jwtTokenUtil.validateToken(authToken, headerEmployeeId)) {
				UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken,
						userDetails);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				logger.info("authenticated user " + username + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(req, res);
	}
}