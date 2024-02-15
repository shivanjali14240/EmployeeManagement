package com.bnt.emplyeemodule.testController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bnt.emplyeemodule.controller.EmployeeController;
import com.bnt.emplyeemodule.entity.Employee;
import com.bnt.emplyeemodule.exception.EmployeeNotFoundException;
import com.bnt.emplyeemodule.service.EmployeeService;

@SpringBootTest
public class EmployeeControllerTest {

	@Mock
	private EmployeeService employeeService;

	@InjectMocks
	private EmployeeController employeeController;

	@Test
	void testGetAllEmployees_PositiveCase() {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1L, "shiv", "shinde", "shiv@example.com", "shiv@12"));
		employees.add(new Employee(2L, "shivanya", "Sawant", "shivanya@example.com", "shivnya@23"));
		when(employeeService.getAllEmployees()).thenReturn(employees);
		ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

	@Test
	void testGetEmployeeById_PositiveCase() {
		Long id = 1L;
		Employee employee = new Employee(1L, "shiv", "shinde", "shiv@example.com", "shiv@12");
		when(employeeService.getEmployeeById(id)).thenReturn(Optional.of(employee));
		ResponseEntity<Employee> response = employeeController.getEmployeeById(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employee, response.getBody());
	}

	@Test
	void testGetEmployeeById_NegativeCase() {
		Long id = 1L;
		when(employeeService.getEmployeeById(id)).thenReturn(Optional.empty());
		EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> {
			employeeController.getEmployeeById(id);
		});
		assertEquals("Employee not found with ID: " + id, exception.getMessage());
	}

	@Test
	void testCreateEmployee_PositiveCase() {
		Employee employee = new Employee(1L, "shiv", "shinde", "shiv@example.com", "shiv@12");
		when(employeeService.createEmployee(employee)).thenReturn(employee);
		ResponseEntity<Employee> response = employeeController.createEmployee(employee);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(employee, response.getBody());
	}

	@Test
	void testUpdateEmployee_PositiveCase() {
		Long id = 1L;
		Employee existingEmployee = new Employee(1L, "shiv", "shinde", "shiv@example.com", "shiv@12");
		Employee updatedEmployeeData = new Employee(2L, "shivanya", "Sawant", "shivanya@example.com", "shivnya@23");
		when(employeeService.updateEmployee(id, updatedEmployeeData)).thenReturn(updatedEmployeeData);
		ResponseEntity<Employee> response = employeeController.updateEmployee(id, updatedEmployeeData);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedEmployeeData, response.getBody());
	}

	@Test
	void testDeleteEmployee_PositiveCase() {
		Long id = 1L;
		ResponseEntity<String> response = employeeController.deleteEmployee(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Employee deleted successfully", response.getBody());
	}

	@Test
	void testHandleEmployeeNotFoundException() {
		EmployeeNotFoundException exception = new EmployeeNotFoundException("Employee not found");
		ResponseEntity<String> response = employeeController.handleEmployeeNotFoundException(exception);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Employee not found", response.getBody());
	}

	@Test
	void testHandleGenericException() {
		Exception exception = new Exception("Internal Server Error");
		ResponseEntity<String> response = employeeController.handleGenericException(exception);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("An unexpected error occurred", response.getBody());
	}
}
