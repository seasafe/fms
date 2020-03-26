/**
 * 
 */
package com.fms.event.security;

/**
 * @author Kesavalu
 *
 */
public class SecurityConstants {

	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
	public static final String SIGNING_KEY = "kesavalu";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String AUTHORITIES_KEY = "scopes";
	public static final String HEADER_EMPLOYEE_STRING= "X-EMP-ID";
}
