package com.blog.entriy;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="pictureGroup")
public class PictureGroup implements Serializable{
	@Id
	@Column(name="groupId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int groupId;
	@Column(name="groupDescrib")
	private String groupDescrib;
	@Column(name="groupBuildTime")
	private Timestamp groupBuildTime;
	@Column(name="groupName")
	private String groupName;
	
	@OneToMany(targetEntity=MyPicture.class, mappedBy="group", fetch=FetchType.EAGER, cascade={CascadeType.REMOVE, CascadeType.MERGE})
	@OrderBy("pictureBuildTime ASC")
	private Set<MyPicture> pictures = new HashSet<MyPicture>();
	
	@OneToMany(targetEntity=PictureComment.class, mappedBy="group", fetch=FetchType.EAGER, cascade={CascadeType.REMOVE, CascadeType.MERGE})
	private Set<PictureComment> comments = new HashSet<PictureComment>();

	public int getGroupId() {
		return groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Set<PictureComment> getComments() {
		return comments;
	}

	public void setComments(Set<PictureComment> comments) {
		this.comments = comments;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupDescrib() {
		return groupDescrib;
	}

	public void setGroupDescrib(String groupDescrib) {
		this.groupDescrib = groupDescrib;
	}

	public Timestamp getGroupBuildTime() {
		return groupBuildTime;
	}

	public void setGroupBuildTime(Timestamp groupBuildTime) {
		this.groupBuildTime = groupBuildTime;
	}

	public Set<MyPicture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<MyPicture> pictures) {
		this.pictures = pictures;
	}
}
