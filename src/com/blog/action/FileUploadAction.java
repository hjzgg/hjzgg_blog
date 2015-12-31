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

	private List<File>	upload; // 上传的文件
    private List<String> uploadFileName; // 文件名称
    private List<String> uploadContentType; // 文件类型
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
	public String fileUploadSuccess(){//添加一张图片 移植过来的 newMyPicture方法
		try{
			MyPicture myPicture = new MyPicture();
			myPicture.setPictureName(pictureName);
			myPicture.setPicturePath(picturePath);
			myPicture.setPictureBuildTime(new Timestamp(System.currentTimeMillis()));
			PictureGroup pictureGroup = pictureDao.pictureJspGetOneGroup(Integer.parseInt(pictureGroupId));
			if(pictureGroup == null) throw new NullPointerException("分组为空!");
			myPicture.setGroup(pictureGroup);
			String msg = pictureDao.newMyPicture(myPicture);
			ActionContext.getContext().getSession().put("operations", "图片" + pictureName + (msg==null ? "上传成功!" : "上传失败:"+msg));
		} catch (Exception e){
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors", "图片" + pictureName + "添加失败: " + e.toString() + " 异常位置： FileUploadAction!fileUploadSuccess。");
			return "errors";
		}
		return "operations";
	}
	
	@Override
	public String  execute() throws Exception {
		try{
			// 取得需要上传的文件数组
	        List<File> files = getUpload();
	        HttpServletRequest request = ServletActionContext.getRequest();
	        //得到 到 项目根目录的URL
	        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	        if (files != null && files.size() > 0) {
	        	String realPath = ServletActionContext.getServletContext().getRealPath(savePath);
	        	//多个文件的上传
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
	        	
	        	if((pictureGroupId=(String)ActionContext.getContext().getSession().get("pictureGroupId")) == null){//未选择分组
					ActionContext.getContext().getSession().put("operations", "图片"+uploadFileName+"上传失败，分组未选择! <a target='_parent' href='../pictureAction!pictureGroupJspGetAllGroups'>选择分组</a>");
					return "operations";
				}
	        	
	        	//处理单个文件的上传
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
	        	ActionContext.getContext().getSession().put("errors", "得到文件数目为0! 异常位置：FileUploadAction!execute。");
	        }
		} catch (Exception e){
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors",  "图片" + getUploadFileName().get(0) + "上传失败: " + e.toString()+"异常位置：FileUploadAction!execute。");
			return "errors";
		}
        return "errors";
	}
}
