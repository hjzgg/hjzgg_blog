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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="blogGroup")
public class BlogGroup implements Serializable{
	@Id
	@Column(name="groupId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int groupId;
	@Column(name="groupName")
	private String groupName;
	@Column(name="groupDescrib")
	private String groupDescrib;
	@Column(name="groupBuildTime")
	private Timestamp groupBuildTime; 
	
	//fetch=FetchType.EAGER 抓取实体时，立即抓取关联实体
	@ManyToMany(targetEntity=BlogArticle.class, cascade={CascadeType.REMOVE}, fetch=FetchType.EAGER)
	@JoinTable(name="groupMessage",
		joinColumns=@JoinColumn(name="groupId", referencedColumnName="groupId"),
		inverseJoinColumns=@JoinColumn(name="articleId", referencedColumnName="articleId")
	)
	@OrderBy("articleBuildTime DESC")
	private Set<BlogArticle> article = new HashSet<BlogArticle>();
	
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
	public Set<BlogArticle> getArticle() {
		return article;
	}
	public void setArticle(Set<BlogArticle> article) {
		this.article = article;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
