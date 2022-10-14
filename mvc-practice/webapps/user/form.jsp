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
<h1>USER form</h1>

<form method="POST" action="/users">
    <div>
        <label for="userId">
            사용자 아이디
        </label>
        <input class="form-control" id="userId" name="userId" placeholder="User ID">
    </div>

    <div>
        <label for="name">
            이름
        </label>
        <input class="form-control" id="name" name="name" placeholder="Name">
    </div>
    <button type="submit">회원가입</b입tton>
</form>

</body>
</html>
