package com.bnt.emplyeemodule.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bnt.emplyeemodule.dto.EmployeeTestDetails;
import com.bnt.emplyeemodule.dto.TestResponse;
import com.bnt.emplyeemodule.entity.Employee;
import com.bnt.emplyeemodule.entity.EmployeeTestAssociation;
import com.bnt.emplyeemodule.feignClient.TestManagementFeignClient;
import com.bnt.emplyeemodule.repository.EmployeeRepo;
import com.bnt.emplyeemodule.repository.EmployeeTestAssociationRepository;
import com.bnt.emplyeemodule.service.EmployeeTestResultService;

@Service
public class EmployeeTestResultServiceImpl implements EmployeeTestResultService {

	@Autowired
	private EmployeeTestAssociationRepository employeeTestAssociationRepository;

	@Autowired
	private TestManagementFeignClient testManagementFeignClient;

	@Autowired
	private EmployeeRepo employeeRepository;

	@Override
	public EmployeeTestDetails getTestResultsByEmployeeId(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElse(null);
		if (employee == null) {
			return null;
		}

		List<EmployeeTestAssociation> associations = employeeTestAssociationRepository.findByEmployeeId(employeeId);
		List<TestResponse> results = new ArrayList<>();

		for (EmployeeTestAssociation association : associations) {
			TestResponse testResult = new TestResponse();
			testResult.setTestId(association.getTestId());

			ResponseEntity<TestResponse> testDetailsResponse = testManagementFeignClient
					.getTestById(association.getTestId());
			if (testDetailsResponse != null && testDetailsResponse.getBody() != null) {
				TestResponse testData = testDetailsResponse.getBody();
				testResult.setTitle(testData.getTitle());
				testResult.setMaxMarks(testData.getMaxMarks());
				testResult.setDescription(testData.getDescription());
				testResult.setNumberofQuestions(testData.getNumberofQuestions());
			} else {
				testResult.setTitle("Test Details Not Found");
			}
			results.add(testResult);
		}

		EmployeeTestDetails employeeTestDetails = new EmployeeTestDetails();
		employeeTestDetails.setEmployee(employee);
		employeeTestDetails.setTestResults(results);

		return employeeTestDetails;
	}

}