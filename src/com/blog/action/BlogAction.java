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
		    	if(group.getGroupName().equals(blogGroupForm.getGroupName())){//�����Ѿ�����
		    		ActionContext.getContext().getSession().put("errors", "�½�����ʧ��: �����Ѿ�����!" + " �쳣λ�ã�blogAction!newBlogGroup��" );
		    		return "errors";
		    	}
			String msg = blogDao.newBlogGroup(blogGroupForm.getGroupName(), blogGroupForm.getGroupDescrib());
			ActionContext.getContext().getSession().put("operations", msg==null ? "�½�����ɹ�!" : msg+" �쳣λ�ã�blogAction!newBlogGroup��");
		} catch(Exception e){
			ActionContext.getContext().getSession().put("errors", "�½�����ʧ��: "+e.toString() + " �쳣λ�ã�blogAction!newBlogGroup��");
			return "errors";
		} 
		return "newBlogGroup";
	}
	
	public String articleRenameGroup(){
		try {
		    List<BlogGroup> list = blogDao.editJspGetAllGroups();
		    BlogGroup blogGroup = null;
		    for(BlogGroup group : list){
		    	if(!group.getGroupName().equals(blogGroupForm.getOrgGroupName()) && group.getGroupName().equals(blogGroupForm.getGroupName())){//�����Ѿ�����
		    		ActionContext.getContext().getSession().put("errors", "���ķ���ʧ��: �����Ѿ�����!" + " �쳣λ�ã�blogAction!articleRenameGroup��" );
		    		return "errors";
		    	} else if(group.getGroupName().equals(blogGroupForm.getOrgGroupName())){
		    		blogGroup = group;
		    	}
		    }
		    blogGroup.setGroupName(blogGroupForm.getGroupName());
		    blogGroup.setGroupDescrib(blogGroupForm.getGroupDescrib());
			String msg = blogDao.articleRenameGroup(blogGroup);
			ActionContext.getContext().getSession().put("operations", msg==null ? "���ķ���ɹ�!" : msg+" �쳣λ�ã�blogAction!articleRenameGroup��");
		} catch(Exception e){
			ActionContext.getContext().getSession().put("errors", "���ķ���ʧ��: "+e.toString() + " �쳣λ�ã�blogAction!articleRenameGroup��");
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
			ActionContext.getContext().getSession().put("errors", "�õ�����ʧ��: "+e.toString()+" �쳣λ��:blogAction!editJspGetAllGroups��");
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
			ActionContext.getContext().getSession().put("errors", "�õ�����ʧ��: " + e.toString() + " �쳣λ�ã� blogAction!articleGroupGetAllGroups��");
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
			String articleSummary = Jsoup.clean(blogArticleForm.getArticleContent(), whiteList.none());//��ȡ���ı�
			articleSummary = articleSummary.replaceAll("&nbsp;", " ");
			articleSummary = articleSummary.replaceAll("\\s+", " ");
			if(articleSummary.length() >= 180)
				articleSummary = articleSummary.substring(0, 180);
			blogArticle.setArticleSummary(articleSummary);
			blogArticle.setArticleReadingCount(0);
			List<String> articleType = blogArticleForm.getArticleType();//�õ�����
			List<BlogGroup> blogGroupList = new ArrayList<BlogGroup>();
			List<BlogGroup> blogGroupListInDB = blogDao.editJspGetAllGroups();
			
			if(articleType==null || articleType.size()==0){
				for(BlogGroup blogGroupInDB : blogGroupListInDB)
					if("δ����".equals(blogGroupInDB.getGroupName())){
						blogGroupList.add(blogGroupInDB);//Ѱ�����ݿ��еĳ־û��������
						break;
					}
			} else if(blogGroupListInDB != null){
				for(BlogGroup blogGroupInDB : blogGroupListInDB)
						for(String type : articleType){
							if(type.equals(blogGroupInDB.getGroupName())){
								blogGroupList.add(blogGroupInDB);//Ѱ�����ݿ��еĳ־û��������
								break;
							}
						}
			}
			blogArticle.getGroups().addAll(blogGroupList);//����ȫ������
			String msg = blogDao.newBlogArticle(blogArticle);//�־û�����
			ActionContext.getContext().getSession().put("operations", msg==null ? "�½���ʳɹ�!" : "�½����ʧ�ܣ�"+msg + " �쳣λ�ã� blogAction!newBlogArticle��");
			blogArticleForm = null;
		} catch(Exception e){
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors", "�½����ʧ�ܣ�"+e.toString() + " �쳣λ�ã� blogAction!newBlogArticle��");
			return "errors";
		} finally{
			blogArticleForm = null;
		}
		return "newBlogArticle";
	}
	
	
	public String indexJpsGetAllArticles(){//�޸ģ����ϲ��͵ķ���
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
			ActionContext.getContext().getSession().put("errors", "�õ������б�ʧ��: "+e.toString() + " �쳣λ�ã�blogAction!indexJpsGetAllArticles��");
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
			if(blogGroup == null) throw new NullPointerException("����Ӧ��������!!!");
			String msg = blogDao.deleteArticleGrouop(blogGroup);
			ActionContext.getContext().getSession().put("operations", msg != null ? "ɾ������ʧ��: " + msg + " �쳣λ�ã� blogAction!deleteArticleGrouop��" : "ɾ������ɹ�!");
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "ɾ������ʧ��: " + e.toString() + " �쳣λ�ã� blogAction!deleteArticleGrouop��");
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
			ActionContext.getContext().getSession().put("errors", "�õ������б�ʧ��: "+e.toString() + "�쳣λ��: blogAction!deleteJpsGetAllArticles��");
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
			ActionContext.getContext().getSession().put("errors", "�õ������б�ʧ��: "+e.toString() + "�쳣λ��: blogAction!editAllJpsGetAllArticles��");
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
			//�Ķ�����1
			blogArticle.setArticleReadingCount(blogArticle.getArticleReadingCount()+1);
			blogDao.updateBlogArticle(blogArticle);
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "�õ�����ʧ��: "+e.toString() + " �쳣λ��: blogAction!articleJspGetOneArticle��");
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
			ActionContext.getContext().getSession().put("errors", "�õ�����ʧ��: "+e.toString() + "�쳣λ�ã�blogAction!updateEditJpsGetOneArticle��");
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
			String articleSummary = Jsoup.clean(blogArticleForm.getArticleContent(), whiteList.none());//��ȡ���ı�
			articleSummary = articleSummary.replaceAll("&nbsp;", " ");
			articleSummary = articleSummary.replaceAll("\\s+", " ");
			if(articleSummary.length() >= 180)
				articleSummary = articleSummary.substring(0, 180);
			blogArticle.setArticleSummary(articleSummary);
			List<String> articleType = blogArticleForm.getArticleType();//�õ�����
			List<BlogGroup> blogGroupList = new ArrayList<BlogGroup>();
			List<BlogGroup> blogGroupListInDB = blogDao.editJspGetAllGroups();
			if(articleType==null || articleType.size()==0){
				for(BlogGroup blogGroupInDB : blogGroupListInDB)
					if("δ����".equals(blogGroupInDB.getGroupName())){
						blogGroupList.add(blogGroupInDB);//Ѱ�����ݿ��еĳ־û��������
						break;
					}
			} else if(blogGroupListInDB != null){
				for(BlogGroup blogGroupInDB : blogGroupListInDB)
						for(String type : articleType){
							if(type.equals(blogGroupInDB.getGroupName())){
								blogGroupList.add(blogGroupInDB);//Ѱ�����ݿ��еĳ־û��������
								break;
							}
						}
			}
			blogArticle.getGroups().clear();
			blogArticle.getGroups().addAll(blogGroupList);
			String msg = blogDao.updateBlogArticle(blogArticle);//�־û�����
			ActionContext.getContext().getSession().put("operations", msg==null ? "������ʳɹ�!" : "�������ʧ�ܣ�"+msg + " �쳣λ�ã� blogAction��updateBlogArticle��");
			blogArticleForm = null;
		} catch(Exception e){
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors", "�������ʧ�ܣ�"+e.toString() + "�쳣λ�ã� blogAction��updateBlogArticle��");
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
			ActionContext.getContext().getSession().put("operations", msg==null ? "ɾ�����³ɹ�!" : "ɾ������ʧ��: "+msg+" �쳣λ�ã�blogAction!deleteBlogArticle��");
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "ɾ������ʧ��: "+e.toString() + " �쳣λ�ã�blogAction!deleteBlogArticle��");
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
			ActionContext.getContext().getSession().put("operations", msg==null ? "���۳ɹ�!" : "����ʧ��: "+msg+" �쳣λ�ã�blogAction!newBlogArticleComment��");
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "����ʧ��: "+e.toString() + " �쳣λ�ã�blogAction!newBlogArticleComment��");
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
			ActionContext.getContext().getSession().put("errors", "�õ�����ʧ��: "+e.toString()+" �쳣λ�ã�blogAction!groupJspGetOneGroup��");
			return "errors";
		}
		return "groupJspGetOneGroup";
	}
}
