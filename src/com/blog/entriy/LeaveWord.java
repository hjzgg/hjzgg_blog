package com.blog.entriy;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="LeaveWord")
public class LeaveWord implements Serializable{
	@Id
	@Column(name="wordId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int wordId;
	@Column(name="wordContent")
	private String wordContent;
	@Column(name="wordTime")
	@OrderBy("wordTime ASC")
	private Timestamp wordTime;
	public int getWordId() {
		return wordId;
	}
	public void setWordId(int wordId) {
		this.wordId = wordId;
	}
	public String getWordContent() {
		return wordContent;
	}
	public void setWordContent(String wordContent) {
		this.wordContent = wordContent;
	}
	public Timestamp getWordTime() {
		return wordTime;
	}
	public void setWordTime(Timestamp wordTime) {
		this.wordTime = wordTime;
	}
}
