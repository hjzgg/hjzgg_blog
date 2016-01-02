package com.blog.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.blog.entriy.MyPicture;
import com.blog.entriy.PictureComment;
import com.blog.entriy.PictureGroup;

public class PictureDao implements Serializable{
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public List<PictureGroup> pictureGroupJspGetAllGroups(){
		List<PictureGroup> list = new ArrayList<PictureGroup>();
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from PictureGroup").list();
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return null;
    	}
    	return list;
	}
	
	public String pictureRenameGroup(PictureGroup pictureGroup){
		Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.merge(pictureGroup);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return e.toString();
    	}
    	return null;
	}
	
	public String newPictureGroup(PictureGroup pictureGroup){
		Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.persist(pictureGroup);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return e.toString();
    	}
    	return null;
	}
	
	public PictureGroup pictureJspGetOneGroup(int groupId){
		PictureGroup pictureGroup = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = this.getSession();
			tran = session.beginTransaction();
			pictureGroup = (PictureGroup)session.createQuery("from PictureGroup where groupId="+groupId).list().get(0);
			tran.commit();
		} catch(Exception e) {
			System.out.println(e.toString());
			tran.rollback();
			return null;
		}
		return pictureGroup;
	}
	
	public String deletePictureGrouop(PictureGroup pictureGroup){
		Session session = null;
		Transaction tran = null;
		try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.delete(pictureGroup);
			tran.commit();
		} catch(Exception e){
			System.out.println(e.toString());
			tran.rollback();
			return e.toString();
		}
		return null;
	}
	
	public MyPicture myPicture;
	public MyPicture getMyPicture() {
		return myPicture;
	}
	public void setMyPicture(MyPicture myPicture) {
		this.myPicture = myPicture;
	}

	public String newMyPicture(MyPicture myPicture){
		Session session = null;
		Transaction tran = null;
		try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.persist(myPicture);
			tran.commit();
		} catch(Exception e){
			System.out.println(e.toString());
			tran.rollback();
			return e.toString();
		}
		return null;
	}
	
	public MyPicture pictureJspGetOnePicture(int pictureId){
		MyPicture myPicture = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = this.getSession();
			tran = session.beginTransaction();
			myPicture = (MyPicture)session.createQuery("from MyPicture where pictureId=" + pictureId).list().get(0);
			tran.commit();
		} catch(Exception e){
			System.out.println(e.toString());
			tran.rollback();
			return null;
		}
		return myPicture;
	}
	
	public String deleteMyPicture(MyPicture myPicture){
		Session session = null;
		Transaction tran = null;
		try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.delete(myPicture);
			tran.commit();
		} catch(Exception e){
			System.out.println(e.toString());
			tran.rollback();
			return e.toString();
		}
		return null;
	}
	
	public String newPicturesComment(PictureComment pictureComment){
		Session session = null;
		Transaction tran = null;
		try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.persist(pictureComment);
			tran.commit();
		} catch(Exception e){
			System.out.println(e.toString());
			tran.rollback();
			return e.toString();
		}
		return null;
	}
}
