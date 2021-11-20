<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: darkdeveloper
  Date: 11/20/21
  Time: 9:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Login</title>
        <style>
            input {
                padding: 0.5rem;
                margin: 0.3rem 0.5rem;
                border: 1px solid #333;
                border-radius: 10px;
            }
            .failed{
                color: indianred;
            }
        </style>
    </head>
    <body>

        <form:form action="${pageContext.request.contextPath}/authUser" method="POST">
            <c:if test="${param.error != null}">
                <i class="failed">Sorry entered wrong username/password</i>
            </c:if>
            <p>
                UserName:
                <input type="text" name="username"/>
            </p>
            <p>
                Password:
                <input type="password" name="password"/>
            </p>
            <input type="submit" value="login"/>
        </form:form>

    </body>
</html>
