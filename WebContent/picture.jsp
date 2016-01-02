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

<title>相册分组图片</title>
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
	   		<iframe name="uploadFrame" src="./jsImageUpload/index.html" style="width:100%; height:100%;"></iframe>
	   </div>
	   <br>
	   <center><button type="button" class="btn btn-info" onclick="$('#operationMsgDivOne')[0].style.display='none'; location.reload();">关闭</button></center>
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
		        <li><a href="pictureAction!pictureGroupJspGetAllGroups">相册</a></li>
		      </ul>
		      
		      <ul class="nav navbar-nav navbar-right blog_nav">
		            <li class="active"><a href="javascript:void(0)" onclick="$('#picturePreviewDivOne')[0].style.display='';">点击预览</a></li>
		            <c:if test="${!(empty session.primaryKey)}">
		            	<li><a href="javascript:void(0)" onclick="$('#operationMsgDivOne')[0].style.display='';">上传图片</a></li>
		            </c:if>
		      </ul>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
		
		<div class="row">
		  <div class="col-xs-12 col-md-12">
		  		<div class="panel panel-default blog-panel">
				  <!-- Default panel contents -->
				  <div class="blog-panel-heading">
				  	相册分组: ${session.pictureJspGetOneGroup.groupName}
				  </div>
				  <!-- List group -->
				  <div class="panel-body">
				  	<div class="row">
					  	  <c:forEach var="myPicture" items="${session.pictureJspGetOneGroup.pictures}">
			  				<div class="col-xs-4 col-md-2" style="height: 200px; display: table;">
						  	  	<a target="_blank" href="showOnePicture.jsp?showOnePictureJsp_pictureName=${myPicture.pictureName}&showOnePictureJsp_picturePath=${myPicture.picturePath}&showOnePictureJsp_groupName=${session.pictureJspGetOneGroup.groupName}" class="thumbnail" style="display:table-cell; vertical-align: middle;">
									<img src="${myPicture.picturePath}" alt="${myPicture.pictureName}" title="${myPicture.pictureName}" width="100"/>
					 			</a>
				  			 </div>
					  	  </c:forEach>
				  	 </div>
				  </div>
				</div>
		  </div>
		</div>
		<div class="panel panel-default blog-panel" id="commentsList">
			  <div class="blog-panel-heading">																		
			    	<h3 class="panel-title"><span class="glyphicon glyphicon-road" aria-hidden="true"></span>&nbsp;&nbsp;评论列表</h3>
			  </div>
			  <div class="panel-body" style="max-height: 400px; overflow: auto;">
				  <c:forEach var="comment" items="${session.pictureJspGetOneGroup.comments}" varStatus="status">
					  <div class="panel-body">
					  		<div class="panel panel-success" style="margin-bottom: 0px;">
					  			<div class="panel-heading">
					  				<a name="${status.count}F">#${status.count}楼 </a> &nbsp; &nbsp; ${comment.commentTime} &nbsp; &nbsp; <a href="javascript:void(0)" onclick="replay('${status.count}')">回复</a>
					  			</div>
					  			<div class="panel-body">
					  				${comment.commentContent}
					  			</div>
					  		</div>
					  </div>
				  </c:forEach>
				</div>
		</div>
		<form action="#" method="post" id="newPictureCommentForm">
    		<input type="hidden" name="pictureCommentForm.groupId" value="${session.pictureJspGetOneGroup.groupId}"/>
	    	<div class="panel panel-default blog-panel">
				  <div class="blog-panel-heading">																		
				    <h3 class="panel-title"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span>&nbsp;&nbsp;欢迎评论</h3>
				  </div>
				  <div class="input-group input-group-lg" style="margin-left: 12px; margin-right: 10px; margin-top: 10px;">
					  	<span class="input-group-addon" id="sizing-addon1">联系方式(if convenient):</span>
					  	<input type="text" class="form-control" name="pictureCommentForm.commentPeopleContact" placeholder="your e-mail" aria-describedby="sizing-addon1">
				  </div>
				  <div class="panel-body" style="padding-left: 20%;">
		  	 			<jsp:include page="./umeditor/index.html" flush="true"/>
				  </div>
				  <center><button type="button" class="btn btn-info" onclick="formAppendSubmit();">提交评论</button></center>
			</div>
		</form>
	</div>
	
	<!-- Modal 模态对话框-->
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
	<!-- 相册预览 -->
	<div class="jumbotron" id="picturePreviewDivOne" style="position: fixed; left:25%; top:15%;z-index: 2; padding: 20px; display: none;">
	   	<div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="width:600px; height:400px; padding: 10px;">
			  <!-- Indicators -->
			   <ol class="carousel-indicators">
				   <c:forEach var="myPicture" items="${session.pictureJspGetOneGroup.pictures}" varStatus="status">
				   	  <c:choose>
				   	  	<c:when test="${status.count eq 1}">
	  				  		<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
	  				  	</c:when>
	  				  	<c:otherwise>
				      		<li data-target="#carousel-example-generic" data-slide-to="${status.count-1}"></li>
				      	</c:otherwise>
				      </c:choose>
			  	   </c:forEach>
			  </ol>
			
			  <!-- Wrapper for slides -->
			  <div class="carousel-inner" role="listbox" style="width: 580px; height:380px;">
			  	<c:forEach var="myPicture" items="${session.pictureJspGetOneGroup.pictures}" varStatus="status">
  				 	<c:choose>
  				 		<c:when test="${status.count eq 1}">
  				 			<div class="item active" style="text-align: center; width: 580px; height:380px;">
						      <img src="${myPicture.picturePath}" alt="${myPicture.pictureName}" style="max-height: 380px; max-width: 580px; display: inline-block;">
						      <div class="carousel-caption">
						       	 ${myPicture.pictureName}
						      </div>
						    </div>
  				 		</c:when>
  				 		<c:otherwise>
  				 			<div class="item" style="width: 580px; height:380px;" >
  				 			  <div style="display: table; width: 580px; height:380px; text-align: center;">
  				 			  	 <div style="display: table-cell; vertical-align: middle;">
						      	 	<img src="${myPicture.picturePath}" alt=""${myPicture.pictureName}"  style="max-height: 380px; max-width: 580px;">
								  </div>						      	 	
						      </div>
						      <div class="carousel-caption">
						      	"${myPicture.pictureName}
						      </div>
				   		     </div>
  				 		</c:otherwise>
  				 	</c:choose>
		  	    </c:forEach>
			  </div>
			
			  <!-- Controls -->
			  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
			    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
			    <span class="sr-only">Previous</span>
			  </a>
			  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
			    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			    <span class="sr-only">Next</span>
			  </a>
		  </div>
	   <br>
	   <center><button type="button" class="btn btn-info" onclick="$('#picturePreviewDivOne')[0].style.display='none';">关闭</button></center>
	</div>
		
</body>
<script type="text/javascript">
	
	function formAppendSubmit(){
		var myform=$('#newPictureCommentForm'); 
		var tmpInput=$("<input type='text' name='pictureCommentForm.pictureCommentContent'/>");
		if(!hasContent()){
			alert("请输入内容！");
			return ;
		}
		tmpInput.attr("value", getContent()); 
		myform.append(tmpInput);
		
		$.ajax({
		    type:"post",
		    url: "pictureAction!newPictureComment",
		    data: myform.serialize(),
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
	function replay(floor){
		setContent("<b>回复  <span style='text-decoration:underline; color:red; cursor:pointer' onclick='location.href=\"#"+floor+"F\"'>#"+floor+"楼 </span>:<b/>", false);
		setContent("<p>&nbsp;&nbsp;&nbsp;&nbsp;</p>", true);
		window.scrollTo(0,document.body.scrollHeight);
	}
</script>
</html>