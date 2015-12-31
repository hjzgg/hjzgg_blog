package com.blog.actionForm;

import java.util.List;

public class BlogArticleForm {
	private String articleId;
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	private String articleTitle;
	private String articleContent;
	private List<String> articleType;
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public List<String> getArticleType() {
		return articleType;
	}
	public void setArticleType(List<String> articleType) {
		this.articleType = articleType;
	}
	
}
