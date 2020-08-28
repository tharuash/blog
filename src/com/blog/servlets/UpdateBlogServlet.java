package com.blog.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.entity.Blog;
import com.blog.services.BlogService;

public class UpdateBlogServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private BlogService blogService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long blogId = Long.parseLong(req.getParameter("id"));
		req.setAttribute("current_blog", blogService.getBlog(blogId));
		dispatcher = req.getRequestDispatcher("/updateww_blog.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Blog blog = new Blog();
		blog.setTitle(req.getParameter("title"));
		blog.setDescription(req.getParameter("description"));
		blog.getUser().setUserId(Long.parseLong(req.getParameter("user_id")));
		
		if(blogService.updateBlogByAuthor(blog) > 0) {
			req.setAttribute("blog", blogService.getBlog(blog.getBlogId()));
			dispatcher = req.getRequestDispatcher("/author_blogs");
		}else {
			req.setAttribute("error", "Posting Unsuccessful, Please retry.");
			dispatcher = req.getRequestDispatcher("/blog.jsp");
		}
		
		dispatcher.forward(req, resp);
		
	}
}
