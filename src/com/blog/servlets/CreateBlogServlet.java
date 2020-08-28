package com.blog.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.entity.Blog;
import com.blog.services.BlogService;

public class CreateBlogServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private BlogService blogService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dispatcher = req.getRequestDispatcher("/new_blog.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Blog blog = new Blog();
		blog.setTitle(req.getParameter("title"));
		blog.setDescription(req.getParameter("description"));
		blog.getUser().setUserId(Long.parseLong(req.getParameter("user_id")));
		
		if(blogService.createBlog(blog) > 0) {
			req.setAttribute("blogs", blogService.getUserBlogs(Long.parseLong(req.getParameter("user_id"))));
			dispatcher = req.getRequestDispatcher("/author_blogs");
		}else {
			req.setAttribute("error", "Posting Unsuccessful, Please retry.");
			dispatcher = req.getRequestDispatcher("/new_blog.jsp");
		}
		
		dispatcher.forward(req, resp);
		
	}

}
