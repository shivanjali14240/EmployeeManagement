package com.bnt.emplyeemodule.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnt.emplyeemodule.dto.EmployeeDto;
import com.bnt.emplyeemodule.entity.Employee;
import com.bnt.emplyeemodule.exception.EmployeeNotFoundException;
import com.bnt.emplyeemodule.repository.EmployeeRepo;
import com.bnt.emplyeemodule.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepository;

	@Override
	public Employee register(EmployeeDto employeeDto) {
		Employee employee = new Employee();
		employee.setFirstName(employeeDto.getFirstName());
		employee.setLastName(employeeDto.getLastName());
		employee.setEmail(employeeDto.getEmail());
		employee.setPassword(employeeDto.getPassword());
		return employeeRepository.save(employee);
	}

	@Override
	public Employee login(String email, String password) {
		Employee employee = employeeRepository.findByEmail(email);
		if (employee != null && employee.getPassword().equals(password)) {
			return employee;
		} else {
			return null;
		}
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
