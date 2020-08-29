package com.blog.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.services.ReportService;

public class AdminHomeServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private ReportService reportService;
	
	public AdminHomeServlet() {
		this.reportService = new ReportService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("summaries", reportService.getAdminReportedBlogs());
		dispatcher = req.getRequestDispatcher("/admin-home.jsp");
		dispatcher.forward(req, resp);
	}

}
