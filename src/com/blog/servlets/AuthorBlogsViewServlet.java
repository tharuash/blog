package com.blog.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.services.BlogService;
import com.blog.utils.CookieDecrypt;

public class AuthorBlogsViewServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private BlogService blogService;
	private CookieDecrypt cookieDecrypt;
	
	public AuthorBlogsViewServlet() {
		blogService = new BlogService();
		cookieDecrypt = new CookieDecrypt();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Optional<String> userId = cookieDecrypt.readCookie("id", req.getCookies());

		if (userId.isPresent()) {

			req.setAttribute("blogs", blogService.getUserBlogs(Long.parseLong(userId.get())));
			dispatcher = req.getRequestDispatcher("/author-blogs.jsp");

		} else {
			req.setAttribute("error", "Log In to view your bogs");
			dispatcher = req.getRequestDispatcher("/login.jsp");
		}
		
		dispatcher.forward(req, resp);
	}

}
