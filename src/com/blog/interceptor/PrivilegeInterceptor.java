package com.blog.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.blog.bean.RandomString;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class PrivilegeInterceptor extends MethodFilterInterceptor{
	private String interceptorName;
	
	public String getInterceptorName() {
		return interceptorName;
	}

	public void setInterceptorName(String interceptorName) {
		this.interceptorName = interceptorName;
	}

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();       
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);  
        String randomString = (String)request.getAttribute("randomString");
		if((ctx.getSession().get("primaryKey")!=null) || (randomString!=null && request.getAttribute(randomString)!=null && request.getAttribute(randomString).equals(RandomString.getPrimaryKey()))){
			if(ctx.getSession().get("primaryKey")==null) ctx.getSession().put("primaryKey", RandomString.getPrimaryKey());
			return invocation.invoke();
		}
		ctx.getSession().put("errors", "Errors occurs: primaryKey is invalidate! 少年, 你无权限访问!");
		return "errors";
	}
}
