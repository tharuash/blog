package com.blog.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.entity.Blog;
import com.blog.services.BlogService;
import com.blog.services.ReportService;

public class AdminBlogReportServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private BlogService blogService;
	private ReportService reportService;
	
	public AdminBlogReportServlet() {
		this.blogService = new BlogService();
		this.reportService = new ReportService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("blog", blogService.getBlog(Long.parseLong(req.getParameter("id"))));
		dispatcher = req.getRequestDispatcher("/admin-blog.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Blog blog = new Blog();
		blog.setBlogId(Long.parseLong(req.getParameter("blog_id")));
		blog.setAdminFeedback(req.getParameter("feedback"));
		
		if(blogService.setAdminFeedbackForReportedBlog(blog) != 0 ) {
			req.setAttribute("summaries", reportService.getAdminReportedBlogs());
			dispatcher = req.getRequestDispatcher("/admin-home.jsp");
		}else {
			req.setAttribute("blog", blogService.getBlog(blog.getBlogId()));
			dispatcher = req.getRequestDispatcher("/admin-blog.jsp");
		}
		
		dispatcher.forward(req, resp);
	}

}
