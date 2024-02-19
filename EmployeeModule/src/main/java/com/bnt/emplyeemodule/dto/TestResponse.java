package com.bnt.emplyeemodule.dto;

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
public class TestResponse {
	private Long testId;

	private String title;

	private String description;

	private int maxMarks;

	private int numberofQuestions;

	private boolean active = false;

}
