package com.blog.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.blog.entriy.LeaveWord;

public class MessageDao {
	private SessionFactory sessionFactory;  
    
    public Session getSession() {  
        return sessionFactory.getCurrentSession();  
    }  
  
    public SessionFactory getSessionFactory() {  
        return sessionFactory;  
    }  
  
    public void setSessionFactory(SessionFactory sessionFactory) {  
        this.sessionFactory = sessionFactory;  
    }  
    
    public String newLeaveWord(LeaveWord leaveWord){
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.persist(leaveWord);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return e.toString();
    	}
    	return null;	
    }
    
    public List<LeaveWord> leaveWordJspGetAllWords(){
    	List<LeaveWord> list = null; 
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from LeaveWord").list();
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return null;
    	}
    	return list;	
    }
}
