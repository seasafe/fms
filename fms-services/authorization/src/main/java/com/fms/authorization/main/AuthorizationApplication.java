package com.fms.authorization.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.fms.authorization.zuul.ErrorFilter;
import com.fms.authorization.zuul.PostFilter;
import com.fms.authorization.zuul.PreFilter;
import com.fms.authorization.zuul.RouteFilter;

/**
 * @author Kesavalu
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@ComponentScan(basePackages = {"com.fms.authorization"})
@EnableZuulProxy
public class AuthorizationApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuthorizationApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	 	@Bean
	    public PreFilter preFilter() {
	        return new PreFilter();
	    }
	    @Bean
	    public PostFilter postFilter() {
	        return new PostFilter();
	    }
	    @Bean
	    public ErrorFilter errorFilter() {
	        return new ErrorFilter();
	    }
	    @Bean
	    public RouteFilter routeFilter() {
	        return new RouteFilter();
	    }
}
