package com.bnt.emplyeemodule.service;

import java.util.List;
import java.util.Optional;

import com.bnt.emplyeemodule.entity.Employee;
import com.entity.TestManagement;

public interface EmployeeService {
	List<Employee> getAllEmployees();

	Optional<Employee> getEmployeeById(Long id);

	Employee createEmployee(Employee employee);

	Employee updateEmployee(Long id, Employee newEmployeeData);

	void deleteEmployee(Long id);

	public List<TestManagement> getAvailableTests(Long employeeId);

	public void takeTest(Long employeeId, Long testId);
}
