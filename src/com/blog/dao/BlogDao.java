package com.blog.dao;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.blog.entriy.ArticleComment;
import com.blog.entriy.BlogArticle;
import com.blog.entriy.BlogGroup;

public class BlogDao implements Serializable{
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
    
    public String articleRenameGroup(BlogGroup blogGroup){
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.merge(blogGroup);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return e.toString();
    	}
    	return null;
    }
    
    public String newBlogGroup(String groupName, String groupDescrib){
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			BlogGroup blogGroup = new BlogGroup();
			blogGroup.setGroupName(groupName);
			blogGroup.setGroupDescrib(groupDescrib);
			blogGroup.setGroupBuildTime(new Timestamp(System.currentTimeMillis()));
			session.persist(blogGroup);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return e.toString();
    	}
    	return null;
    }
    
    public List<BlogGroup> editJspGetAllGroups(){
    	List<BlogGroup> list = new ArrayList<BlogGroup>();
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from BlogGroup").list();
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return null;
    	}
    	return list;
    }
    
    public String newBlogArticle(BlogArticle blogArticle){
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.save(blogArticle);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return e.toString();
    	}
    	return null;
    }
    
    public String updateBlogArticle(BlogArticle blogArticle){
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.merge(blogArticle);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return e.toString();
    	}
    	return null;
    }
    
    public List<BlogArticle> indexJpsGetAllArticles(){
    	List<BlogArticle> list = new ArrayList<BlogArticle>();
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from BlogArticle").list();
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return null;
    	}
    	return list;
    }
    
    public BlogArticle articleJspGetOneArticle(int articleId){
    	BlogArticle blogArticle = null;
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			blogArticle = (BlogArticle) session.createQuery("from BlogArticle where articleId=" + articleId).list().get(0);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return null;
    	}
    	return blogArticle;
    }
    
    public String deleteBlogArticle(BlogArticle blogArticle){
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.delete(blogArticle);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return e.toString();
    	}
    	return null;
    }
    
    public String deleteArticleGrouop(BlogGroup blogGroup){
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.delete(blogGroup);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return e.toString();
    	}
    	return null;
    }
    
    public String newBlogArticleComment(ArticleComment comment){
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			session.persist(comment);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return e.toString();
    	}
    	return null;
    }
    
    public List<BlogGroup> someJspGetAllBlogGroup(){
    	List<BlogGroup> list = null;
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from BlogGroup").list();
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return null;
    	}
    	return list;
    }
    
    public BlogGroup groupJspGetOneGroup(int groupId){
    	BlogGroup blogGroup = null;
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			blogGroup = (BlogGroup) session.createQuery("from BlogGroup where groupId=" + groupId).list().get(0);
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return null;
    	}
    	return blogGroup;
    }
    
    public List<BlogArticle> searchArticles(String articleTitle){
    	List<BlogArticle> list = null;
    	Session session = null;
    	Transaction tran = null;
    	try{
			session = this.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from BlogArticle where articleTitle like '%" + articleTitle +"%'").list();
			tran.commit();
    	} catch(Exception e){
    		System.out.println(e.toString());
    		tran.rollback();
    		return null;
    	}
    	return list;
    }
}
