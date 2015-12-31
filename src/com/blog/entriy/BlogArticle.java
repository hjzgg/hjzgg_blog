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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="blogArticle")
public class BlogArticle implements Serializable{
	@Id
	@Column(name="articleId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int articleId;
	@Column(name="articleReadingCount")
	private int articleReadingCount;
	@Column(name="articleContent")
	private String articleContent;
	@Column(name="articleTitle")
	private String articleTitle;
	@Column(name="articleSummary")
	private String articleSummary;
	@Column(name="articleBuildTime")
	private Timestamp articleBuildTime;
	
	@ManyToMany(targetEntity=BlogGroup.class, fetch=FetchType.EAGER)
	@JoinTable(name="groupMessage",
		joinColumns=@JoinColumn(name="articleId", referencedColumnName="articleId"),
		inverseJoinColumns=@JoinColumn(name="groupId", referencedColumnName="groupId")
	)
	private Set<BlogGroup> groups = new HashSet<BlogGroup>();
	
	@OneToMany(targetEntity=ArticleComment.class, mappedBy="article", fetch=FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.REMOVE})//article是ArticleComment类中的BlogArticle对象引用
	@OrderBy("commentTime ASC")
	private Set<ArticleComment> comments = new HashSet<ArticleComment>();
	
	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleSummary() {
		return articleSummary;
	}

	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}

	public Timestamp getArticleBuildTime() {
		return articleBuildTime;
	}

	public void setArticleBuildTime(Timestamp articleBuildTime) {
		this.articleBuildTime = articleBuildTime;
	}

	public Set<BlogGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<BlogGroup> groups) {
		this.groups = groups;
	}

	public Set<ArticleComment> getComments() {
		return comments;
	}

	public void setComments(Set<ArticleComment> comments) {
		this.comments = comments;
	}
	
	public int getArticleReadingCount() {
		return articleReadingCount;
	}

	public void setArticleReadingCount(int articleReadingCount) {
		this.articleReadingCount = articleReadingCount;
	}
}
