package com.bnt.emplyeemodule.testController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bnt.emplyeemodule.controller.EmployeeController;
import com.bnt.emplyeemodule.dto.EmployeeDto;
import com.bnt.emplyeemodule.dto.EmployeeTestDetails;
import com.bnt.emplyeemodule.entity.Employee;
import com.bnt.emplyeemodule.exception.EmployeeNotFoundException;
import com.bnt.emplyeemodule.service.EmployeeService;
import com.bnt.emplyeemodule.service.implementation.EmployeeTestResultServiceImpl;

@SpringBootTest
class EmployeeControllerTest {

	@Mock
	private EmployeeService employeeService;

	@Mock
	private EmployeeTestResultServiceImpl employeeTestResultService;

	@InjectMocks
	private EmployeeController employeeController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testRegisterEmployee_Positive() {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setEmail("test@example.com");
		employeeDto.setPassword("password");

		when(employeeService.register(any(EmployeeDto.class))).thenReturn(new Employee());

		ResponseEntity<String> response = employeeController.registerEmployee(employeeDto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Employee registered successfully.", response.getBody());
	}

	@Test
	void testRegisterEmployee_Negative_EmailExists() {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setEmail("test@example.com");
		employeeDto.setPassword("password");

		when(employeeService.login("test@example.com", "password")).thenReturn(new Employee());

		ResponseEntity<String> response = employeeController.registerEmployee(employeeDto);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Employee with this email already exists.", response.getBody());
	}

	@Test
	void testRegisterEmployee_Negative_InternalServerError() {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setEmail("test@example.com");
		employeeDto.setPassword("password");

		when(employeeService.register(any(EmployeeDto.class))).thenReturn(null);

		ResponseEntity<String> response = employeeController.registerEmployee(employeeDto);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Failed to register employee.", response.getBody());
	}

	@Test
	void testGetEmployeeById_Positive() {
		Long employeeId = 1L;
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(employee));

		ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employee, response.getBody());
	}

	@Test
	void testGetEmployeeById_Negative_NotFound() {
		Long employeeId = 1L;
		when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.empty());

		assertThrows(EmployeeNotFoundException.class, () -> {
			employeeController.getEmployeeById(employeeId);
		});
	}

	@Test
	void testTakeTest_Positive() {
		Long employeeId = 1L;
		Long testId = 1L;

		ResponseEntity<String> response = employeeController.takeTest(employeeId, testId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Test taken successfully", response.getBody());
	}

	@Test
	void testGetTestResultsByEmployeeId_Positive() {
		Long employeeId = 1L;
		EmployeeTestDetails employeeTestDetails = new EmployeeTestDetails();
		when(employeeTestResultService.getTestResultsByEmployeeId(employeeId)).thenReturn(employeeTestDetails);

		ResponseEntity<?> response = employeeController.getTestResultsByEmployeeId(employeeId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employeeTestDetails, response.getBody());
	}

	@Test
	void testGetTestResultsByEmployeeId_Negative_NotFound() {
		Long employeeId = 1L;
		when(employeeTestResultService.getTestResultsByEmployeeId(employeeId)).thenReturn(null);

		ResponseEntity<?> response = employeeController.getTestResultsByEmployeeId(employeeId);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Employee not found", response.getBody());
	}
}
