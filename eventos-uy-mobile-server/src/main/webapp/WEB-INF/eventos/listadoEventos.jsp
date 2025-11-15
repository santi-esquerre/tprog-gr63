<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Eventos UY - Eventos</title>
	<jsp:include page="/WEB-INF/template/head.jsp" />
</head>
<body class="d-flex flex-column h-100">
<jsp:include page="/WEB-INF/template/header.jsp" />
<style>
	.card-img-top {
	  width: 100%;
	  object-fit: cover; /* recorta lo que sobra */
	  aspect-ratio: 4/3; /* default para móviles */
	}
	
	@media (min-width: 768px) { /* tablets y más grandes */
	  .card-img-top {
	    aspect-ratio: 16/9;
	  }
}

</style>

	<main class="flex-shrink-0">
		<div class="container mt-5 py-5">
			 <div class="text-center mb-5">
                <h1 class="fw-bolder">Eventos</h1>
             </div>

			 <div id="cards" class="row"> 
			 </div>
		
			<div id="observador-agregar-eventos"></div>
		
		</div>
	</main>

		<template id="card-template">
			<div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
				<a href="" class="evento-link text-decoration-none">
					<div class="card shadow-sm h-100"> 
	                    <img
	                         class="card-img-top"
	                         src=""
	                          alt="Banner evento"
	                          />
	                    <div class="card-body">
        					<p class="fw-normal card-name"></p>
        					<p class="fw-lighter card-desc"></p>
      					</div>				
					</div>
				</a>
			</div>
		
		</template>
		

		<script>
  			const basePath = "<%= request.getContextPath() %>";
		</script>
		
		<script src="media/js/pages/listadoEventos.js"></script>
        <!-- Bootstrap core JS-->
        <script src="media/js/bootstrap.min.js"></script>
        <!-- Core theme JS-->
        <script src="media/js/scripts.js"></script>
</body>
</html>