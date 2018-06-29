package com.yorum.plaka.dao.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.yorum.plaka.dao.pojo.Plate;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Veritabaninda Comment isimli tablonun classtir.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "comment")
public class Comment implements Serializable{
	
	@Column(name="id",nullable = false)
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="comment")
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "plateId", nullable = false)
	private Plate plate;

	public Comment() {
		
	}

	public Comment(int i,String comment,Plate plate ) {
		this.setId(i);
		this.setComment(comment);
		this.setPlate(plate);
	}
	
	@JsonProperty
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@JsonProperty
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonProperty
	public Plate getPlate() {
		return plate;
	}

	public void setPlate(Plate plate) {
		this.plate = plate;
	}
}