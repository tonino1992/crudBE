package it.crud.demo.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto {
	
	private int id;
	private Date day;
	private Time hour;
	private String classroom;
	private boolean isDone;
	private int courseId;
	


}
