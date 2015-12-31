package com.blog.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.blog.dao.PictureDao;
import com.blog.entriy.MyPicture;
import com.blog.entriy.PictureGroup;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FileUploadAction extends ActionSupport{
	private PictureDao pictureDao;
	public PictureDao getPictureDao() {
		return pictureDao;
	}
	public void setPictureDao(PictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}

	private List<File>	upload; // �ϴ����ļ�
    private List<String> uploadFileName; // �ļ�����
    private List<String> uploadContentType; // �ļ�����
    private String savePath;
    
	public List<File> getUpload() {
		return upload;
	}
	public void setUpload(List<File> upload) {
		this.upload = upload;
	}
	public List<String> getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(List<String> uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public List<String> getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(List<String> uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	private String pictureName = null;
	private String picturePath = null;
	private String pictureGroupId = null;
	public String fileUploadSuccess(){//���һ��ͼƬ ��ֲ������ newMyPicture����
		try{
			MyPicture myPicture = new MyPicture();
			myPicture.setPictureName(pictureName);
			myPicture.setPicturePath(picturePath);
			myPicture.setPictureBuildTime(new Timestamp(System.currentTimeMillis()));
			PictureGroup pictureGroup = pictureDao.pictureJspGetOneGroup(Integer.parseInt(pictureGroupId));
			if(pictureGroup == null) throw new NullPointerException("����Ϊ��!");
			myPicture.setGroup(pictureGroup);
			String msg = pictureDao.newMyPicture(myPicture);
			ActionContext.getContext().getSession().put("operations", "ͼƬ" + pictureName + (msg==null ? "�ϴ��ɹ�!" : "�ϴ�ʧ��:"+msg));
		} catch (Exception e){
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors", "ͼƬ" + pictureName + "���ʧ��: " + e.toString() + " �쳣λ�ã� FileUploadAction!fileUploadSuccess��");
			return "errors";
		}
		return "operations";
	}
	
	@Override
	public String  execute() throws Exception {
		try{
			// ȡ����Ҫ�ϴ����ļ�����
	        List<File> files = getUpload();
	        HttpServletRequest request = ServletActionContext.getRequest();
	        //�õ� �� ��Ŀ��Ŀ¼��URL
	        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	        if (files != null && files.size() > 0) {
	        	String realPath = ServletActionContext.getServletContext().getRealPath(savePath);
	        	//����ļ����ϴ�
//	            for (int i = 0; i < files.size(); i++) {
//	            	String fileName = System.currentTimeMillis()+getUploadContentType().get(i).replace("/", ".");
//	            	picturesPath.add(url+savePath+"/"+fileName);
//	                FileOutputStream fos = new FileOutputStream(realPath + "\\" + fileName);
//	                FileInputStream fis = new FileInputStream(files.get(i));
//	                byte[] buffer = new byte[1024];
//	                int len = 0;
//	                while ((len = fis.read(buffer)) > 0) {
//	                    fos.write(buffer, 0, len);
//	                }
//	                fis.close();
//	                fos.close();
//	            }
	        	
	        	if((pictureGroupId=(String)ActionContext.getContext().getSession().get("pictureGroupId")) == null){//δѡ�����
					ActionContext.getContext().getSession().put("operations", "ͼƬ"+uploadFileName+"�ϴ�ʧ�ܣ�����δѡ��! <a target='_parent' href='../pictureAction!pictureGroupJspGetAllGroups'>ѡ�����</a>");
					return "operations";
				}
	        	
	        	//�������ļ����ϴ�
	        	String fileName = System.currentTimeMillis()+getUploadFileName().get(0)+getUploadContentType().get(0).replace("/", ".");
                FileOutputStream fos = new FileOutputStream(realPath + "\\" + fileName);
                FileInputStream fis = new FileInputStream(files.get(0));
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fis.close();
                fos.close();
                picturePath = url+savePath+"/"+fileName;
                pictureName = getUploadFileName().get(0);
                return fileUploadSuccess();
	        } else {
	        	ActionContext.getContext().getSession().put("errors", "�õ��ļ���ĿΪ0! �쳣λ�ã�FileUploadAction!execute��");
	        }
		} catch (Exception e){
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors",  "ͼƬ" + getUploadFileName().get(0) + "�ϴ�ʧ��: " + e.toString()+"�쳣λ�ã�FileUploadAction!execute��");
			return "errors";
		}
        return "errors";
	}
}
