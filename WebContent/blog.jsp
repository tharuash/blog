<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.blog.entity.Blog"%>
<%@ page import="javax.servlet.http.Cookie"%>

<!doctype html>
<%
	Blog blog = (Blog) request.getAttribute("blog");
%>
<%
	Cookie[] cks = request.getCookies();
	long id = 0L;
	boolean authenticated = false;
	if (cks != null) {
		for (int i = 0; i < cks.length; i++) {
			String name = cks[i].getName();
			String value = cks[i].getValue();
			if (name.equals("id") && Long.parseLong(value) != 0) {
				id = Long.parseLong(value);
				authenticated = true;
				break; // exit the loop and continue the page
			}
		}
	}
	boolean visibility = blog.isVisibility();
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

<link rel="stylesheet" href="/blog/fonts/icomoon/style.css">

<link rel="stylesheet" href="/blog/css/bootstrap.min.css">
<link rel="stylesheet" href="/blog/css/bootstrap-datepicker.css">
<link rel="stylesheet" href="/blog/css/jquery.fancybox.min.css">
<link rel="stylesheet" href="/blog/css/owl.carousel.min.css">
<link rel="stylesheet" href="/blog/css/owl.theme.default.min.css">
<link rel="stylesheet" href="/blog/fonts/flaticon/font/flaticon.css">
<link rel="stylesheet" href="/blog/css/aos.css">

<!-- MAIN CSS -->
<link rel="stylesheet" href="/blog/css/style.css">

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
								src="/blog/images/logo.png" alt="Image" class="img-fluid">
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

								<li class="active"><a href="${pageContext.request.contextPath}/" class="nav-link">Blog</a></li>
								
								<% if(!authenticated) { %>
								
									<li><a href="${pageContext.request.contextPath}/login"
										class="nav-link">Sign In</a></li>
								<% } %>

							</ul>
						</nav>
					</div>


				</div>
			</div>

		</header>

		<div class="ftco-blocks-cover-1">
			<div class="site-section-cover overlay"
				data-stellar-background-ratio="0.5"
				style="background-image: url('/blog/images/hero_1.jpg')">
				<div class="container">
					<div
						class="row align-items-center justify-content-center text-center">
						<div class="col-md-12">
							<span class="d-block mb-3 text-white" data-aos="fade-up"><%=blog.getCreatedDate()%>
								<span class="mx-2 text-primary">&bullet;</span> by <%=blog.getUser().getFirstname()%></span>
							<h1 class="mb-4" data-aos="fade-up" data-aos-delay="100"><%=blog.getTitle()%></h1>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="site-section">
			<div class="container">
				<div class="row">
					<div class="col-md-12 blog-content">
						<!--  p class="lead">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p-->
						<p><%=blog.getDescription()%></p>

						

						<%@ page import="java.util.List"%>
						<%@ page import="com.blog.entity.Comment"%>
						<%@ page import="com.blog.entity.User"%>
						<%
							List<Comment> comments = (List) blog.getComments();
						%>

						<div class="pt-5">
							<h3 class="mb-5"><%=comments.size()%>
								Comments
							</h3>
							<ul class="comment-list">
								<c:forEach items="${blog.comments}" var="comment">

									<li class="comment">
										<div class="vcard bio">
											<img src="images/person_4.jpg" alt="Image">
										</div>
										<div class="comment-body">
											<h3>${comment.user.firstname}${comment.user.lastname}</h3>
											<div class="meta">${comment.createdDate } at
												${comment.createdTime}</div>
											<p>${comment.content}</p>
											<!--  p><a href="#" class="reply">Reply</a></p-->
										</div>
									</li>
								</c:forEach>

							</ul>
							<!-- END comment-list -->
							<% if(!visibility) { %>
							<h2 style="color: red">This post is not publically available.</h2>
							<div class="comment-form-wrap pt-5">
								<h3 class="mb-5">Admin Feedback</h3>
								<h5 style="color: black"> <%= blog.getAdminFeedback() %> </h5>
							</div>
							<% } %>
							
							<% if(authenticated && visibility) { %>
							<div class="comment-form-wrap pt-5">
								<h3 class="mb-5">Leave a comment</h3>
								<form action="${pageContext.request.contextPath}/comment" method="post">

									<input type="hidden" value="<%= id %>" name="userId">
									<input type="hidden" value="<%= blog.getBlogId() %>" name="blogId">
									<div class="form-group">
										<label for="message">Comment *</label>
										<textarea name="content" id="message" cols="30" rows="10"
											class="form-control" required="required"></textarea>
									</div>
									<div class="form-group">
										<input type="submit" value="Post Comment"
											class="btn btn-primary btn-md text-white">
									</div>

								</form>
							</div>
							<% } %>
						</div>

					</div>

				</div>
			</div>
		</div>


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

	<script src="/blog/js/jquery-3.3.1.min.js"></script>
	<script src="/blog/js/jquery-migrate-3.0.0.js"></script>
	<script src="/blog/js/popper.min.js"></script>
	<script src="/blog/js/bootstrap.min.js"></script>
	<script src="/blog/js/owl.carousel.min.js"></script>
	<script src="/blog/js/jquery.sticky.js"></script>
	<script src="/blog/js/jquery.waypoints.min.js"></script>
	<script src="/blog/js/jquery.animateNumber.min.js"></script>
	<script src="/blog/js/jquery.fancybox.min.js"></script>
	<script src="/blog/js/jquery.stellar.min.js"></script>
	<script src="/blog/js/jquery.easing.1.3.js"></script>
	<script src="/blog/js/bootstrap-datepicker.min.js"></script>
	<script src="/blog/js/isotope.pkgd.min.js"></script>
	<script src="/blog/js/aos.js"></script>

	<script src="/blog/js/main.js"></script>

</body>

</html>

