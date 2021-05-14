<%@page import="com.Project"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<link rel="stylesheet" href="Views/style.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/main.js"></script>
</head>
<body>

<div class="container"><div class="row"><div class="col-6">

	<h1>Project Management</h1>
	
	<form id="formItem" name="formItem" method="post" action="index.jsp">
		<!--  Project Code:
		<input id="itemCode" name="itemCode" type="text"
					class="form-control form-control-sm">

		<br--> Project Name:
		<input id="itemName" name="itemName" type="text"
					class="form-control form-control-sm">

		<br> Project Price:
		<input id="itemPrice" name="itemPrice" type="text"
					class="form-control form-control-sm">
					
		<br> Project Description:
		<input id="itemDesc" name="itemDesc" type="text"
					class="form-control form-control-sm">
		
		<br>
		<input id="btnSave" name="btnSave" type="button" value="Save"
					class="btn btn-primary">
		<input type="hidden" id="hidItemIDSave"
					name="hidItemIDSave" value="">
	</form>
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>
	
	<br>
	<div id="divItemsGrid">
			<%
			Project itemObj = new Project();
					out.print(itemObj.readItems());
			%>
	</div>
</div> </div> </div>
</body>
</html>