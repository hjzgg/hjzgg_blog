<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>操作提示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="./css/404.css">
</head>
<body>
<div class="wrap" style="height: 500px;">
	<h1 style="margin-top: 0px;">hjzgg——blog提示：</h1>
	<span style="font-weight: 800; font-size: 20px; color: red; z-index:10;">&nbsp;&nbsp;&nbsp;&nbsp;${session.operations}</span>
	<div class="banner" style="position: absolute; left:0px; top:0px; width:100%; height:500px; filter:alpha(opacity=70); -moz-opacity:0.70; opacity:0.70;">
		<img height="400" width="300" src="./image/messageImages/operations.png" alt="操作提示" title="操作提示"/>
		<div class="page">
			<h2>Dude, the message of you operations!</h2>
		</div>
	</div>
</div>
</body>
</html>

