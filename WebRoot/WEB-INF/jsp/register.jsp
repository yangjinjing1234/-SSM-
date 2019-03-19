<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fm" uri="http://www.springframework.org/tags/form" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'register.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <fm:form action="doRegister.html" method="post" modelAttribute="user" >
    	用户名:<fm:input path="userName" />
    	<fm:errors path="userName"></fm:errors> <br/>
    	密码: <fm:password path="userPassword"/>
    	<fm:errors path="userPassword"></fm:errors><br/><br/>
    	生日: <fm:input path="birthday"/>
    	<fm:errors path="birthday"></fm:errors><br/><br/>
    	性别: <fm:radiobutton path="gender" value="1"/>男<br/>
    	爱好 :<fm:checkbox path="phone" value="reading"/> 读书<br/>
    	城市:<fm:select path="address">
    		<fm:option value="beijing">北京</fm:option>
    		</fm:select><br/>
    	<input type="submit" value="提交"/>
    </fm:form>
  </body>
</html>
