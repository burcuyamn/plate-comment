package com.yorum.plaka.dao.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Veritabaninda Plate isimli tablonun classtir.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "plate")
public class Plate implements Serializable{

	@Column(name="id",nullable = false)
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="plate", unique = true)
	private String plate;

	public Plate(int i, String string) {
		this.setId(i);
		this.setPlate(string);
	}
	
	public Plate() {}
	
	@JsonProperty
	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}
	@JsonProperty
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}