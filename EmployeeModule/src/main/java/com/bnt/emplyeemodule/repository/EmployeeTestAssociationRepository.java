package com.bnt.emplyeemodule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bnt.emplyeemodule.entity.EmployeeTestAssociation;

@Repository
public interface EmployeeTestAssociationRepository extends JpaRepository<EmployeeTestAssociation, Long> {

	List<EmployeeTestAssociation> findByEmployeeId(Long employeeId);

}
