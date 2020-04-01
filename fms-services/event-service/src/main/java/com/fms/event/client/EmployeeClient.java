/**
 * 
 */
package com.fms.event.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fms.event.model.Employee;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kesavalu
 *
 */
@Component
@Slf4j
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
	        	String employeeServiceUrl = instanceInfo.getPort()==443?"https://":"http://" + instanceInfo.getAppName()+":"+ instanceInfo.getPort()+"/employees-api/employees/{employeeName}";
	            ResponseEntity<Employee> response = this.template.getForEntity(employeeServiceUrl, Employee.class, employeeName);
	            return response.getBody();
	        } catch (HttpClientErrorException e) {
	        	log.error(e.getMessage());
	            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
	                return null;
	            }
	        }
	        return null;
	  }


	/**
	 * @param employeeIds
	 * @return
	 */
	public List<Employee> findEmployeeByIds(List<Long> employeeIds) {
		  
		  Application application = eurekaClient.getApplication(employeeServiceName);
	        InstanceInfo instanceInfo = application.getInstances().get(0);
	        try {
	        	String employeeServiceUrl = instanceInfo.getPort()==443?"https://":"http://" + instanceInfo.getAppName()+":"+ instanceInfo.getPort()+"/employees-api/employeeslist";
	            ResponseEntity<Employee[]> response = this.template.postForEntity(employeeServiceUrl,employeeIds, Employee[].class);
	            return Arrays.asList(response.getBody());
	        } catch (HttpClientErrorException e) {
	        	log.error(e.getMessage());
	            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
	                return null;
	            }
	        }
	        return null;
	  }    
}
