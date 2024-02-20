package com.bnt.emplyeemodule.dto;

import java.util.List;

import com.bnt.emplyeemodule.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeTestDetails {
	private Employee employee;
	private List<TestResponse> testResults;

}
