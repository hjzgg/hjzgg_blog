<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 为了确保适当的绘制和触屏缩放，需要在 <head> 之中添加 viewport 元数据标签。 -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>给我留言</title>
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
	<div class="jumbotron" id="operationMsgDivOne" style="position: fixed; left:5%; top:5%;z-index: 2; padding: 20px; display: none;">
	   <div class="container" style="height:500px;" id="operationMsgDivTwo">
	   </div>
	   <br>
	   <center><button type="button" class="btn btn-info" onclick="$('#operationMsgDivOne')[0].style.display='none'; location.reload();">知道了</button></center>
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
		        <li class="active"><a href="messageAction!leaveWordJspGetAllWords">留言板</a></li>
		        <li><a href="#">联系</a></li>
		        <li><a href="pictureAction!pictureGroupJspGetAllGroups">相册</a></li>
		      </ul>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
		
		<div class="row">
		  <div class="col-xs-12 col-md-12">
		  		<div class="panel panel-default blog-panel">
					  <!-- Default panel contents -->
					  <div class="blog-panel-heading"><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>&nbsp;&nbsp;留言板</div>
				  	  <div class="panel-body">
				  	  	<c:forEach var="leaveWord" items="${session.leaveWordJspGetAllWords}" varStatus="status">
				  	  		<div class="panel panel-success">
				  	  			<div class="panel-heading">留言条#${status.count}&nbsp;&nbsp;时间:${leaveWord.wordTime}</div>
				  	  			<div class="panel-body">${leaveWord.wordContent}</div>
				  	  		</div>
				  	  	</c:forEach>
					  </div>
				</div>
				
		  		<div class="panel panel-default blog-panel">
				  <!-- Default panel contents -->
				  <div class="blog-panel-heading"><span class="glyphicon glyphicon-leaf" aria-hidden="true"></span>&nbsp;&nbsp;欢迎给hjzgg留言</div>
				  <!-- List group -->
				  <ul class="list-group">
				    <li class="list-group-item" style="padding-top: 20px;">
				    	<span class="label label-default blog-label-1">留言内容:</span>
				    	<br><br>
						<textarea rows="5" style="width:100%" id="wordContent" name="message" placeholder="请输入消息（不要超过100个字符）"></textarea>
				    </li>
				  </ul>
				  <center><button type="button" onclick="toLeaveWord()" class="btn btn-info" style="margin-top:10px;" onclick="">确认留言</button></center>
				</div>
		  </div>
		</div>
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
	function toLeaveWord(){
		if($("#wordContent")[0].value==""){
			alert("留言内容不能为空!");
			return ;
		}
		$.ajax({
		    type:"post",
		    url: "messageAction!newLeaveWord",
		    data: "wordContent="+$("#wordContent")[0].value,
		    success:
		        function(dataResponses){
		            $("#operationMsgDivTwo")[0].innerHTML = dataResponses;
		            $("#operationMsgDivOne")[0].style.display = "";
		        },
		    error:
		        function(dataResponses){
		    		alert("操作失败:" + dataResponses);
		        }
		});
	}
</script>
</html>