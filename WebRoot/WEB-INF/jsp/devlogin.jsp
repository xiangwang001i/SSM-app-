<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>开发者登录</title>
    
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
          <section class="login_content">
            <form action="${pageContext.request.contextPath}/devlogin.html" method="post">
              <h1>APP开发者平台</h1>
              <span style="color: red">${error}</span>
              <div>
                <input type="text" class="form-control" name="devCode" placeholder="用户名" required="required" />
              </div>
              <div>
                <input type="password" class="form-control" value="123456" name="devPassword" placeholder="密码" required="required" />
              </div>
              <div>
                <button class="btn btn-success" type="submit">登录</button>
                <button class="btn btn-default" type="reset">重置</button>
              </div>
              <div class="clearfix"></div>
              <div class="separator">
                <p class="change_link">New to site?
                  <a href="#signup" class="to_register"> Create Account </a>
                </p>
                <div class="clearfix"></div>
                <br /> 
              </div>
            </form>
          </section>
        </div>
      </div>
    </div>
  </body>
</html>
