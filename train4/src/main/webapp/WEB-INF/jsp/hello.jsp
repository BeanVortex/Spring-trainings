<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>

        Hello ${student.firstName} ${student.lastName} from ${countryOptions.get(student.country)}

        <ul>
            <c:forEach items="${student.os}" var="tmp">
                <li>
                        ${tmp}
                </li>
            </c:forEach>
        </ul>
    </body>
</html>