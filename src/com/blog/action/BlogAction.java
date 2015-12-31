package com.blog.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.blog.actionForm.BlogArticleCommentForm;
import com.blog.actionForm.BlogArticleForm;
import com.blog.actionForm.BlogGroupForm;
import com.blog.dao.BlogDao;
import com.blog.dao.PictureDao;
import com.blog.entriy.ArticleComment;
import com.blog.entriy.BlogArticle;
import com.blog.entriy.BlogGroup;
import com.blog.entriy.MyPicture;
import com.blog.entriy.PictureGroup;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BlogAction extends ActionSupport{
	private BlogDao blogDao;
	private PictureDao pictureDao;
	public PictureDao getPictureDao() {
		return pictureDao;
	}
	public void setPictureDao(PictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}
	public BlogDao getBlogDao() {
		return blogDao;
	}
	public void setBlogDao(BlogDao blogDao) {
		this.blogDao = blogDao;
	}
	public BlogGroupForm getBlogGroupForm() {
		return blogGroupForm;
	}
	public void setBlogGroupForm(BlogGroupForm blogGroupForm) {
		this.blogGroupForm = blogGroupForm;
	}
	
	public void setBlogArticleForm(BlogArticleForm blogArticleForm) {
		this.blogArticleForm = blogArticleForm;
	}
	public BlogArticleForm getBlogArticleForm() {
		return blogArticleForm;
	}
	
	public BlogGroupForm blogGroupForm;
	public String newBlogGroup(){
		try {
		    List<BlogGroup> list = blogDao.editJspGetAllGroups();
		    for(BlogGroup group : list)
		    	if(group.getGroupName().equals(blogGroupForm.getGroupName())){//分组已经存在
		    		ActionContext.getContext().getSession().put("errors", "新建分组失败: 分组已经存在!" + " 异常位置：blogAction!newBlogGroup。" );
		    		return "errors";
		    	}
			String msg = blogDao.newBlogGroup(blogGroupForm.getGroupName(), blogGroupForm.getGroupDescrib());
			ActionContext.getContext().getSession().put("operations", msg==null ? "新建分组成功!" : msg+" 异常位置：blogAction!newBlogGroup。");
		} catch(Exception e){
			ActionContext.getContext().getSession().put("errors", "新建分组失败: "+e.toString() + " 异常位置：blogAction!newBlogGroup。");
			return "errors";
		} 
		return "newBlogGroup";
	}
	
	public String articleRenameGroup(){
		try {
		    List<BlogGroup> list = blogDao.editJspGetAllGroups();
		    BlogGroup blogGroup = null;
		    for(BlogGroup group : list){
		    	if(!group.getGroupName().equals(blogGroupForm.getOrgGroupName()) && group.getGroupName().equals(blogGroupForm.getGroupName())){//分组已经存在
		    		ActionContext.getContext().getSession().put("errors", "更改分组失败: 分组已经存在!" + " 异常位置：blogAction!articleRenameGroup。" );
		    		return "errors";
		    	} else if(group.getGroupName().equals(blogGroupForm.getOrgGroupName())){
		    		blogGroup = group;
		    	}
		    }
		    blogGroup.setGroupName(blogGroupForm.getGroupName());
		    blogGroup.setGroupDescrib(blogGroupForm.getGroupDescrib());
			String msg = blogDao.articleRenameGroup(blogGroup);
			ActionContext.getContext().getSession().put("operations", msg==null ? "更改分组成功!" : msg+" 异常位置：blogAction!articleRenameGroup。");
		} catch(Exception e){
			ActionContext.getContext().getSession().put("errors", "更改分组失败: "+e.toString() + " 异常位置：blogAction!articleRenameGroup。");
			return "errors";
		} 
		return "articleRenameGroup";
	}
	
	public String editJspGetAllGroups(){
		List<BlogGroup> list = null;
		try{
			list = blogDao.editJspGetAllGroups();
			if(list == null) throw new NullPointerException();
			ActionContext.getContext().getSession().put("editJspGetAllGroups", list);
		} catch(Exception e){
			ActionContext.getContext().getSession().put("errors", "得到分组失败: "+e.toString()+" 异常位置:blogAction!editJspGetAllGroups。");
			return "errors";
		}
		return "editJspGetAllGroups";
	}
	
	public String articleGroupGetAllGroups(){
		List<PictureGroup> list = null;
		List<BlogGroup> listg = null;
		try{
			list = pictureDao.pictureGroupJspGetAllGroups();
			if(list == null) throw new NullPointerException();
			listg = blogDao.someJspGetAllBlogGroup();
			ActionContext.getContext().getSession().put("pictureGroupJspGetAllGroups", list);
			ActionContext.getContext().getSession().put("someJspGetAllBlogGroup", listg);
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "得到分组失败: " + e.toString() + " 异常位置： blogAction!articleGroupGetAllGroups。");
			return "errors";
		}
		return "articleGroupGetAllGroups";
	}

	private BlogArticleForm blogArticleForm;
	public String newBlogArticle(){
		try{
			BlogArticle blogArticle = new BlogArticle();
			blogArticle.setArticleTitle(blogArticleForm.getArticleTitle());
			blogArticle.setArticleContent(blogArticleForm.getArticleContent());
			blogArticle.setArticleBuildTime(new Timestamp(System.currentTimeMillis()));
			
			Whitelist whiteList = new Whitelist();
			String articleSummary = Jsoup.clean(blogArticleForm.getArticleContent(), whiteList.none());//获取纯文本
			articleSummary = articleSummary.replaceAll("&nbsp;", " ");
			articleSummary = articleSummary.replaceAll("\\s+", " ");
			if(articleSummary.length() >= 180)
				articleSummary = articleSummary.substring(0, 180);
			blogArticle.setArticleSummary(articleSummary);
			blogArticle.setArticleReadingCount(0);
			List<String> articleType = blogArticleForm.getArticleType();//得到分组
			List<BlogGroup> blogGroupList = new ArrayList<BlogGroup>();
			List<BlogGroup> blogGroupListInDB = blogDao.editJspGetAllGroups();
			
			if(articleType==null || articleType.size()==0){
				for(BlogGroup blogGroupInDB : blogGroupListInDB)
					if("未分组".equals(blogGroupInDB.getGroupName())){
						blogGroupList.add(blogGroupInDB);//寻找数据库中的持久化分组对象
						break;
					}
			} else if(blogGroupListInDB != null){
				for(BlogGroup blogGroupInDB : blogGroupListInDB)
						for(String type : articleType){
							if(type.equals(blogGroupInDB.getGroupName())){
								blogGroupList.add(blogGroupInDB);//寻找数据库中的持久化分组对象
								break;
							}
						}
			}
			blogArticle.getGroups().addAll(blogGroupList);//关联全部分组
			String msg = blogDao.newBlogArticle(blogArticle);//持久化对象
			ActionContext.getContext().getSession().put("operations", msg==null ? "新建随笔成功!" : "新建随笔失败："+msg + " 异常位置： blogAction!newBlogArticle。");
			blogArticleForm = null;
		} catch(Exception e){
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors", "新建随笔失败："+e.toString() + " 异常位置： blogAction!newBlogArticle。");
			return "errors";
		} finally{
			blogArticleForm = null;
		}
		return "newBlogArticle";
	}
	
	
	public String indexJpsGetAllArticles(){//修改：加上博客的分组
		List<BlogArticle> list = null;
		List<BlogGroup> listg = null;
		List<PictureGroup> listgg = null;
		try{
			list = blogDao.indexJpsGetAllArticles();
			if(list == null) throw new NullPointerException();
			listg = blogDao.someJspGetAllBlogGroup();
			if(listg == null) throw new NullPointerException();
			listgg = pictureDao.pictureGroupJspGetAllGroups();
			if(listgg == null) throw new NullPointerException();
			ActionContext.getContext().getSession().put("indexJpsGetAllArticles", list);
			ActionContext.getContext().getSession().put("someJspGetAllBlogGroup", listg);
			ActionContext.getContext().getSession().put("pictureGroupJspGetAllGroups", listgg);
		} catch(Exception e){
			ActionContext.getContext().getSession().put("errors", "得到文章列表失败: "+e.toString() + " 异常位置：blogAction!indexJpsGetAllArticles。");
			return "errors";
		}
		return "indexJpsGetAllArticles";
	}
	
	private String groupId;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String deleteArticleGrouop(){
		try{
			BlogGroup blogGroup = blogDao.groupJspGetOneGroup(Integer.parseInt(groupId));
			if(blogGroup == null) throw new NullPointerException("分组应经不存在!!!");
			String msg = blogDao.deleteArticleGrouop(blogGroup);
			ActionContext.getContext().getSession().put("operations", msg != null ? "删除分组失败: " + msg + " 异常位置： blogAction!deleteArticleGrouop。" : "删除分组成功!");
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "删除分组失败: " + e.toString() + " 异常位置： blogAction!deleteArticleGrouop。");
			return "errors";
		}
		return "deleteArticleGrouop";
	}
	
	public String deleteJpsGetAllArticles(){
		List<BlogArticle> list = null;
		try{
			list = blogDao.indexJpsGetAllArticles();
			if(list == null) throw new NullPointerException();
			ActionContext.getContext().getSession().put("deleteJpsGetAllArticles", list);
		} catch(Exception e){
			ActionContext.getContext().getSession().put("errors", "得到文章列表失败: "+e.toString() + "异常位置: blogAction!deleteJpsGetAllArticles。");
			return "errors";
		}
		return "deleteJpsGetAllArticles";
	}
	
	public String editAllJpsGetAllArticles(){
		List<BlogArticle> list = null;
		try{
			list = blogDao.indexJpsGetAllArticles();
			if(list == null) throw new NullPointerException();
			ActionContext.getContext().getSession().put("editAllJpsGetAllArticles", list);
		} catch(Exception e){
			ActionContext.getContext().getSession().put("errors", "得到文章列表失败: "+e.toString() + "异常位置: blogAction!editAllJpsGetAllArticles。");
			return "errors";
		}
		return "editAllJpsGetAllArticles";
	}
	
	private String articleId;
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String articleJspGetOneArticle(){
		BlogArticle blogArticle = null;
		List<BlogGroup> listg = null;
		List<PictureGroup> listgg = null;
		try{
			int ad = Integer.parseInt(getArticleId());
			blogArticle = blogDao.articleJspGetOneArticle(ad);
			if(blogArticle == null) throw new NullPointerException("articleJspGetOneArticle NullPointerException!");
			listg = blogDao.someJspGetAllBlogGroup();
			if(listg == null) throw new NullPointerException("someJspGetAllBlogGroup NullPointerException!");
			listgg = pictureDao.pictureGroupJspGetAllGroups();
			if(listgg == null) throw new NullPointerException("pictureGroupJspGetAllGroups NullPointerException");
			ActionContext.getContext().getSession().put("someJspGetAllBlogGroup", listg);
			ActionContext.getContext().getSession().put("pictureGroupJspGetAllGroups", listgg);
			ActionContext.getContext().getSession().put("articleJspGetOneArticle", blogArticle);
			//阅读数加1
			blogArticle.setArticleReadingCount(blogArticle.getArticleReadingCount()+1);
			blogDao.updateBlogArticle(blogArticle);
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "得到文章失败: "+e.toString() + " 异常位置: blogAction!articleJspGetOneArticle。");
			return "errors";
		}
		return "articleJspGetOneArticle";
	}
	
	public String updateEditJpsGetOneArticle(){
		BlogArticle blogArticle = null;
		List<BlogGroup> list = null;
		try{
			list = blogDao.editJspGetAllGroups();
			if(list == null) throw new NullPointerException();
			ActionContext.getContext().getSession().put("editJspGetAllGroups", list);
			int ad = Integer.parseInt(getArticleId());
			blogArticle = blogDao.articleJspGetOneArticle(ad);
			if(blogArticle == null) throw new NullPointerException();
			ActionContext.getContext().getSession().put("updateEditJpsGetOneArticle", blogArticle);
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "得到文章失败: "+e.toString() + "异常位置：blogAction!updateEditJpsGetOneArticle。");
			return "errors";
		}
		return "updateEditJpsGetOneArticle";
	}
	
	public String updateBlogArticle(){
		try{
			BlogArticle blogArticle = blogDao.articleJspGetOneArticle(Integer.parseInt(blogArticleForm.getArticleId()));
			blogArticle.setArticleTitle(blogArticleForm.getArticleTitle());
			blogArticle.setArticleContent(blogArticleForm.getArticleContent());
			Whitelist whiteList = new Whitelist();
			String articleSummary = Jsoup.clean(blogArticleForm.getArticleContent(), whiteList.none());//获取纯文本
			articleSummary = articleSummary.replaceAll("&nbsp;", " ");
			articleSummary = articleSummary.replaceAll("\\s+", " ");
			if(articleSummary.length() >= 180)
				articleSummary = articleSummary.substring(0, 180);
			blogArticle.setArticleSummary(articleSummary);
			List<String> articleType = blogArticleForm.getArticleType();//得到分组
			List<BlogGroup> blogGroupList = new ArrayList<BlogGroup>();
			List<BlogGroup> blogGroupListInDB = blogDao.editJspGetAllGroups();
			if(articleType==null || articleType.size()==0){
				for(BlogGroup blogGroupInDB : blogGroupListInDB)
					if("未分组".equals(blogGroupInDB.getGroupName())){
						blogGroupList.add(blogGroupInDB);//寻找数据库中的持久化分组对象
						break;
					}
			} else if(blogGroupListInDB != null){
				for(BlogGroup blogGroupInDB : blogGroupListInDB)
						for(String type : articleType){
							if(type.equals(blogGroupInDB.getGroupName())){
								blogGroupList.add(blogGroupInDB);//寻找数据库中的持久化分组对象
								break;
							}
						}
			}
			blogArticle.getGroups().clear();
			blogArticle.getGroups().addAll(blogGroupList);
			String msg = blogDao.updateBlogArticle(blogArticle);//持久化对象
			ActionContext.getContext().getSession().put("operations", msg==null ? "更新随笔成功!" : "更新随笔失败："+msg + " 异常位置： blogAction！updateBlogArticle。");
			blogArticleForm = null;
		} catch(Exception e){
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors", "更新随笔失败："+e.toString() + "异常位置： blogAction！updateBlogArticle。");
			return "errors";
		} finally{
			blogArticleForm = null;
		}
		return "updateBlogArticle";
	}
	
	public String deleteBlogArticle(){
		BlogArticle blogArticle = null;
		try{
			int ad = Integer.parseInt(getArticleId());
			blogArticle = blogDao.articleJspGetOneArticle(ad);
			if(blogArticle == null) throw new NullPointerException();
			String msg = blogDao.deleteBlogArticle(blogArticle);
			ActionContext.getContext().getSession().put("operations", msg==null ? "删除文章成功!" : "删除文章失败: "+msg+" 异常位置：blogAction!deleteBlogArticle。");
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "删除文章失败: "+e.toString() + " 异常位置：blogAction!deleteBlogArticle。");
			return "errors";
		}
		return "deleteBlogArticle";
	}
	
	public BlogArticleCommentForm blogArticleCommentForm;
	public BlogArticleCommentForm getBlogArticleCommentForm() {
		return blogArticleCommentForm;
	}
	public void setBlogArticleCommentForm(
			BlogArticleCommentForm blogArticleCommentForm) {
		this.blogArticleCommentForm = blogArticleCommentForm;
	}
	public String newBlogArticleComment(){
		BlogArticle blogArticle = null;
		try{
			int ad = Integer.parseInt(blogArticleCommentForm.getBlogArticleId());
			blogArticle = blogDao.articleJspGetOneArticle(ad);
			if(blogArticle == null) throw new NullPointerException();
			ArticleComment comment = new ArticleComment();
			comment.setCommentContent(blogArticleCommentForm.getBlogArticleCommentContent());
			comment.setCommentTime(new Timestamp(System.currentTimeMillis()));
			comment.setCommentPeopleContact(blogArticleCommentForm.getCommentPeopleContact());
			comment.setArticle(blogArticle);
			String msg = blogDao.newBlogArticleComment(comment);
			ActionContext.getContext().getSession().put("operations", msg==null ? "评论成功!" : "评论失败: "+msg+" 异常位置：blogAction!newBlogArticleComment。");
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "评论失败: "+e.toString() + " 异常位置：blogAction!newBlogArticleComment。");
			return "errors";
		}
		return "newBlogArticleComment";
	}
	
	String blogGroupId;
	public String getBlogGroupId() {
		return blogGroupId;
	}
	public void setBlogGroupId(String blogGroupId) {
		this.blogGroupId = blogGroupId;
	}
	public String groupJspGetOneGroup(){
		BlogGroup blogGroup = null;
		List<PictureGroup> listgg = null;
		try{
			blogGroup = blogDao.groupJspGetOneGroup(Integer.parseInt(blogGroupId));
			if(blogGroup == null) throw new NullPointerException();
			listgg = pictureDao.pictureGroupJspGetAllGroups();
			if(listgg == null) throw new NullPointerException();
			ActionContext.getContext().getSession().put("groupJspGetOneGroup", blogGroup);
			ActionContext.getContext().getSession().put("pictureGroupJspGetAllGroups", listgg);
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "得到分组失败: "+e.toString()+" 异常位置：blogAction!groupJspGetOneGroup。");
			return "errors";
		}
		return "groupJspGetOneGroup";
	}
}
