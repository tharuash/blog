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
	
	public UpdateBlogServlet() {
		blogService = new BlogService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long blogId = Long.parseLong(req.getParameter("id"));
		req.setAttribute("blog", blogService.getBlog(blogId));
		dispatcher = req.getRequestDispatcher("/update-blog.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Blog blog = new Blog();
		blog.setBlogId(Long.parseLong(req.getParameter("blog_id")));
		blog.setTitle(req.getParameter("title"));
		blog.setDescription(req.getParameter("description"));
		
		if(blogService.updateBlogByAuthor(blog) > 0) {
			req.setAttribute("blog", blogService.getBlog(blog.getBlogId()));
			dispatcher = req.getRequestDispatcher("/blog.jsp");
		}else {
			req.setAttribute("error", "Posting Unsuccessful, Please retry.");
			req.setAttribute("blog", blogService.getBlog(blog.getBlogId()));
			dispatcher = req.getRequestDispatcher("/update-blog.jsp");
		}
		
		dispatcher.forward(req, resp);
		
	}
}
