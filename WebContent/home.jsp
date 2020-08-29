<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<!doctype html>
<%
	List blogs = (List) request.getAttribute("blogs");
%>
<html lang="en">

<head>
<title>Blog</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Work+Sans:400,700,900&display=swap"
	rel="stylesheet">

<link rel="stylesheet" href="fonts/icomoon/style.css">

<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/bootstrap-datepicker.css">
<link rel="stylesheet" href="css/jquery.fancybox.min.css">
<link rel="stylesheet" href="css/owl.carousel.min.css">
<link rel="stylesheet" href="css/owl.theme.default.min.css">
<link rel="stylesheet" href="fonts/flaticon/font/flaticon.css">
<link rel="stylesheet" href="css/aos.css">

<!-- MAIN CSS -->
<link rel="stylesheet" href="css/style.css">

</head>

<body data-spy="scroll" data-target=".site-navbar-target"
	data-offset="300">


	<div class="site-wrap" id="home-section">

		<div class="site-mobile-menu site-navbar-target">
			<div class="site-mobile-menu-header">
				<div class="site-mobile-menu-close mt-3">
					<span class="icon-close2 js-menu-toggle"></span>
				</div>
			</div>
			<div class="site-mobile-menu-body"></div>
		</div>



		<header class="site-navbar site-navbar-target" role="banner">

			<div class="container">
				<div class="row align-items-center position-relative">

					<div class="col-3 ">
						<div class="site-logo">
							<a href="index.html" class="font-weight-bold"> <img
								src="images/logo.png" alt="Image" class="img-fluid">
							</a>
						</div>
					</div>

					<div class="col-9  text-right">


						<span class="d-inline-block d-lg-none"><a href="#"
							class="text-white site-menu-toggle js-menu-toggle py-5 text-white"><span
								class="icon-menu h3 text-white"></span></a></span>



						<nav class="site-navigation text-right ml-auto d-none d-lg-block"
							role="navigation">
							<ul class="site-menu main-menu js-clone-nav ml-auto ">
								<li class="active"><a
									href="${pageContext.request.contextPath}/" class="nav-link">Home</a></li>
								<li><a href="${pageContext.request.contextPath}/login"
									class="nav-link">Sign In</a></li>
								<li><a href="${pageContext.request.contextPath}/register"
									class="nav-link">Sign Up</a></li>
								
							</ul>
						</nav>
					</div>


				</div>
			</div>

		</header>

		<div class="ftco-blocks-cover-1">
			<div class="site-section-cover overlay"
				style="background-image: url('images/hero_1.jpg')">
				<div class="container">
					<div
						class="row align-items-center justify-content-center text-center">
						<div class="col-md-5" data-aos="fade-up">
							<h1 class="mb-3 text-white">Blog</h1>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
								Soluta veritatis in tenetur doloremque, maiores doloribus
								officia iste. Dolores.</p>

						</div>
					</div>
				</div>
			</div>
		</div>


		<div class="site-section">

			<div class="container">

				<div class="row">

					<div class="mx-auto">
						
						<form class="form-inline"
							action="${pageContext.request.contextPath}/search" method="post">


							<div class="form-group mx-sm-3 mb-2">

								<input name="query" class="form-control" required="required" />
							</div>
							<div class="form-group">
								<input type="submit" value="Search"
									class="btn btn-primary btn-lg text-white">
							</div>

						</form>
					</div>
				</div>

				<div class="row">
					<c:forEach items="${blogs}" var="blog">
						<div class="col-lg-4 col-md-6 mb-4">
							<div class="post-entry-1 h-100">
								<a
									href="${pageContext.request.contextPath}/view?id=${blog.blogId}">
									<img src="images/img_1.jpg" alt="Image" class="img-fluid">
								</a>
								<div class="post-entry-1-contents"
									style="height: 250px; overflow: hidden">

									<h2>
										<a href="single.html">${blog.title}</a>
									</h2>
									<span class="meta d-inline-block mb-3">${blog.createdDate}
										<span class="mx-2">by</span> <a href="#">${blog.user.firstname}</a>
									</span>
									<p>${blog.description}</p>
								</div>
							</div>
						</div>
					</c:forEach>

				</div>


				<!--div class="col-12 mt-5 text-center">
					<span class="p-3">1</span> <a href="#" class="p-3">2</a> <a
						href="#" class="p-3">3</a> <a href="#" class="p-3">4</a>
				</div-->

			</div>
		</div>
		<!-- END .site-section -->

		<footer class="site-footer bg-light">
			<div class="container">
				<div class="row">
					<div class="col-lg-3">
						<h2 class="footer-heading mb-3">Instagram</h2>
						<div class="row">
							<div class="col-4 gal_col">
								<a href="#"><img src="images/insta_1.jpg" alt="Image"
									class="img-fluid"></a>
							</div>
							<div class="col-4 gal_col">
								<a href="#"><img src="images/insta_2.jpg" alt="Image"
									class="img-fluid"></a>
							</div>
							<div class="col-4 gal_col">
								<a href="#"><img src="images/insta_3.jpg" alt="Image"
									class="img-fluid"></a>
							</div>
							<div class="col-4 gal_col">
								<a href="#"><img src="images/insta_4.jpg" alt="Image"
									class="img-fluid"></a>
							</div>
							<div class="col-4 gal_col">
								<a href="#"><img src="images/insta_5.jpg" alt="Image"
									class="img-fluid"></a>
							</div>
							<div class="col-4 gal_col">
								<a href="#"><img src="images/insta_6.jpg" alt="Image"
									class="img-fluid"></a>
							</div>
						</div>
					</div>
					<div class="col-lg-8 ml-auto">
						<div class="row">
							<div class="col-lg-6 ml-auto">
								<h2 class="footer-heading mb-4">Quick Links</h2>
								<ul class="list-unstyled">
									<li><a href="#">About Us</a></li>
									<li><a href="#">Testimonials</a></li>
									<li><a href="#">Terms of Service</a></li>
									<li><a href="#">Privacy</a></li>
									<li><a href="#">Contact Us</a></li>
								</ul>
							</div>
							<div class="col-lg-6">
								<h2 class="footer-heading mb-4">Newsletter</h2>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
									Nesciunt odio iure animi ullam quam, deleniti rem!</p>
								<form action="#" class="d-flex" class="subscribe">
									<input type="text" class="form-control mr-3"
										placeholder="Email"> <input type="submit" value="Send"
										class="btn btn-primary">
								</form>
							</div>

						</div>
					</div>
				</div>
				<div class="row pt-5 mt-5 text-center">
					<div class="col-md-12">
						<div class="border-top pt-5">
							<p>
								<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
								Copyright &copy;
								<script>
									document.write(new Date().getFullYear());
								</script>
								All rights reserved | This template is made with <i
									class="icon-heart text-danger" aria-hidden="true"></i> by <a
									href="https://colorlib.com" target="_blank">Colorlib</a>
								<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
							</p>
						</div>
					</div>

				</div>
			</div>
		</footer>

	</div>

	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/jquery-migrate-3.0.0.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/jquery.sticky.js"></script>
	<script src="js/jquery.waypoints.min.js"></script>
	<script src="js/jquery.animateNumber.min.js"></script>
	<script src="js/jquery.fancybox.min.js"></script>
	<script src="js/jquery.stellar.min.js"></script>
	<script src="js/jquery.easing.1.3.js"></script>
	<script src="js/bootstrap-datepicker.min.js"></script>
	<script src="js/isotope.pkgd.min.js"></script>
	<script src="js/aos.js"></script>

	<script src="js/main.js"></script>

</body>

</html>

