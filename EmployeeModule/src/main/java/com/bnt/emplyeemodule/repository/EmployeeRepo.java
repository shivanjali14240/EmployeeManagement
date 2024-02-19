package com.bnt.emplyeemodule.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bnt.emplyeemodule.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	Employee findByEmail(String email);

	Employee save(Optional<Employee> employee);

}
