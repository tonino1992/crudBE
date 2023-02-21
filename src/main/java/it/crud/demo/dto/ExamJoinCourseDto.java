package it.crud.demo.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamJoinCourseDto {

	private int id;
	private Date day;
	private Time hour;
	private int vote;
	private String classroom;
	private int courseId;
	private String courseSubject;
	private String teacherName;
	private String teacherSurname;

	
}
