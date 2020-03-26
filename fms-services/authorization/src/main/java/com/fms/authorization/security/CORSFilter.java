package com.fms.authorization.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CORSFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if ("OPTIONS".equals(request.getMethod())) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token, x-emp-id");
			response.setHeader("Access-Control-Expose-Headers", "xsrf-token,x-emp-id");
			response.setStatus(HttpServletResponse.SC_OK);
			
		} else {
			filterChain.doFilter(request, new HttpServletResponseWrapper(response) {
				public void setHeader(String name, String value) {
					logger.debug("setHeader(" + name + "," + value + ")");

					if (!name.equalsIgnoreCase("Access-Control-Allow-Origin")) {
						super.setHeader(name, "*");
					} else if (!name.equalsIgnoreCase("Access-Control-Allow-Methods")) {
						super.setHeader(name, "GET, POST, PUT, DELETE, OPTIONS");
					} else if (!name.equalsIgnoreCase("Access-Control-Max-Age")) {
						super.setHeader(name, "3600");
					} else if (!name.equalsIgnoreCase("Access-Control-Allow-Headers")) {
						super.setHeader(name, "authorization, content-type, xsrf-token, x-emp-id");
					} else if (!name.equalsIgnoreCase("Access-Control-Expose-Headers")) {
						super.setHeader(name, "xsrf-token,x-emp-id");
					}
				}
			});
		}

	}

}