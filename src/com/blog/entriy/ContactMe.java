package com.blog.entriy;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contactMe")
public class ContactMe implements Serializable{
	@Id
	@Column(name="contactId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int contactId;
	@Column(name="fromName")
	private String fromName;
	@Column(name="fromEmail")
	private String fromEmail;
	@Column(name="contactCotent")
	private String contactCotent;
	@Column(name="contactTime")
	private Timestamp contactTime;
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public String getContactCotent() {
		return contactCotent;
	}
	public void setContactCotent(String contactCotent) {
		this.contactCotent = contactCotent;
	}
	public Timestamp getContactTime() {
		return contactTime;
	}
	public void setContactTime(Timestamp contactTime) {
		this.contactTime = contactTime;
	}
	
}
