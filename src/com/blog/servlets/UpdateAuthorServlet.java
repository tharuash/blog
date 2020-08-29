package com.blog.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.entity.User;
import com.blog.services.BlogService;
import com.blog.services.UserService;
import com.blog.utils.CookieDecrypt;

public class UpdateAuthorServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private UserService userService;
	private CookieDecrypt cookieDecrypt;
	private BlogService blogService;
	
	public UpdateAuthorServlet() {
		userService = new UserService();
		cookieDecrypt = new CookieDecrypt();
		blogService = new BlogService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Optional<String> userId = cookieDecrypt.readCookie("id", req.getCookies());
		req.setAttribute("user", userService.getUser(Long.parseLong(userId.get())));
		dispatcher = req.getRequestDispatcher("/update-author.jsp");
		
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		Optional<String> userId = cookieDecrypt.readCookie("id", req.getCookies());

		if (userId.isPresent()) {

			User updatingUser = new User();
			updatingUser.setUserId(Long.parseLong(userId.get()));
			updatingUser.setFirstname(req.getParameter("firstname"));
			updatingUser.setLastname(req.getParameter("lastname"));
			updatingUser.setEmail(req.getParameter("email"));
			updatingUser.setMobile(req.getParameter("mobile"));
			
			//System.out.println(updatingUser.getFirstname() + " " + updatingUser.getLastname() + " " +updatingUser.getEmail() + " " +updatingUser.getMobile());
			
			if(userService.updateAuthorProfile(updatingUser) == 0) {
				req.setAttribute("error", "Profile Updating Failed. Please retry");
				req.setAttribute("user", userService.getUser(Long.parseLong(userId.get())));
				dispatcher = req.getRequestDispatcher("/update-author.jsp");
			} else {
				req.setAttribute("blogs", blogService.getAllBlogs());
				dispatcher = req.getRequestDispatcher("/author-home.jsp");
			}

		} else {
			req.setAttribute("error", "Log In to view your bogs");
			dispatcher = req.getRequestDispatcher("/login.jsp");
		}
		
		dispatcher.forward(req, resp);
		
		
	}

}
