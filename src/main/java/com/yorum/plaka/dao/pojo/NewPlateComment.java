package com.yorum.plaka.dao.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * plaka ve yorum iceren classtir.
 */
public class NewPlateComment {

	private String plate;
	private String comment;
	
	public NewPlateComment() {
		
	}
	
	public NewPlateComment(String plate, String comment) {
		this.setPlate(plate);
		this.setComment(comment);
	}
	
	@JsonProperty
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	@JsonProperty
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}