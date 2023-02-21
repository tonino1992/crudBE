package it.crud.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseJoinTeacherDto {

	private int id;
	private String subject;
	private double hourAmount;
	private int teacherId;
	private String teacherName;
	private String teacherSurname;

	
}
