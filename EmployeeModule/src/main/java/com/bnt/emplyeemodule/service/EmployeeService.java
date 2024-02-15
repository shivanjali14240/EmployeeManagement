package com.bnt.emplyeemodule.service;

import java.util.List;
import java.util.Optional;

import com.bnt.emplyeemodule.dto.EmployeeDto;
import com.bnt.emplyeemodule.entity.Employee;

public interface EmployeeService {
	List<Employee> getAllEmployees();

	Optional<Employee> getEmployeeById(Long id);

	Employee createEmployee(Employee employee);

	Employee updateEmployee(Long id, Employee newEmployeeData);

	void deleteEmployee(Long id);
	
	Employee register(EmployeeDto employeeDto);

	Employee login(String email, String password);
}
