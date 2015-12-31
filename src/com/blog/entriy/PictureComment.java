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
import javax.persistence.Table;

import com.sun.org.glassfish.external.statistics.TimeStatistic;

@Entity
@Table(name="pictureComment")
public class PictureComment implements Serializable{
	@Id
	@Column(name="commentId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int commentId;
	@Column(name="commentContent")
	private String commentContent;
	@Column(name="commentPeopleContact")
	private String commentPeopleContact;
	@Column(name="commentTime")
	private Timestamp commentTime;
	
	@ManyToOne(targetEntity=PictureGroup.class)
	@JoinColumn(name="groupId", referencedColumnName="groupId", nullable=false)
	private PictureGroup group;
}
