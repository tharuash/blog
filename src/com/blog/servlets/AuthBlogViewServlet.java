package com.blog.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.services.BlogService;

public class AuthBlogViewServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private BlogService blogService;
	
	public AuthBlogViewServlet() {
		this.blogService = new BlogService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("blogs", blogService.getAllBlogs());
		dispatcher = req.getRequestDispatcher("/author-home.jsp");
		dispatcher.forward(req, resp);
	}	

}
