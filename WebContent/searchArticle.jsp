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

<title>文章搜索</title>

<script src="js/background.js" type="text/javascript"></script>
<!-- 鼠标周围的星星 -->
<script type="text/javascript" src="js/mouse.js" id="mymouse"></script>
<script type="text/javascript" src="js/imgzoom.js"></script>

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

<script type="text/javascript">
	var articleTitle = ${session.articleTitle};
</script>
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
		        <li><a href="javascript:void(0)" onclick="$('#myModal').modal('show');">新随笔</a></li>
		        <li><a href="messageAction!leaveWordJspGetAllWords">留言板</a></li>
		        <li><a href="contact.jsp">联系</a></li>
		        <li><a href="pictureAction!pictureGroupJspGetAllGroups">相册</a></li>
		      </ul>
		   
		      <form ction="blogAction!searchArticles" method="post" class="navbar-form navbar-right" role="search">
		        <div class="form-group">
		          <input name="articleTitle" type="text" class="form-control" placeholder="Search" value="${session.articleTitle}">
		        </div>
		        <button type="submit" class="btn btn-default">文章检索</button>
		      </form>
		      
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
		
		<div class="row">
			<div class="col-xs-6 col-md-4">
				<h3 class="catListTitle">我的形象</h3>
				<div class="panel panel-default">
				  <div class="panel-body">
				  		<div style="width:70%; height:100px;float:left; display: table;">
				  			<div style="display: table-cell; vertical-align: middle;">
					  			<b>越努力，越幸运！</b>
					  			<br>
					  			<p style="text-indent:60%;">---胡峻峥</p>
				  			 </div>
				  		</div>
				  		
				  		<div style="width:30%; float:left;">
				    		<img src="image/hjzgg.jpg" title="我的形象" class="img-responsive img-circle blog-img" >
				    	</div>
				  </div>
				</div>
				
				<h3 class="catListTitle">我的组件</h3>
				<div class="panel panel-default">
				  <div class="panel-body">
			    	<!-- 计数器  -->
					<div align="center" style="font-family:KaiTi;font-size:23px">欢迎第<a href="http://www.amazingcounters.com"><img style="vertical-align: middle;" border="0" src="http://cc.amazingcounters.com/counter.php?i=3195664&c=9587305" alt="AmazingCounters.com"></a>位访客!</div>
					<!-- 时钟 -->
					<div align="center"><embed wmode="transparent" src="http://chabudai.sakura.ne.jp/blogparts/honehoneclock/honehone_clock_tr.swf" quality="high" bgcolor="#ffffff" width="160" height="70" name="honehoneclock" align="middle" allowscriptaccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer"></div>
					<br>
					<!-- 音乐播放器 
					<embed style="background-color:#3c3c3c" frameborder="no" border="0" marginwidth="0" marginheight="0" width=327 height=280 src="http://music.163.com/outchain/player?type=0&id=95138311&auto=1&height=430"></embed>
					<br><br>-->
					
					<div style="background-color:#3c3c3c; padding:10px;">
						<a href="http://user.qzone.qq.com/2570230521" target="_blank"><img src="http://r.qzone.qq.com/cgi-bin/cgi_get_user_pic?openid=0000000000000000000000003A3738B1&amp;pic=4.jpg&amp;key=956ff9858ce0dbb5980c5b47cfe5d940" title="欢迎访问我的QQ空间" data-bd-imgshare-binded="1" width="305"></a>
					</div>
					<br>
					<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2570230521&site=qq&menu=yes"><img id="contact_myqq" style="position: relative; left:0px; top:0px;" onload="hjzgg_init(this);" border="0" src="http://wpa.qq.com/pa?p=1:2570230521:13" alt="有事您Q我" title="有事您Q我"></a>
				  </div>
				</div>
				
				<h3 class="catListTitle">我的标签</h3>
				<div class="panel panel-default">
				  <div class="panel-body">
				  	<div class="list-group">
				  		 <c:forEach var="groupItem" items="${session.someJspGetAllBlogGroup}">
					  	 	<a href="blogAction!groupJspGetOneGroup?blogGroupId=${groupItem.groupId}" class="list-group-item">${groupItem.groupName}<span class="badge">${fn:length(groupItem.article)}</span></a>
					  	 </c:forEach>
					</div>
				  </div>
				</div>
				
				<h3 class="catListTitle">我的相册</h3>
				<div class="panel panel-default">
				  <div class="panel-body">
				  	<div class="list-group">
				  		 <c:forEach var="groupItem" items="${session.pictureGroupJspGetAllGroups}">
					  	 	<a href="pictureAction!pictureJspGetOneGroup?groupId=${groupItem.groupId}" class="list-group-item">${groupItem.groupName}<span class="badge">${fn:length(groupItem.pictures)}</span></a>
					  	 </c:forEach>
					</div>
				  </div>
				</div>
			</div>
		    <div class="col-xs-12 col-sm-6 col-md-8">
		    	<%
			    	int pageCur=0, pageBegin=0, pageTot=0, num= 0;
				    final int pageSize = 1;//每一面显示的文章的数目
					List<BlogArticle> articleList = (List<BlogArticle>)session.getAttribute("searchArticles");
				    num = articleList.size();//总个数 
				    pageTot = num%pageSize == 0 ? num/pageSize : num/pageSize+1;//总页数
				    if(num == 0) pageTot = 0;
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
							<div class="panel panel-default blog-panel">
							  <div class="blog-panel-heading">
							    <h3 class="panel-title"><span class="glyphicon glyphicon-send blog-icon" aria-hidden="true"></span>${article.articleTitle}</h3>
							  </div>
							  <div class="panel-body">
							     	<a style="text-decoration: none;" href="blogAction!articleJspGetOneArticle?articleId=${article.articleId}">${article.articleSummary}<b>......</b></a>
							     	<div class="blog-postDesc">posted @ ${article.articleBuildTime} 胡峻峥 阅读(${article.articleReadingCount}) 评论(${fn:length(article.comments)})  <a href="transferStation.jsp?articleId=${article.articleId}" rel="nofollow">编辑</a></div>
							  </div>
							</div>
				<%
						}
				%>
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
							 <c:when test="${pageCur ne pageTot && pageTot ne 0}">
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
		<h3 class="blog-footer">Copyright ©2015 小眼儿</h3>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" style="margin-top: 20%;" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">访问权限: 请输入PrimaryKey...</h4>
	      </div>
	      <div class="modal-body">
	      		<div class="input-group">
				  <span class="input-group-addon" id="basic-addon1">PrimaryKey:</span>
				  <input type="text" class="form-control" id="myPrimaryKey" placeholder="PrimaryKey" aria-describedby="basic-addon1">
				</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" id="submit_primaryKey_btn" onclick="location.href='transferStation.jsp?primaryKey='+$('#myPrimaryKey')[0].value;">确认</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
<script type="text/javascript">
	function myGoTo(pageIndex){
		location.href = "blogAction!searchArticles?" + pageIndex;
	}
	
	function myDumpTo(pageTot){
		if($("#pageTo")[0].value=="" || isNaN($("#pageTo")[0].value)) return;
		var page_tot = parseInt(pageTot, 10);
		var dump_page = parseInt($("#pageTo")[0].value, 10);
		if(dump_page<1 || dump_page>page_tot) return;
		myGoTo("page=" + dump_page);
	}
	
	$("#myModal")[0].onkeydown = function(event_e){ //模态框捕获回车键
        if(window.event)  
         	event_e = window.event;  
        var int_keycode = event_e.charCode||event_e.keyCode;  
        if(int_keycode ==13){
      	   $("#submit_primaryKey_btn").click();
   		}
  	}  
</script>
</html>