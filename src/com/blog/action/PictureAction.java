package com.blog.action;

import java.sql.Timestamp;
import java.util.List;

import com.blog.actionForm.PictrueGroupForm;
import com.blog.actionForm.PictureCommentForm;
import com.blog.dao.BlogDao;
import com.blog.dao.PictureDao;
import com.blog.entriy.ArticleComment;
import com.blog.entriy.BlogGroup;
import com.blog.entriy.MyPicture;
import com.blog.entriy.PictureComment;
import com.blog.entriy.PictureGroup;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PictureAction extends ActionSupport{
	private PictureDao pictureDao;
	private BlogDao blogDao;
	
	public BlogDao getBlogDao() {
		return blogDao;
	}
	
	public void setBlogDao(BlogDao blogDao) {
		this.blogDao = blogDao;
	}

	public PictureDao getPictureDao() {
		return pictureDao;
	}

	public void setPictureDao(PictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}
	
	private PictrueGroupForm pictureGroupForm;
	public PictrueGroupForm getPictureGroupForm() {
		return pictureGroupForm;
	}

	public void setPictureGroupForm(PictrueGroupForm pictureGroupForm) {
		this.pictureGroupForm = pictureGroupForm;
	}
	
	public String newPictureGroup(){
		try {
		    List<PictureGroup> list = pictureDao.pictureGroupJspGetAllGroups();
		    for(PictureGroup group : list)
		    	if(group.getGroupName().equals(pictureGroupForm.getGroupName())){//�����Ѿ�����
		    		ActionContext.getContext().getSession().put("errors", "�½�����ʧ��: �����Ѿ�����!" + " �쳣λ�ã�PictureAction!newPictureGroup��" );
		    		return "errors";
		    	}
		    PictureGroup pictureGroup = new PictureGroup();
		    pictureGroup.setGroupName(pictureGroupForm.getGroupName());
		    pictureGroup.setGroupDescrib(pictureGroupForm.getGroupDescrib());
		    pictureGroup.setGroupBuildTime(new Timestamp(System.currentTimeMillis()));
			String msg = pictureDao.newPictureGroup(pictureGroup);
			ActionContext.getContext().getSession().put("operations", msg==null ? "�½�����ɹ�!" : msg+" �쳣λ�ã�PictureAction!newPictureGroup��");
		} catch(Exception e){
			ActionContext.getContext().getSession().put("errors", "�½�����ʧ��: "+e.toString() + " �쳣λ�ã�PictureAction!newPictureGroup��");
			return "errors";
		}  
		return "newPictureGroup";
	}
	
	public String groupId;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String deletePictureGrouop(){
		try{
			PictureGroup pictureGroup = pictureDao.pictureJspGetOneGroup(Integer.parseInt(groupId));
			if(pictureGroup == null) throw new NullPointerException("�����Ѿ�������!!!");
			String msg = pictureDao.deletePictureGrouop(pictureGroup);
			ActionContext.getContext().getSession().put("operations", msg != null ? "ɾ������ʧ��: " + msg + " �쳣λ�ã� BlogAction!deletePictureGrouop��" : "ɾ������ɹ�!");
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "ɾ������ʧ��: " + e.toString() + " �쳣λ�ã� BlogAction!deletePictureGrouop��");
			return "errors";
		}
		return "deletePictureGrouop";
	}
	
	public String pictureJspGetOneGroup(){
		try{
			PictureGroup pictureGroup = pictureDao.pictureJspGetOneGroup(Integer.parseInt(groupId));
			if(pictureGroup == null) throw new NullPointerException();
			ActionContext.getContext().getSession().put("pictureJspGetOneGroup", pictureGroup);
			ActionContext.getContext().getSession().put("pictureGroupId", groupId);
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "�õ�����ʧ��: " + e.toString() + " �쳣λ�ã� PictureAction!pictureJspGetOneGroup��");
			return "errors";
		}
		return "pictureJspGetOneGroup";
	}
	
//	public String newMyPicture(){//���һ��ͼƬ,����ֲ��FileUploadAction
//		String pictureName = null;
//		String picturePath = null;
//		String pictureGroupId = null;
//		try{
//			Map<String, Object> session = ActionContext.getContext().getSession();
//			pictureName = (String)session.get("pictureName");
//			picturePath = (String)session.get("picturePath");
//			pictureGroupId = (String)session.get("pictureGroupId");
//			MyPicture myPicture = new MyPicture();
//			myPicture.setPictureName(pictureName);
//			myPicture.setPicturePath(picturePath);
//			myPicture.setPictureBuildTime(new Timestamp(System.currentTimeMillis()));
//			PictureGroup pictureGroup = pictureDao.pictureJspGetOneGroup(Integer.parseInt(pictureGroupId));
//			if(pictureGroup == null) throw new NullPointerException("����Ϊ��!");
//			myPicture.setGroup(pictureGroup);
//			String msg = pictureDao.newMyPicture(myPicture);
//			ActionContext.getContext().getSession().put("operations", "ͼƬ" + pictureName + (msg==null ? "�ϴ��ɹ�!" : "�ϴ�ʧ��:"+msg));
//		} catch (Exception e){
//			System.out.println(e.toString());
//			ActionContext.getContext().getSession().put("errors", "ͼƬ" + pictureName + "���ʧ��: " + e.toString() + " �쳣λ�ã� PictureAction!newMyPicture��");
//			return "errors";
//		}
//		return "newMyPicture";
//	}
	
	private String pictureId;
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	public String deleteMyPicture(){
		try{
			MyPicture myPicture = pictureDao.pictureJspGetOnePicture(Integer.parseInt(pictureId));
			if(myPicture == null) throw new NullPointerException();
			String msg = pictureDao.deleteMyPicture(myPicture);
			ActionContext.getContext().getSession().put("operations", msg != null ? "ɾ��ͼƬʧ��: " + msg + " �쳣λ�ã� PictureAction!deletePictureGroup��" : "ɾ��ͼƬ�ɹ�!");
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "ɾ��ͼƬʧ��: " + e.toString() + " �쳣λ�ã� PictureAction!deletePictureGroup��");
			return "errors";
		}
		return "deleteMyPicture";
	}
	
	public String pictureGroupJspGetAllGroups(){
		List<PictureGroup> list = null;
		List<BlogGroup> listg = null;
		try{
			list = pictureDao.pictureGroupJspGetAllGroups();
			if(list == null) throw new NullPointerException();
			listg = blogDao.someJspGetAllBlogGroup();
			ActionContext.getContext().getSession().put("pictureGroupJspGetAllGroups", list);
			ActionContext.getContext().getSession().put("someJspGetAllBlogGroup", listg);
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "�õ�����ʧ��: " + e.toString() + " �쳣λ�ã� PictureAction!deletePictureGroup��");
			return "errors";
		}
		return "pictureGroupJspGetAllGroups";
	}
	
	
	public String pictureRenameGroup(){
		try {
		    List<PictureGroup> list = pictureDao.pictureGroupJspGetAllGroups();
		    PictureGroup pictureGroup = null;
		    for(PictureGroup group : list){
		    	if(!pictureGroupForm.getOrgGroupName().equals(pictureGroupForm.getGroupName()) && group.getGroupName().equals(pictureGroupForm.getGroupName())){//�����Ѿ�����
		    		ActionContext.getContext().getSession().put("errors", "���ķ���ʧ��: �����Ѿ�����!" + " �쳣λ�ã�PictureAction!pictureRenameGroup��" );
		    		return "errors_x";
		    	} else if(group.getGroupName().equals(pictureGroupForm.getOrgGroupName())){//�õ�Ҫ���ķ���ĳ־û�����
		    		pictureGroup = group;
		    	}
		    }
		    pictureGroup.setGroupName(pictureGroupForm.getGroupName());
		    pictureGroup.setGroupDescrib(pictureGroupForm.getGroupDescrib());
			String msg = pictureDao.pictureRenameGroup(pictureGroup);
			ActionContext.getContext().getSession().put("operations", msg==null ? "���ķ���ɹ�!" : msg+" �쳣λ�ã�PictureAction!pictureRenameGroup��");
		} catch(Exception e){
			ActionContext.getContext().getSession().put("errors", "���ķ���ʧ��: "+e.toString() + " �쳣λ�ã�PictureAction!pictureRenameGroup��");
			return "errors_x";
		}  
		return "pictureRenameGroup";
	}
	
	PictureCommentForm pictureCommentForm;
	public PictureCommentForm getPictureCommentForm() {
		return pictureCommentForm;
	}
	public void setPictureCommentForm(PictureCommentForm pictureCommentForm) {
		this.pictureCommentForm = pictureCommentForm;
	}

	public String newPictureComment(){
		PictureGroup pictureGroup = null;
		try{
			int gd = Integer.parseInt(pictureCommentForm.getGroupId());
			pictureGroup = pictureDao.pictureJspGetOneGroup(gd);
			if(pictureGroup == null) throw new NullPointerException();
			PictureComment comment = new PictureComment();
			comment.setCommentContent(pictureCommentForm.getPictureCommentContent());
			comment.setCommentTime(new Timestamp(System.currentTimeMillis()));
			comment.setCommentPeopleContact(pictureCommentForm.getCommentPeopleContact());
			comment.setGroup(pictureGroup);
			String msg = pictureDao.newPicturesComment(comment);
			ActionContext.getContext().getSession().put("operations", msg==null ? "���۳ɹ�!" : "����ʧ��: "+msg+" �쳣λ�ã�pictureAction!newPicturesComment��");
		} catch (Exception e){
			ActionContext.getContext().getSession().put("errors", "����ʧ��: "+e.toString() + " �쳣λ�ã�pictureAction!newPicturesComment��");
			return "errors";
		}
		return "newPictureComment";
	}
}
