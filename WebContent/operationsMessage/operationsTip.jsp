<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>操作提示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="../css/404.css">
</head>
<body>
<div class="wrap">
	<h1>hjzgg——blog提示：</h1>
	<span style="font-weight: 800; font-size: 20px; color: red;">&nbsp;&nbsp;&nbsp;&nbsp;${session.operations}</span>
	<div class="banner">
		<img src="../image/messageImages/operations.png" alt="操作提示" title="操作提示"/>
	</div>
	<div class="page">
		<h2>Dude, the message of you operations!</h2>
	</div>
	<div class="footer">
		<p>返回上一页：<a href="javascript:void(0)" onclick="self.location=document.referrer;">返回</a></p>
	</div>
</div>
</body>
</html>

