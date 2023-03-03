package it.crud.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseJoinTeacherDto {

	@JsonProperty("id")
	private int id;

	@JsonProperty("subject")
	private String subject;

	@JsonProperty("hourAmount")
	private double hourAmount;

	@JsonProperty("done")
	private boolean isDone;

	@JsonProperty("teacherId")
	private int teacherId;

	@JsonProperty("teacherName")
	private String teacherName;

	@JsonProperty("teacherSurname")
	private String teacherSurname;

}
