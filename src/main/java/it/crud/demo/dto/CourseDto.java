package it.crud.demo.dto;

public class CourseDto {

	private int id;
	private String subject;
	private double hourAmount;
	private int teacherId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public double getHourAmount() {
		return hourAmount;
	}
	public void setHourAmount(double hourAmount) {
		this.hourAmount = hourAmount;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	
	
}
