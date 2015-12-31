package com.blog.actionForm;

public class BlogArticleCommentForm {
	private String blogArticleId;
	private String blogArticleCommentContent;
	private String commentPeopleContact;
	public String getBlogArticleId() {
		return blogArticleId;
	}
	public void setBlogArticleId(String blogArticleId) {
		this.blogArticleId = blogArticleId;
	}
	public String getBlogArticleCommentContent() {
		return blogArticleCommentContent;
	}
	public void setBlogArticleCommentContent(String blogArticleCommentContent) {
		this.blogArticleCommentContent = blogArticleCommentContent;
	}
	public String getCommentPeopleContact() {
		return commentPeopleContact;
	}
	public void setCommentPeopleContact(String commentPeopleContact) {
		this.commentPeopleContact = commentPeopleContact;
	}
}
