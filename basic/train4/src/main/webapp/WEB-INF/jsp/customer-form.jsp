<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <style>
            .error {
                color: red
            }
        </style>
    </head>
    <body>
        <form:form action="processCustomer" modelAttribute="customer">
            First name: <form:input path="firstName"/>
            <form:errors path="firstName" cssClass="error"/>
            <br><br>
            Last name *: <form:input path="lastName"/>
            <form:errors path="lastName" cssClass="error"/>
            <br><br>
            freePass *: <form:input path="freePass"/>
            <form:errors path="freePass" cssClass="error"/>
            <br><br>
            Postal Code *: <form:input path="postalCode"/>
            <form:errors path="postalCode" cssClass="error"/>
            <br><br>
            Postal Code *: <form:input path="courseCode"/>
            <form:errors path="courseCode" cssClass="error"/>
            <br><br>
            <input type="submit">
        </form:form>
    </body>
</html>