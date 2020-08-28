package com.blog.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.entity.Comment;
import com.blog.services.BlogService;

public class ViewBlogServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private BlogService blogService;
	
	public ViewBlogServlet() {
		this.blogService = new BlogService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("occures");
		//System.out.println(req.getContextPath());
		req.setAttribute("blog", blogService.getBlog(Long.parseLong(req.getParameter("id"))));
		dispatcher = req.getRequestDispatcher("/blog.jsp");
		dispatcher.forward(req, resp);
	}
}
