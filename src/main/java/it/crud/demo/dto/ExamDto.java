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
public class ExamDto {
	
	private int id;
	private Date day;
	private LocalTime hour;
	private String classroom;
	private boolean isDone;
	private int courseId;

}
