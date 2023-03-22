package it.crud.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentExamDto {

	LocalDate bookingDate;
	int vote;
	private int studentId;
	private String studentName;
	private String studentSurname;
	private int examId;
	private String teacherName;
	private String teacherSurname;
	private String subject;
	
}
