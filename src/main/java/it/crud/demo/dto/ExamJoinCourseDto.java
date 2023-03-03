package it.crud.demo.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamJoinCourseDto {

	private int id;
	private Date day;
	private LocalTime hour;
	private int vote;
	private String classroom;
	private boolean isDone;
	private int courseId;
	private String courseSubject;
	private String teacherName;
	private String teacherSurname;

	
}
