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
import com.blog.services.AuthService;
import com.blog.services.BlogService;
import com.blog.services.dto.LoginDto;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private AuthService authService;
	private BlogService blogService;

	public LoginServlet() {
		this.authService = new AuthService();
		this.blogService = new BlogService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dispatcher = req.getRequestDispatcher("/login.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginDto loginDto = authService.validateUser(req.getParameter("email"), req.getParameter("password"));
		req.setAttribute("auth", loginDto);
		
		if(loginDto.isAuthorized()) {
			List<Blog> userBlogs = blogService.getUserBlogs(loginDto.getUser().getUserId());
			req.setAttribute("blogs", userBlogs);
			Cookie ck = new Cookie("id", String.valueOf(loginDto.getUser().getUserId()));
			resp.addCookie(ck);
			dispatcher = req.getRequestDispatcher("/author-home.jsp");
		}
		
		dispatcher.forward(req, resp);
	}

}
