package com.bnt.emplyeemodule.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import com.bnt.emplyeemodule.dto.EmployeeTestDetails;
import com.bnt.emplyeemodule.dto.TestResponse;
import com.bnt.emplyeemodule.entity.Employee;
import com.bnt.emplyeemodule.entity.EmployeeTestAssociation;
import com.bnt.emplyeemodule.feignClient.TestManagementFeignClient;
import com.bnt.emplyeemodule.repository.EmployeeRepo;
import com.bnt.emplyeemodule.repository.EmployeeTestAssociationRepository;
import com.bnt.emplyeemodule.service.implementation.EmployeeTestResultServiceImpl;

@SpringBootTest
class EmployeeTestResultServiceTest {

	@Mock
	private EmployeeTestAssociationRepository employeeTestAssociationRepository;

	@Mock
	private TestManagementFeignClient testManagementFeignClient;

	@Mock
	private EmployeeRepo employeeRepository;

	@InjectMocks
	private EmployeeTestResultServiceImpl employeeTestResultService;

	@Test
	void testGetTestResultsByEmployeeId_Positive() {
		Long employeeId = 1L;
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		List<EmployeeTestAssociation> associations = new ArrayList<>();
		EmployeeTestAssociation association = new EmployeeTestAssociation();
		association.setEmployeeId(employeeId);
		association.setTestId(1L);
		associations.add(association);
		TestResponse testResponse = new TestResponse();
		testResponse.setTitle("Test Title");
		testResponse.setMaxMarks(100);
		ResponseEntity<TestResponse> testDetailsResponse = new ResponseEntity<>(testResponse, HttpStatus.OK);
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
		when(employeeTestAssociationRepository.findByEmployeeId(employeeId)).thenReturn(associations);
		when(testManagementFeignClient.getTestById(association.getTestId())).thenReturn(testDetailsResponse);
		EmployeeTestDetails employeeTestDetails = employeeTestResultService.getTestResultsByEmployeeId(employeeId);
		assertNotNull(employeeTestDetails);
		assertEquals(employee, employeeTestDetails.getEmployee());
		assertFalse(employeeTestDetails.getTestResults().isEmpty());
		TestResponse testResult = employeeTestDetails.getTestResults().get(0);
		assertEquals("Test Title", testResult.getTitle());
		assertEquals(100, testResult.getMaxMarks());
	}

	@Test
	void testGetTestResultsByEmployeeId_Negative_EmployeeNotFound() {
		Long employeeId = 1L;
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
		EmployeeTestDetails employeeTestDetails = employeeTestResultService.getTestResultsByEmployeeId(employeeId);
		assertNull(employeeTestDetails);
	}

}
