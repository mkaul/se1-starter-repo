<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>Parkhaus Kasse</title>
</head>
<body>
<h1><%= "Parkhaus Kasse" %></h1>
<form action="pay-servlet" method="get">
    <input type="number" name="amount">
    <input type="submit">
</form>
<a href="index.jsp">zurück zum Parkhaus</a>
</body>
</html>