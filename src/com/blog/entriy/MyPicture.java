package com.blog.entriy;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="myPicture")
public class MyPicture implements Serializable{
	@Id
	@Column(name="pictureId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pictureId;
	@Column(name="pictureName")
	private String pictureName;
	@Column(name="picturePath")
	private String picturePath;
	@Column(name="pictureBuildTime")
	private Timestamp pictureBuildTime;
	
	@ManyToOne(targetEntity=PictureGroup.class)
	@JoinColumn(name="groupId", referencedColumnName="groupId", nullable=false)
	private PictureGroup group;

	public int getPictureId() {
		return pictureId;
	}

	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public Timestamp getPictureBuildTime() {
		return pictureBuildTime;
	}

	public void setPictureBuildTime(Timestamp pictureBuildTime) {
		this.pictureBuildTime = pictureBuildTime;
	}

	public PictureGroup getGroup() {
		return group;
	}

	public void setGroup(PictureGroup group) {
		this.group = group;
	}
	
}
