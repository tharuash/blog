package com.blog.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.entity.Settings;
import com.blog.services.SettingsService;
import com.google.gson.Gson;

public class SettingsServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private SettingsService settingsService;
	private Gson gson;
	
	public SettingsServlet() {
		this.settingsService = new SettingsService();
		this.gson = new Gson();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String settingsString = this.gson.toJson( settingsService.getSettings());
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(settingsString);
        out.flush();   
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Settings settings = new Settings();
		settings.setColor(req.getParameter("color"));
		settings.setName(req.getParameter("title"));
		
		if(settingsService.addSetting(settings) != 1) {
			req.setAttribute("Error", "Settings updating failed");
		}
		
		dispatcher = req.getRequestDispatcher("/web-settings.jsp");
		dispatcher.forward(req, resp);
		
	}

}
