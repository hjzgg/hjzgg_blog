<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	request.getRequestDispatcher("blogAction!indexJpsGetAllArticles").forward(request, response);
%>