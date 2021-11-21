<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!Doctype html>
<html lang="en">

    <head>

        <title>Login Page</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Reference Bootstrap files -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css"
              integrity="sha512-YWzhKL2whUzgiheMoBFwW8CKV4qpHQAEuvilg9FAn5VJUDwKZZxkJNuGM4XkWuk94WCrrwslk8yWNGmY1EduTA=="
              crossorigin="anonymous" referrerpolicy="no-referrer"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
                crossorigin="anonymous"></script>
    </head>

    <body>

        <div class="d-flex justify-content-center alignItem mt-5">

            <div id="loginbox" style="margin: auto;"
                 class="col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">

                <div class="card card-info">

                    <div class="card-header bg-dark text-light">
                        Sign In
                    </div>
                    <div style="padding-top: 30px" class="card-body">

                        <!-- Login Form -->
                        <form:form action="${pageContext.request.contextPath}/authUser"
                                   method="POST" class="form-horizontal">

                            <!-- Place for messages: error, alert etc ... -->
                            <div class="form-group">
                                <div class="col-xs-15">
                                    <div>

                                        <!-- Check for login error -->

                                        <c:if test="${param.error != null}">

                                            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                                Invalid username and password.
                                            </div>

                                        </c:if>
                                        <c:if test="${param.logout != null}">

                                            <div class="alert alert-success col-xs-offset-1 col-xs-10">
                                                You have been logged out.
                                            </div>

                                        </c:if>


                                    </div>
                                </div>
                            </div>

                            <!-- User name -->
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-text" id="addon1"><i class="fas fa-user"></i></span>

                                <input type="text" name="user" placeholder="username" class="form-control">
                            </div>

                            <!-- Password -->
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-text"><i class="fas fa-key"></i></span>

                                <input type="password" name="pass" placeholder="password" class="form-control">
                            </div>

                            <!-- Login/Submit Button -->
                            <div style="margin-top: 10px" class="form-group">
                                <div class="col-sm-6 controls">
                                    <button type="submit" class="btn btn-success">Login</button>
                                </div>
                            </div>

                        </form:form>

                    </div>

                </div>

            </div>

        </div>

    </body>
</html>