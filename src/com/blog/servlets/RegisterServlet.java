package com.blog.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.entity.Blog;
import com.blog.entity.User;
import com.blog.services.BlogService;
import com.blog.services.UserService;
import com.blog.services.dto.LoginDto;

public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private BlogService blogService;
	private UserService userService;

	public RegisterServlet() {
		blogService = new BlogService();
		userService = new UserService();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dispatcher = req.getRequestDispatcher("/register.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User newUser = new User();
		newUser.setFirstname(req.getParameter("firstname"));
		newUser.setLastname(req.getParameter("lastname"));
		newUser.setEmail(req.getParameter("email"));
		newUser.setPassword(req.getParameter("password"));
		newUser.setMobile(req.getParameter("mobile"));
		LoginDto loginDto = userService.createUser(newUser);
		req.setAttribute("auth", loginDto);

		if (loginDto.isAuthorized()) {

			Cookie ck = new Cookie("id", String.valueOf(loginDto.getUser().getUserId()));
			resp.addCookie(ck);

			List<Blog> blogs = blogService.getAllBlogs();
			req.setAttribute("blogs", blogs);
			dispatcher = req.getRequestDispatcher("/author-home.jsp");
		} else {
			req.setAttribute("auth", loginDto);
			dispatcher = req.getRequestDispatcher("/register.jsp");
		}

		dispatcher.forward(req, resp);
	}

}
