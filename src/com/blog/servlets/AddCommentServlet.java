package com.blog.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.entity.User;
import com.blog.services.BlogService;
import com.blog.services.CommentService;

public class AddCommentServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;
	private BlogService blogService;
	private CommentService commentService;
	
	public AddCommentServlet() {
		blogService = new BlogService();
		commentService = new CommentService();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User commentingUser = new User();
		commentingUser.setUserId(Long.parseLong(req.getParameter("userId")));
		Blog currentBlog = new Blog();
		currentBlog.setBlogId(Long.parseLong(req.getParameter("blogId")));
		Comment comment = new Comment();
		comment.setContent(req.getParameter("content"));
		comment.setUser(commentingUser);
		comment.setBlog(currentBlog);
		
		if( commentService.addComment(comment) == 0) {
			req.setAttribute("error", "Commenting Failed. Please retry");
		}
		
		req.setAttribute("blog", blogService.getBlog(currentBlog.getBlogId()));
		dispatcher = req.getRequestDispatcher("/blog.jsp");
		dispatcher.forward(req, resp);
	}
	
	
	
}
