package com.blog.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

/**
 * Servlet Filter implementation class MyStrutsPrepareAndExecuteFilter
 */

public class MyStrutsPrepareAndExecuteFilter extends StrutsPrepareAndExecuteFilter{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		   HttpServletRequest req = (HttpServletRequest) request;    
	       String url = req.getRequestURI();    
	       if (url.contains("/ueditor/jsp/controller.jsp") || url.contains("/umeditor/jsp/")) {//ueditor或者umeditor的不过滤
	           chain.doFilter(request, response);    
	       }else{    
	           super.doFilter(request, response, chain);    
	       }    
	}
}
