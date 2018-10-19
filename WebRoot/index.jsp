<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>APP后台管理平台</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="${pageContext.request.contextPath}/statics/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${pageContext.request.contextPath}/statics/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="${pageContext.request.contextPath}/statics/css/nprogress.css" rel="stylesheet">
    <!-- Animate.css -->
    <link href="https://colorlib.com/polygon/gentelella/css/animate.min.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="${pageContext.request.contextPath}/statics/css/custom.min.css" rel="stylesheet">
  </head>
  
  <body class="login">
    <div>
      <div class="login_wrapper">
        <div class="animate form login_form">
            <h1>APP信息管理平台</h1>
            <div>
               <a class="btn btn-link" href="${pageContext.request.contextPath}/backend.html">后台管理者入口</a><br/>
               <a class="btn btn-link" href="${pageContext.request.contextPath}/devlogin.html">开发者管理入口</a>
            </div>
        </div>
      </div>
    </div>
  </body>
</html>
