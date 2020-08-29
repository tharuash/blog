package com.blog.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.entity.Blog;
import com.blog.entity.User;
import com.blog.services.BlogService;
import com.blog.utils.CookieDecrypt;

public class CreateBlogServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private BlogService blogService;
	private CookieDecrypt cookieDecrypt;
	
	public CreateBlogServlet() {
		blogService = new BlogService();
		cookieDecrypt = new CookieDecrypt();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dispatcher = req.getRequestDispatcher("/add-blog.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Blog blog = new Blog();
		User user = new User();
		blog.setUser(user);
		blog.setTitle(req.getParameter("title"));
		blog.setDescription(req.getParameter("description"));
		Optional<String> userId = cookieDecrypt.readCookie("id", req.getCookies());
		
		if(userId.isPresent()) {
			blog.getUser().setUserId(Long.parseLong(userId.get()));
			
			if(blogService.createBlog(blog) > 0) {
				req.setAttribute("blogs", blogService.getUserBlogs(Long.parseLong(userId.get())) );
				dispatcher = req.getRequestDispatcher("/author-blogs.jsp");
			}else {
				req.setAttribute("error", "Posting Unsuccessful, Please retry.");
				dispatcher = req.getRequestDispatcher("/add-blog.jsp");
			}
		}else {
			req.setAttribute("error", "Log In to create bogs");
			dispatcher = req.getRequestDispatcher("/login.jsp");
		}
		
		
		
		dispatcher.forward(req, resp);
		
	}

}
