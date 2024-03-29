package com.bnt.emplyeemodule.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnt.emplyeemodule.dto.EmployeeDto;
import com.bnt.emplyeemodule.dto.EmployeeTestDetails;
import com.bnt.emplyeemodule.entity.Employee;
import com.bnt.emplyeemodule.exception.EmployeeNotFoundException;
import com.bnt.emplyeemodule.service.EmployeeService;
import com.bnt.emplyeemodule.service.implementation.EmployeeTestResultServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/employeeModule/api/v1/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeTestResultServiceImpl employeeTestResultService;

	@PostMapping("/register")
	public ResponseEntity<String> registerEmployee(@RequestBody EmployeeDto employeeDto) {
		log.info("Received registration request for employee: {}", employeeDto.getEmail());

		Employee existingEmployee = employeeService.login(employeeDto.getEmail(), employeeDto.getPassword());
		if (existingEmployee != null) {
			log.warn("Registration failed: Employee with email {} already exists.", employeeDto.getEmail());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee with this email already exists.");

		}

		Employee registeredEmployee = employeeService.register(employeeDto);
		if (registeredEmployee != null) {
			log.info("Employee registered successfully: {}", registeredEmployee);
			return ResponseEntity.ok("Employee registered successfully.");
		} else {
			log.error("Failed to register employee.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register employee.");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginEmployee(@RequestBody EmployeeDto employeeDto) {
		log.info("Received login request for employee: {}", employeeDto.getEmail());
		Employee employee = employeeService.login(employeeDto.getEmail(), employeeDto.getPassword());
		if (employee != null) {
			log.info("Login successful for employee: {}", employee.getEmail());
			return ResponseEntity.ok(employee);
		} else {
			log.warn("Login failed for email: {}", employeeDto.getEmail());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
		}
	}

	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		log.info("Request received to get all employees");
		List<Employee> employees = employeeService.getAllEmployees();
		log.info("Returning {} employees", employees.size());
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		log.info("Request received to get employee with ID: {}", id);
		Optional<Employee> employee = employeeService.getEmployeeById(id);
		if (employee.isPresent()) {
			log.info("Returning employee with ID: {}", id);
			return ResponseEntity.ok(employee.get());
		} else {
			throw new EmployeeNotFoundException("Employee not found with ID: " + id);
		}
	}

	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		log.info("Request received to create employee", employee);
		Employee createdEmployee = employeeService.createEmployee(employee);
		log.info("Employee created with ID: {}", createdEmployee);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee newEmployeeData) {
		log.info("Request received to update employee with ID: {}", id);
		Employee updatedEmployee = employeeService.updateEmployee(id, newEmployeeData);
		log.info("Employee with ID: {} updated", updatedEmployee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
		log.info("Request received to delete employee with ID: {}", id);
		employeeService.deleteEmployee(id);
		log.info("Employee with ID: {} deleted", id);
		return ResponseEntity.ok("Employee deleted successfully");
	}

	@PostMapping("/{employeeId}/{testId}")
	public ResponseEntity<String> takeTest(@PathVariable Long employeeId, @PathVariable Long testId) {
		try {
			employeeService.takeTest(employeeId, testId);
			log.info("Request received to testmanagement with test id: {}", testId);
			return ResponseEntity.ok("Test taken successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/{employeeId}/testResult")
	public ResponseEntity<?> getTestResultsByEmployeeId(@PathVariable Long employeeId) {
		try {
			EmployeeTestDetails employeeTestDetails = employeeTestResultService.getTestResultsByEmployeeId(employeeId);
			if (employeeTestDetails == null) {
				return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
			}
			log.info("get test result by employee id: {}", employeeTestDetails);
			return new ResponseEntity<>(employeeTestDetails, HttpStatus.OK);
		} catch (Exception e) {
			log.error("An unexpected error occurred: {}", e.getMessage());
			return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
		log.info("Employee not found: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		log.error("An unexpected error occurred: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
	}

}
