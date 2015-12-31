<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.blog.entriy.BlogArticle"%>
<%@page import="com.blog.entriy.BlogGroup"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 为了确保适当的绘制和触屏缩放，需要在 <head> 之中添加 viewport 元数据标签。 -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>随笔分类</title>

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

</head>
<body>
	<!-- 新建分组 -->
	<div class="jumbotron" id="newBlogGroup" style="position: fixed; left:5%; top:5%; z-index: 2; padding: 20px; display: none;">
	   <div class="container">
	   		<div class="panel panel-default blog-panel">
			  <!-- Default panel contents -->
			  <div class="blog-panel-heading">添加随笔分组</div>
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
	
	<!-- 更改分组面板 -->
	<div class="jumbotron" id="renameArticleGroup" style="position: fixed; left:5%; top:5%; z-index: 2; padding: 20px; display: none;">
	   <div class="container">
	   		<div class="panel panel-default blog-panel">
			  <!-- Default panel contents -->
			  <div class="blog-panel-heading">更改随笔分组</div>
			  	<form action="blogAction!articleRenameGroup" method="post" name="groupForm" id="renameArticleGroupForm">
			  		  <input type="hidden" name="blogGroupForm.orgGroupName" id="renameArticleGroupForm_preGroupName" value=""/>
					  <!-- List group -->
					  <ul class="list-group">
					    <li class="list-group-item">
							<div class="input-group">
							  <span class="input-group-addon" id="basic-addon1">分组名称:</span>
							  <input type="text" class="form-control" placeholder="group-name" name="blogGroupForm.groupName" id="renameArticleGroupForm_groupName" aria-describedby="basic-addon1">
							</div>
						</li>
					    
					    <li class="list-group-item" style="padding-top: 20px;">
					    	<span class="label label-default blog-label-1">分组描述:</span>
					    	<br><br>
							<textarea rows="10" style="width:100%" name="blogGroupForm.groupDescrib" id="renameArticleGroupForm_groupDescrib" placeholder="请输入分组描述（不要超过100字符）"></textarea>
					    </li>
					    
					    <li class="list-group-item" style="padding-top: 20px;">
					    	<center>
					    		<button type="button" class="btn btn-success" onclick="$('#renameArticleGroupForm').submit();"><span class="glyphicon glyphicon-floppy-saved" aria-hidden="true"></span>&nbsp;更改</button>
					    		<button type="button" class="btn btn-warning" style="margin-left: 50px;"  onclick="$('#renameArticleGroup')[0].style.display='none';"><span class="glyphicon glyphicon-floppy-remove" aria-hidden="true"></span>&nbsp;取消</button>
					    	</center>
					    </li>
					  </ul>
			  	</form>
			</div>
	   </div>
	</div>

	<!-- 删除分组提示 -->
	<div class="jumbotron" id="operationMsgDivOneCopy" style="position: fixed; left:5%; top:5%;z-index: 2; padding: 20px; display: none;">
	   <div class="container" style="height:500px;" id="operationMsgDivTwoCopy">
	   </div>
	   <br>
	   <center><button type="button" class="btn btn-info" onclick="$('#operationMsgDivOneCopy')[0].style.display='none';">知道了</button></center>
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
		        <li><a href="javascript:void(0)" onclick="$('#myModal').modal('show');">新随笔</a></li>
		        <li><a href="messageAction!leaveWordJspGetAllWords">留言板</a></li>
		        <li><a href="contact.jsp">联系</a></li>
		        <li class="active"><a href="pictureAction!pictureGroupJspGetAllGroups">相册</a></li>
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
			            <li><a href="javascript:void(0)" onclick="showTableTd('hjzgg_delete');">删除分组</a></li>
			            <li><a href="javascript:void(0)" onclick="showTableTd('hjzgg_rename');">更改分组</a></li>
			          </ul>
			        </li>
			      </ul>
		      </c:if>
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
		    	<div class="panel panel-default blog-panel">
				  	<div class="blog-panel-heading">
				  	 	<h3 class="panel-title"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;&nbsp;相册分组</h3>
				 	</div>
			       	<div class="panel-body">
			       	   <table class="table table-hover">
		       	   	      <thead>
						        <tr>
						          <th>标号</th>
						          <th>名称和描述</th>
						          <th style="text-align: center;">随笔数目</th>
						        </tr>
					      </thead>
					       <tbody>
							  <c:forEach var="group" items="${session.someJspGetAllBlogGroup}" varStatus="status">
						  		<tr style="cursor: pointer;">
						       		<td scope="row" style="vertical-align: middle;"  onclick="location.href='blogAction!groupJspGetOneGroup?blogGroupId=${group.groupId}';">
									    <h1>${status.count}<h1>
								    </td>
					 		   		<td style="vertical-align: middle;"  onclick="location.href='blogAction!groupJspGetOneGroup?blogGroupId=${group.groupId}';">
					    			   <div class="list-group">
										  <a href="javascript:void(0)" class="list-group-item">
										    <h4 class="list-group-item-heading">分组名称:&nbsp;${group.groupName}</h4>
										    <p class="list-group-item-text">分组描述:&nbsp;${group.groupDescrib}</p>
										  </a>
										</div>
									</td> 
									<td style="vertical-align: middle; text-align: center;"  onclick="location.href='blogAction!groupJspGetOneGroup?blogGroupId=${group.groupId}';">
							 			 <h1>${fn:length(group.article)}<h1>
								    </td>
									<td style="vertical-align: middle; display: none;" name="hjzgg_delete" id="hjzgg_deleteId${status.count}">
							 			<a class="btn btn-info" href="javascript:void(0)" style="float:right;" role="button" onclick="myDeleteArticleGroup('hjzgg_deleteId${status.count}', '${group.groupId}');">删除</a>
							 		</td>
							 		<td style="vertical-align: middle; display: none;" name="hjzgg_rename" id="hjzgg_renameId${status.count}">
							 			<a class="btn btn-info" href="javascript:void(0)" style="float:right;" role="button" onclick="$('#renameArticleGroupForm_preGroupName')[0].value='${group.groupName}'; $('#renameArticleGroupForm_groupName')[0].value='${group.groupName}'; $('#renameArticleGroupForm_groupDescrib')[0].value='${group.groupDescrib}'; $('#renameArticleGroup')[0].style.display='';">更改分组</a>
							 		</td>
								</tr>
							  </c:forEach>
					       </tbody>
				       </table>
				  </div>	
				</div>
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
	        <button type="button" class="btn btn-primary" onclick="location.href='transferStation.jsp?primaryKey='+$('#myPrimaryKey')[0].value;">确认</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
<script type="text/javascript">
	 
	var pre_id = null;
	function showTableTd(_id){
		if(pre_id!=null && pre_id != _id){
			$("td[name='" + pre_id +"']").each(
				function(){
					this.style.display = "none";
				}		
			);	
		}
		
		pre_id = _id;
		$("td[name='" + _id +"']").each(
			function(){
				this.style.display = "";
			}		
		);
	}
	
	function myDeleteArticleGroup(operationId, groupId){
		$.ajax({
		    type:"post",
		    url: "blogAction!deleteArticleGrouop",
		    data: "groupId="+groupId,
		    success:
		        function(dataResponses){
		            $("#"+operationId)[0].innerHTML = "<b style=\"color:red\">删除成功!</b>";
		            $("#operationMsgDivTwoCopy")[0].innerHTML = dataResponses;
		            $("#operationMsgDivOneCopy")[0].style.display = "";
		        },
		    error:
		        function(dataResponses){
		    		alert("操作失败:" + dataResponses);
		        }
		});
	}
	
</script>
</html>