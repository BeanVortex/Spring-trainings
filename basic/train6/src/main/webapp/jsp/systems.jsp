<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: darkdeveloper
  Date: 11/21/21
  Time: 1:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Systems</title>
    </head>
    <body>
        <p>
            Systems:
        </p>

        <p>
            Username: <security:authentication property="principal.username"/>
        </p>
        <p>
            Roles: <security:authentication property="principal.authorities"/>
        </p>
    </body>
</html>
