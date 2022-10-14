<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Home-jsp</title>
</head>
<body>
<h1>USER List</h1>
<table>
    <thead>
    <tr>
        <th>#</th>
        <th>userId</th>
        <th>name</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user" varStatus="status">
        <tr>
            <th scope="row">${status.count}</th>
<%--            Getter 를 통해 값을 가져옴          --%>
            <td>${user.userId}</td>
            <td>${user.name}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
