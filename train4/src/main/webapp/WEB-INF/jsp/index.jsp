<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Document</title>
    </head>
    <body>
        <form:form action="processForm" modelAttribute="student">
            First name: <form:input type="text" placeholder="firstname" path="firstName"/>
            <br>
            Last name: <form:input type="text" placeholder="lastname" path="lastName"/>
            <br>
            Country:
            <form:select path="country">
                <form:options items="${countryOptions}"/>
            </form:select>
            <br/>
            Java <form:radiobutton path="favLang" value="Java"/>
            C# <form:radiobutton path="favLang" value="C#"/>
            PHP <form:radiobutton path="favLang" value="PHP"/>
            C <form:radiobutton path="favLang" value="C"/>
            <br/>
            Operating System:
            Linux <form:checkbox path="os" value="Linux"/>
            Mac <form:checkbox path="os" value="Mac"/>
            Windows <form:checkbox path="os" value="Windows"/>
            <br/>
            <input type="submit" value="submit"/>
        </form:form>

        <a href="<c:url value="/customer-form"/>">customer-form</a>
    </body>
</html>
