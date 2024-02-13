package com.bnt.emplyeemodule.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnt.emplyeemodule.entity.Employee;
import com.bnt.emplyeemodule.exception.EmployeeNotFoundException;
import com.bnt.emplyeemodule.repository.EmployeeRepo;
import com.bnt.emplyeemodule.service.EmployeeService;
import com.entity.TestManagement;
import com.repository.TestRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepository;

	@Autowired
	private TestRepository testManagementRepository;

	public List<TestManagement> getAvailableTests(Long employeeId) {
		Optional<Employee> employee = getEmployeeById(employeeId);
		return employee.get().getTests();
	}

	public void takeTest(Long employeeId, Long testId) {
		Optional<Employee> employee = getEmployeeById(employeeId);
		TestManagement test = getTestById(testId);
		List<TestManagement> testsTaken = employee.get().getTests();
		testsTaken.add(test);
		employee.orElseThrow().setTests(testsTaken);
		employeeRepository.save(employee);
	}

	private TestManagement getTestById(Long testId) {
		Optional<TestManagement> testOptional = testManagementRepository.findById(testId);
		return testOptional.orElseThrow(() -> new IllegalArgumentException("Test not found with ID: " + testId));
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Optional<Employee> getEmployeeById(Long id) {
		return employeeRepository.findById(id);
	}

	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public Employee updateEmployee(Long id, Employee newEmployeeData) {
		return employeeRepository.findById(id).map(employee -> {
			newEmployeeData.setEmployee_id(employee.getEmployee_id());
			return employeeRepository.save(newEmployeeData);
		}).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id));
	}

	public void deleteEmployee(Long id) {
		if (employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
		} else {
			throw new EmployeeNotFoundException("Employee not found with ID: " + id);
		}
	}
}
