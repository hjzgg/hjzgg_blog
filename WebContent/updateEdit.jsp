<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<script type="text/javascript">
	var updateEditJpsGetOneArticleContent = "${fn:replace(session.updateEditJpsGetOneArticle.articleContent, '\"', '\\\"')}";
</script>
</head>
<body>
	<!-- 新建分组 -->
	<div class="jumbotron" id="newBlogGroup" style="position: fixed; left:5%; top:10%; z-index: 2; padding: 20px; display: none;">
	   <div class="container">
	   		<div class="panel panel-default blog-panel">
			  <!-- Default panel contents -->
			  <div class="blog-panel-heading">更新随笔</div>
			  	<form action="blogAction!newBlogGroup" method="post" name="groupForm" id="newBlogGroupForm">
					  <!-- List group -->
					  <ul class="list-group">
					    <li class="list-group-item">
							<div class="input-group">
							  <span class="input-group-addon" id="basic-addon1">分组名称:</span>
							  <input type="text" class="form-control" placeholder="group-name" name="blogGroupForm.groupName" aria-describedby="basic-addon1">
							</div>
						</li>
					    
					    <li class="list-group-item" style="padding-top: 20px;">
					    	<span class="label label-default blog-label-1">分组描述:</span>
					    	<br><br>
							<textarea rows="10" style="width:100%" name="blogGroupForm.groupDescrib" placeholder="请输入分组描述（不要超过100字符）"></textarea>
					    </li>
					    
					    <li class="list-group-item" style="padding-top: 20px;">
					    	<center>
					    		<button type="button" class="btn btn-success" onclick="$('#newBlogGroupForm').submit();"><span class="glyphicon glyphicon-floppy-saved" aria-hidden="true"></span>&nbsp;创建</button>
					    		<button type="button" class="btn btn-warning" style="margin-left: 50px;"  onclick="$('#newBlogGroup')[0].style.display='none';"><span class="glyphicon glyphicon-floppy-remove" aria-hidden="true"></span>&nbsp;取消</button>
					    	</center>
					    </li>
					  </ul>
			  	</form>
			</div>
	   </div>
	</div>

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
		        <li><a href="contact.jsp">联系</a></li>
		        <li><a href="pictureAction!pictureGroupJspGetAllGroups">相册</a></li>
		      </ul>
		      
		      <ul class="nav navbar-nav navbar-right blog_nav">
		        <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">随笔管理<span class="caret"></span></a>
		          <ul class="dropdown-menu blog-menu">
		            <li><a href="blogAction!editJspGetAllGroups">新随笔</a></li>
		            <li><a href="blogAction!editAllJpsGetAllArticles">编辑随笔</a></li>
		            <li><a href="blogAction!deleteJpsGetAllArticles">删除随笔</a></li>
		            <li role="separator" class="divider"></li>
		            <li><a href="javascript:void(0)" onclick="$('#newBlogGroup')[0].style.display='';">新建分组</a></li>
		            <li><a href="#">删除分组</a></li>
		            <li><a href="#">重命名分组</a></li>
		          </ul>
		        </li>
		      </ul>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
		
		<!-- 警告新建随笔之前想好随笔的分组，如果分组不存在，要新建分组 -->
		<div class="alert alert-warning alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<strong>提示:</strong>&nbsp;&nbsp;新建随笔之前想好随笔的分组，如果分组不存在，要新建分组!
		</div>

		<div class="row">
		  <div class="col-xs-12 col-md-12">
			<div class="panel panel-default blog-panel">
				  <!-- Default panel contents -->
				  <div class="blog-panel-heading">更新随笔</div>
				  <!-- List group -->
				  <form action="blogAction!updateBlogArticle" method="post" name="articleForm" id="newArticleForm">
				  	  <input type="hidden" name="blogArticleForm.articleId" value="${session.updateEditJpsGetOneArticle.articleId}">
					  <ul class="list-group">
					    <li class="list-group-item">
							<div class="input-group">
							  <span class="input-group-addon" id="basic-addon1">博客标题:</span>
							  <input type="text" class="form-control" placeholder="blog-title" name="blogArticleForm.articleTitle" aria-describedby="basic-addon1" value="${session.updateEditJpsGetOneArticle.articleTitle}">
							</div>
						</li>
					    
					    <li class="list-group-item" style="padding-top: 20px;">
					    	<span class="label label-default blog-label-1">博客内容:</span>
					    	<br><br>
							<iframe name="myUeditor" style="width:100%; margin-left:-8px; height:630px;" src="ueditor/index.html"></iframe>
					    </li>
					    
					    <li class="list-group-item" style="padding-top: 20px;">
					    	<span class="label label-default blog-label-1">随笔分类(默认保存到未分组中):</span>
					    	<br><br>
					    	<div class="checkbox">
						    	<c:forEach var="group" items="${session.editJspGetAllGroups}">
						    		<c:if test="${group.groupName ne '未分组'}">
						    				<c:set var="isCheck" value="false"/>
						    				<c:forEach var="tmpGroup" items="${session.updateEditJpsGetOneArticle.groups}">
						    					<c:if test="${tmpGroup.groupName eq group.groupName}">
						    						<c:set var="isCheck" value="true"/>
						    					</c:if>
						    				</c:forEach>
						    				<label style="margin-left: 5px; margin-right: 5px;">
							    				<c:choose>
							    					<c:when test="${isCheck}">
													     <input name="blogArticleForm.articleType" value="${group.groupName}" type="checkbox" checked="checked"> ${group.groupName}
							    					</c:when>
													<c:otherwise>
													     <input name="blogArticleForm.articleType" value="${group.groupName}" type="checkbox"> ${group.groupName}
													</c:otherwise>						    					
							    				</c:choose>
							    			</label>
						    		</c:if>
						    	</c:forEach>
						    </div>
					    </li>
					    
					    <li class="list-group-item" style="padding-top: 20px;">
					    	<center>
					    		<button type="button" class="btn btn-success" onclick="formAppendSubmit();"><span class="glyphicon glyphicon-floppy-saved" aria-hidden="true"></span>&nbsp;保存</button>
					    		<button type="button" class="btn btn-warning" style="margin-left: 50px;" onclick="location.href='index.jsp';"><span class="glyphicon glyphicon-floppy-remove" aria-hidden="true"></span>&nbsp;取消</button>
					    	</center>
					    </li>
					  </ul>
				  </form>
			</div>
		  </div>
		</div>
	</div>
</body>

<script type="text/javascript">
	function formAppendSubmit(){
		var myform=$('#newArticleForm'); 
		var tmpInput=$("<input type='text' name='blogArticleForm.articleContent'/>");
		if(!myUeditor.window.ue.hasContents()){
			alert("请输入内容！");
			return ;
		}
		tmpInput.attr("value", myUeditor.window.getContentInsideBody());
		myform.append(tmpInput);
		myform.submit();
	}
</script>

</html>