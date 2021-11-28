<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: darkdeveloper
  Date: 11/19/21
  Time: 5:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <p>
            Welcome ....
        </p>
        <form:form action="${pageContext.request.contextPath}/logout" method="post">
            <input type="submit" value="logout">
        </form:form>

        <p>
            User : <security:authentication property="principal.username"/>
        </p>
        <p>
            Roles : <security:authentication property="principal.authorities"/>
        </p>

        <security:authorize access="hasRole('ADMIN')">

            <p>
                <a href="${pageContext.request.contextPath}/systems">Systems</a>
            </p>
        </security:authorize>

        <security:authorize access="hasRole('MANAGER')">
            <p>
                <a href="${pageContext.request.contextPath}/leaders">Leaders</a>
            </p>
        </security:authorize>

    </body>
</html>
