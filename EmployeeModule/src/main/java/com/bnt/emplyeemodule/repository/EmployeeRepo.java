package com.bnt.emplyeemodule.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bnt.emplyeemodule.entity.Employee;
import com.entity.TestManagement;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
	List<TestManagement> findTestsByEmployeeId(Long employeeId);

	void save(Optional<Employee> employee);

}
