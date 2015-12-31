<%@page import="com.blog.bean.RandomString"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String primaryKey = request.getParameter("primaryKey");
	if(primaryKey != null){
		String randomString = RandomString.produceRandomString();
		request.setAttribute("randomString", randomString);
		request.setAttribute(randomString, primaryKey);
		request.getRequestDispatcher("blogAction!editJspGetAllGroups").forward(request, response);
	} else {
		request.getRequestDispatcher("blogAction!updateEditJpsGetOneArticle").forward(request, response);
	}
%>