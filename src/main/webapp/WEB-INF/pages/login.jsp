<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>--%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Spring App</title>

</head>

<body>
    <div>
        <form name="loginForm" method="post" action="/j_spring_security_check">
            <fieldset>
                <legend>Login</legend>
                <label for="email">Email</label>
                <input type="text" id="email" name="username">
                <label for="password">Password</label>
                <input type="password" id="password" name="password">

                <button type="submit">Log in</button>
            </fieldset>
        </form>
    </div>

</body>
</html>