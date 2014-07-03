<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Success Page</title>
</head>
<body>
Admin Details
<hr>
User Name   : ${userdetails.name} <br/>
Gender      : ${userdetails.gender} <br/>
Country     : ${userdetails.country} <br/>
About You   : ${userdetails.aboutYou} <br/>
Community   : ${userdetails.community[0]}  ${userdetails.community[1]} ${userdetails.community[2]}<br/>
Mailing List: ${userdetails.mailingList} 
</body>
</html>