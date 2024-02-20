
package com.bnt.emplyeemodule.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import com.bnt.emplyeemodule.dto.EmployeeDto;
import com.bnt.emplyeemodule.dto.TestResponse;
import com.bnt.emplyeemodule.entity.Employee;
import com.bnt.emplyeemodule.feignClient.TestManagementFeignClient;
import com.bnt.emplyeemodule.repository.EmployeeRepo;
import com.bnt.emplyeemodule.repository.EmployeeTestAssociationRepository;
import com.bnt.emplyeemodule.service.implementation.EmployeeServiceImpl;

@SpringBootTest
class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepo employeeRepository;

	@Mock
	private TestManagementFeignClient testManagementFeignClient;

	@Mock
	private EmployeeTestAssociationRepository associationRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Test
	void testRegister_Positive() {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setFirstName("shiv");
		employeeDto.setLastName("shinde");
		employeeDto.setEmail("shivshinde@example.com");
		employeeDto.setPassword("shiv");
		Employee savedEmployee = new Employee();
		savedEmployee.setEmployeeId(1L);
		savedEmployee.setFirstName("shiv");
		savedEmployee.setLastName("shinde");
		savedEmployee.setEmail("shivshinde@example.com");
		savedEmployee.setPassword("shiv");
		when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
		Employee result = employeeService.register(employeeDto);
		assertNotNull(result);
		assertEquals("shiv", result.getFirstName());
		assertEquals("shinde", result.getLastName());
		assertEquals("shivshinde@example.com", result.getEmail());
		assertEquals("shiv", result.getPassword());
	}

	@Test
	void testGetEmployeeById_Positive() {
		Long employeeId = 1L;
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
		Optional<Employee> result = employeeService.getEmployeeById(employeeId);
		assertTrue(result.isPresent());
		assertEquals(employeeId, result.get().getEmployeeId());
	}

	@Test
    void testGetEmployeeById_Negative_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> {
            employeeService.getEmployeeById(1L);
        }, "EmployeeNotFoundException was thrown unexpectedly");
    }

	@Test
	void testGetAllEmployees_Positive() {
		List<Employee> employees = new ArrayList<>();
		Employee employee1 = new Employee();
		employee1.setEmployeeId(1L);
		Employee employee2 = new Employee();
		employee2.setEmployeeId(2L);
		employees.add(employee1);
		employees.add(employee2);
		when(employeeRepository.findAll()).thenReturn(employees);
		List<Employee> result = employeeService.getAllEmployees();
		assertNotNull(result);
		assertEquals(2, result.size());
	}

	@Test
	void testCreateEmployee_Positive() {
		Employee employee = new Employee();
		employee.setFirstName("shiv");
		Employee savedEmployee = new Employee();
		savedEmployee.setEmployeeId(1L);
		savedEmployee.setFirstName("shiv");
		when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
		Employee result = employeeService.createEmployee(employee);
		assertNotNull(result);
		assertEquals(1L, result.getEmployeeId());
		assertEquals("shiv", result.getFirstName());
	}

	@Test
	void testUpdateEmployee_Positive() {
		Long employeeId = 1L;
		Employee existingEmployee = new Employee();
		existingEmployee.setEmployeeId(employeeId);
		existingEmployee.setFirstName("shiv");
		Employee newEmployeeData = new Employee();
		newEmployeeData.setFirstName("shivansh");
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
		when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployeeData);
		Employee result = employeeService.updateEmployee(employeeId, newEmployeeData);
		assertNotNull(result);
		assertEquals("shivansh", result.getFirstName());
	}

	@Test
	void testDeleteEmployee_Positive() {
		Long employeeId = 1L;
		Employee existingEmployee = new Employee();
		existingEmployee.setEmployeeId(employeeId);
		existingEmployee.setFirstName("John");
		when(employeeRepository.existsById(employeeId)).thenReturn(true);
		assertDoesNotThrow(() -> {
			employeeService.deleteEmployee(employeeId);
		});
	}

	@Test
	void testTakeTest_Positive() {
		Long employeeId = 1L;
		Long testId = 1L;
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
		ResponseEntity<TestResponse> responseEntity = new ResponseEntity<>(new TestResponse(), HttpStatus.OK);
		when(testManagementFeignClient.getTestById(testId)).thenReturn(responseEntity);
		assertDoesNotThrow(() -> {
			employeeService.takeTest(employeeId, testId);
		});
	}
}
