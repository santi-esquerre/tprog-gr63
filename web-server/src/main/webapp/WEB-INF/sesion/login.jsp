<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">


<% %>

<head>
    <title>EventosUY - Login</title>
    <jsp:include page="/WEB-INF/template/head.jsp" />
</head>

<body class="bg-gradient-primary">
    

    <div class="container">

        <!-- Outer Row -->
        <div class="row justify-content-center">

            <div class="col-lg-6 col-md-8 col-sm-10 col-12">

                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <!-- <div class="col-lg-6 d-none d-lg-block bg-login-image"></div> -->
                            <div class="col-lg-12">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4">Bienvenido</h1>
                                    </div>
                                    <form id="loginForm" action="./index.html" method="post">
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="usuario" type="text" placeholder="Enter your name..." required/>
                                        <label for="usuario">Nickname o correo electrónico</label>
                                        <div class="invalid-feedback" data-sb-feedback="usuario:required">Debe ingresar un nickname o correo electrónico</div>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="clave" type="password" placeholder="name@example.com" minlength="8" required/>
                                        <label for="clave">Contraseña</label>
                                        <div class="invalid-feedback" data-sb-feedback="clave:required">Debe ingresar su contraseña</div>
                                    </div>
                                    <!-- Submit success message-->
                                    <!---->
                                    <!-- This is what your users will see when the form-->
                                    <!-- has successfully submitted-->
                                    <div class="d-none" id="submitSuccessMessage">
                                        <div class="text-center mb-3">
                                            <div class="fw-bolder">Form submission successful!</div>
                                            To activate this form, sign up at
                                            <br />
                                            <a href="https://startbootstrap.com/solution/contact-forms">https://startbootstrap.com/solution/contact-forms</a>
                                        </div>
                                    </div>
                                    <!-- Submit error message-->
                                    <!---->
                                    <!-- This is what your users will see when there is-->
                                    <!-- an error submitting the form-->
                                    <div class="d-none" id="submitErrorMessage"><div class="text-center text-danger mb-3">Error sending message!</div></div>
                                    <!-- Submit Button-->
                                    <div class="d-grid"><button class="btn btn-primary btn-lg" id="submitButton" type="submit" form="loginForm">Iniciar Sesión</button></div>
                                </form>
                                    <hr>
                                    <div class="text-center">
                                        <a class="small" href="registroUsuario.html">Crea tu cuenta!</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    

    

</body>

</html>