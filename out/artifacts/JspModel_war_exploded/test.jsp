<%--
  Created by IntelliJ IDEA.
  User: lomuhoe
  Date: 2020-12-10
  Time: 오전 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@page import="mypkg.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="./common/common.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<ul class='pagination pagination-sm'><li><a href='boList.jsp&pageNumber=1&pageSize=10&mode=&keyword='>1</li></a>&nbsp;<li class='active'><a><font color='red'><b>2</b></font></a></li>&nbsp;<li><a href='boList.jsp&pageNumber=3&pageSize=10&mode=&keyword='>3</li></a>&nbsp;<li><a href='boList.jsp&pageNumber=4&pageSize=10&mode=&keyword='>4</li></a>&nbsp;</ul>

</body>
</html>
