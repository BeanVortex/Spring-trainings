<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        The customer name: ${customer.firstName} ${customer.lastName}
        <br/>
        Free pass : ${customer.freePass}
        <br>
        postal code: ${customer.postalCode}
        <br>
        course code: ${customer.courseCode}
    </body>
</html>
