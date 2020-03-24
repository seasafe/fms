/**
 * 
 */
package com.fms.authorization.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fms.authorization.model.Employee;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

/**
 * @author Kesavalu
 *
 */
@Component
public class EmployeeClient {
	
	
	@Value("${employee.service.name}")
	private String employeeServiceName;
	@Autowired
	private RestTemplate template;
	
	@Autowired
    private EurekaClient eurekaClient;

	
	  public Employee findByEmployeeName(String employeeName) {
		  
		  Application application = eurekaClient.getApplication(employeeServiceName);
	        InstanceInfo instanceInfo = application.getInstances().get(0);
	        try {
	            ResponseEntity<Employee> response = this.template.getForEntity(instanceInfo.getSecurePort()==1?"https://":"http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/employees/{employeeName}", Employee.class, employeeName);
	            return response.getBody();
	        } catch (HttpClientErrorException e) {
	            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
	                return null;
	            }
	        }
	        return null;
	  }    
}
