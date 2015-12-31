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

@Entity
@Table(name="articleComment")
public class ArticleComment implements Serializable{
	@Id
	@Column(name="commentId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int commentId;
	@Column(name="commentContent")
	private String commentContent;
	@Column(name="commentTime")
	private Timestamp commentTime;
	@Column(name="commentPeopleContact")
	private String commentPeopleContact;
	
	@ManyToOne(targetEntity=BlogArticle.class)
	@JoinColumn(name="articleId", referencedColumnName="articleId", nullable=false)
	private BlogArticle article;
	
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Timestamp getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentPeopleContact() {
		return commentPeopleContact;
	}

	public void setCommentPeopleContact(String commentPeopleContact) {
		this.commentPeopleContact = commentPeopleContact;
	}

	public BlogArticle getArticle() {
		return article;
	}

	public void setArticle(BlogArticle article) {
		this.article = article;
	}

}
