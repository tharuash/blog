package com.blog.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.entity.Blog;
import com.blog.entity.Report;
import com.blog.entity.User;
import com.blog.services.BlogService;
import com.blog.services.ReportService;

public class AddReportServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private BlogService blogService;
	private ReportService reportService;
	
	public AddReportServlet() {
		blogService = new BlogService();
		reportService = new ReportService();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User commentingUser = new User();
		commentingUser.setUserId(Long.parseLong(req.getParameter("user_id")));
		Blog currentBlog = new Blog();
		currentBlog.setBlogId(Long.parseLong(req.getParameter("blog_id")));
		Report report = new Report();
		report.setDescription(req.getParameter("content"));
		report.setUser(commentingUser);
		report.setBlog(currentBlog);
		
		if( reportService.addReport(report) == 0) {
			req.setAttribute("error", "Reporting Failed. Please retry");
		}
		
		req.setAttribute("blogs", blogService.getAllBlogs());
		dispatcher = req.getRequestDispatcher("/author-home.jsp");
		dispatcher.forward(req, resp);
	}

}
