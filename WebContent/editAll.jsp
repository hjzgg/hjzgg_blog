<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.blog.entriy.BlogArticle"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 为了确保适当的绘制和触屏缩放，需要在 <head> 之中添加 viewport 元数据标签。 -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>随笔编辑</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="jquery/jquery-1.11.3.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="bootstrap/js/bootstrap.min.js"></script>

<!-- 自定义css -->
<link rel="stylesheet" href="css/index-css.css">

<script src="js/background.js" type="text/javascript"></script>
<!-- 鼠标周围的星星 -->
<script type="text/javascript" src="js/mouse.js" id="mymouse"></script>
</head>
<body>
	<div class="container">
		<nav class="navbar navbar-inverse navbar-default">
		  <div class="container-fluid">
		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header" style="padding-left: 15px;">
		      <a class="navbar-brand" href="http://www.cnblogs.com/hujunzheng">博客园</a>
		    </div>
		
		    <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      <ul class="nav navbar-nav blog_nav">
		        <li><a href="blogAction!indexJpsGetAllArticles">首页</a></li>
		        <li><a href="blogAction!editJspGetAllGroups">新随笔</a></li>
		        <li><a href="messageAction!leaveWordJspGetAllWords">留言板</a></li>
		        <li><a href="/contact.jsp">联系</a></li>
		        <li><a href="pictureAction!pictureGroupJspGetAllGroups">相册</a></li>
		      </ul>
		      <c:if test="${!(empty session.primaryKey)}">
			      <ul class="nav navbar-nav navbar-right blog_nav">
			        <li class="dropdown">
			          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">随笔管理<span class="caret"></span></a>
			          <ul class="dropdown-menu blog-menu">
			            <li><a href="blogAction!editJspGetAllGroups">新随笔</a></li>
			            <li><a href="blogAction!editAllJpsGetAllArticles">编辑随笔</a></li>
			            <li><a href="blogAction!deleteJpsGetAllArticles">删除随笔</a></li>
			            <li role="separator" class="divider"></li>
			            <li><a href="javascript:void(0)" onclick="$('#newBlogGroup')[0].style.display='';">新建分组</a></li>
			            <li><a href="blogAction!articleGroupGetAllGroups">随笔分组</a></li>
			          </ul>
			        </li>
			      </ul>
			   </c:if>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
		
		<div class="row">
		  <div class="col-xs-12 col-md-12">
		  		<div class="panel panel-default blog-panel">
				  <!-- Default panel contents -->
				  <div class="blog-panel-heading">随笔编辑</div>
					 <div class="table-responsive">
					  <table class="table table-hover">
					      <thead>
					        <tr>
					          <th>#</th>
					          <th>标题</th>
					          <th style="text-align: center">评论数</th>
					          <th style="text-align: center">阅读数</th>
					          <th>操作</th>
					        </tr>
					      </thead>
					      <tbody>
					      	<%
						    	int pageCur=0, pageBegin=0, pageTot=0, num= 0;
							    final int pageSize = 6;//每一面显示的文章的数目
								List<BlogArticle> articleList = (List<BlogArticle>)session.getAttribute("editAllJpsGetAllArticles");
							    num = articleList.size();//总个数 
							    pageTot = num%pageSize == 0 ? num/pageSize : num/pageSize+1;//总页数
							    String curPage  = request.getParameter("page");//当前定位的页码
							    if(curPage == null) pageCur = 1; 
							    else pageCur = Integer.valueOf(curPage);
							    pageBegin = pageCur-5;
							    if(pageBegin < 1) pageBegin = 1;
							    int i = (pageCur-1)*pageSize;
							    request.setAttribute("pageCur", pageCur);
							    request.setAttribute("pageBegin", pageBegin);
							    request.setAttribute("pageTot", pageTot);
							    if(num>0)
									for(int cnt=0; cnt<pageSize && i<articleList.size(); ++i, ++cnt){
										BlogArticle article = articleList.get(i);
										request.setAttribute("article", article);
						    %>
						        <tr>
						          <th scope="row"><%=i+1%></th>
						          <td><a style="text-decoration: none;" href="blogAction!articleJspGetOneArticle?articleId=${article.articleId}">${article.articleTitle}</a></td>
						          <td style="text-align: center">${fn:length(article.comments)}</td>
						          <td style="text-align: center">${article.articleReadingCount}</td>
						          <td id="operationId<%=i+1%>"><a class="btn btn-info" href="javascript:void(0)" onclick="location.href='blogAction!updateEditJpsGetOneArticle?articleId=${article.articleId}';" role="button">编辑</a></td>
						        </tr>
						    <%
								}
							%>
					      </tbody>
					    </table>
					</div>
					<nav class="blog-nav">
					  <ul class="pagination">
							<!-- 加入分页的按钮  -->
							<c:choose>
								<c:when test="${pageCur eq 1}">
									   <li class="disabled">
									      <a href="javascript:void(0)" aria-label="Previous">
									        <span aria-hidden="true">&laquo;</span>
									      </a>
									    </li>
						         </c:when>
						         <c:otherwise>
						        	    <li onclick="myGoTo('page=${pageCur-1}')">
									      <a href="javascript:void(0)" aria-label="Previous">
									        <span aria-hidden="true">&laquo;</span>
									      </a>
									    </li>
						         </c:otherwise>
					         </c:choose>
					         <c:set var="endI" value="10"/>
					       	 <c:if test="${pageTot-pageBegin < endI}">
					         	<c:set var="endI" value="${pageTot}"/>
					         </c:if>
					         <c:forEach var="p" begin="${pageBegin}" end="${endI}">
					         	<c:choose>
						         	<c:when test="${p == pageCur}">
						         		 <li class="active"><a href="javascript:void(0)">${p}</a></li>
						         	</c:when>
						         	<c:otherwise>
										 <li onclick="myGoTo('page=${p}')"><a href="javascript:void(0)">${p}</a></li>					         	
						         	</c:otherwise>
						         </c:choose>
					         </c:forEach> 
						     <c:choose>
								 <c:when test="${pageCur ne pageTot}">
								 	<li onclick="myGoTo('page=${pageCur+1}')">
								      <a href="javascript:void(0)" aria-label="Next">
								        <span aria-hidden="true">&raquo;</span>
								      </a>
								    </li>
								 </c:when>
								 <c:otherwise>
								 	<li class="disabled">
								      <a href="javascript:void(0)" aria-label="Next">
								        <span aria-hidden="true">&raquo;</span>
								      </a>
								    </li>
								 </c:otherwise>
					          </c:choose>
					          <b>
					                  &nbsp;&nbsp;共${pageTot}页&nbsp;&nbsp;
					                                                     向第<input type="text" id="pageTo" size="1">页
					          </b>
					          <button onclick="myDumpTo(${pageTot})" type="button" class="btn btn-default">跳转</button>
					   </ul>
					</nav>
				</div>
		  </div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function myGoTo(pageIndex){
		location.href = "blogAction!editAllJpsGetAllArticles?" + pageIndex;
	}
	
	function myDumpTo(pageTot){
		if($("#pageTo")[0].value=="" || isNaN($("#pageTo")[0].value)) return;
		var page_tot = parseInt(pageTot, 10);
		var dump_page = parseInt($("#pageTo")[0].value, 10);
		if(dump_page<1 || dump_page>page_tot) return;
		myGoTo("page=" + dump_page);
	}
	
</script>
</html>