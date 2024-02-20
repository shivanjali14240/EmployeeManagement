package com.bnt.emplyeemodule.service;

import com.bnt.emplyeemodule.dto.EmployeeTestDetails;

public interface EmployeeTestResultService {
	public EmployeeTestDetails getTestResultsByEmployeeId(Long employeeId);
}


