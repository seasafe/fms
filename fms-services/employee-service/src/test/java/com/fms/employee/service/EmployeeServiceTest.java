/**
 * 
 */
package com.fms.employee.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import com.fms.employee.EmployeeServiceApplication;
import com.fms.employee.EmployeeServiceApplicationTests;

/**
 * @author kesah
 *
 */
class EmployeeServiceTest extends EmployeeServiceApplicationTests {

	@Autowired
	EmployeeService empService;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.fms.employee.service.EmployeeService#getEmployee(java.lang.String)}.
	 */
	@Test
	void testGetEmployee() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.fms.employee.service.EmployeeService#saveEmployee(com.fms.employee.model.Employee)}.
	 */
	@Test
	void testSaveEmployee() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.fms.employee.service.EmployeeService#getAllEmployees(org.springframework.data.domain.Pageable)}.
	 */
	@Test
	void testGetAllEmployees() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.fms.employee.service.EmployeeService#getEmployeeByRole()}.
	 */
	@SuppressWarnings("deprecation")
	@Test
	void testGetEmployeeByRole() {
		Pageable pageable = Pageable.unpaged();
		var test = empService.getEmployeeByRole("PMO",pageable);
		Assert.notNull(test);
	}
	
	@Test
	void testAssignRole() {
		empService.assignRole("kesa.hari1@gmail.com", "PMO", false);
	}

}
