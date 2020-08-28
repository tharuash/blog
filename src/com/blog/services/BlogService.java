package com.blog.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.entity.User;
import com.blog.utils.DbUtil;

public class BlogService {
	
	private Connection connection;
	
	public List<Blog> getAllBlogs() {
		List<Blog> blogs = new ArrayList<Blog>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			connection = DbUtil.getConnection();
			String sql = "SELECT u.id, u.firstname, u.lastname, b.id, b.title, b.description, b.created_date, b.created_time FROM blogs b INNER JOIN users u ON b.user_id = u.id WHERE b.visibility != 'false'";
			pstmt = connection.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				User user = new User();
				user.setUserId( rs.getLong(1) );
				user.setFirstname( rs.getString(2) );
				user.setLastname( rs.getString(3) );
				Blog blog = new Blog();
				blog.setBlogId( rs.getLong(4) );
				blog.setTitle( rs.getString(5) );
				blog.setDescription( rs.getString(6) );
				blog.setCreatedDate( rs.getDate(7).toLocalDate() );
				blog.setCreatedTime( rs.getTime(8).toLocalTime() );
				blog.setUser( user );
				blogs.add( blog );
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				
				e.printStackTrace();
			}

		}

		return blogs;
		
	}
	
	public Blog getBlog(long id) {
		Blog blog = new Blog();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DbUtil.getConnection();
			String sql = "SELECT u.id, u.firstname, u.lastname, b.id, b.title, b.description, b.created_date, b.created_time FROM blogs b INNER JOIN users u ON b.user_id = u.id WHERE b.id =?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();
			rs.next();
			User user = new User();
			user.setUserId( rs.getLong(1) );
			user.setFirstname( rs.getString(2) );
			user.setLastname( rs.getString(3) );
			blog.setBlogId( rs.getLong(4) );
			blog.setTitle( rs.getString(5) );
			blog.setDescription( rs.getString(6) );
			blog.setCreatedDate( rs.getDate(7).toLocalDate() );
			blog.setCreatedTime( rs.getTime(8).toLocalTime() );
			blog.setUser( user );
			
			pstmt.close();
			rs.close();
			
			String sql1 = "SELECT u.id, u.firstname, u.lastname, c.id, c.content, c.created_date, c.created_time FROM comments c INNER JOIN users u ON c.user_id = u.id WHERE c.blog_id =?";
			pstmt = connection.prepareStatement(sql1);
			pstmt.setLong(1, id);
			
			rs = pstmt.executeQuery();
			
			List<Comment> blogComments = new ArrayList<Comment>();
			
			while (rs.next()) {
				User commentedUser = new User();
				commentedUser.setUserId( rs.getLong(1) );
				commentedUser.setFirstname( rs.getString(2) );
				commentedUser.setLastname( rs.getString(3) );
				Comment comment = new Comment();
				comment.setCommentId( rs.getLong(4) );
				comment.setContent( rs.getString(5) );
				comment.setCreatedDate( rs.getDate(6).toLocalDate() );
				comment.setCreatedTime( rs.getTime(7).toLocalTime() );
				comment.setUser( commentedUser );
				blogComments.add( comment );
				
			}
			
			blog.setComments(blogComments);
			
			
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				
				e.printStackTrace();
			}

		}

		return blog;
		
	}
	
	public long createBlog(Blog blog) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long savedId = 0L;
		
		try {
			connection = DbUtil.getConnection();
			String sql = "INSERT INTO blogs( user_id, title, description, created_date, created_time, visibility) VALUES (?,?,?,?,?,?) RETURNING id";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setLong(1, blog.getUser().getUserId());
			pstmt.setString(2, blog.getTitle());
			pstmt.setString(3, blog.getDescription());
			pstmt.setDate(4, Date.valueOf(LocalDate.now()));
			pstmt.setTime(5, Time.valueOf(LocalTime.now()));
			pstmt.setBoolean(6, false);

			rs = pstmt.executeQuery();
			rs.next();
			savedId = rs.getLong(0);
		 
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				
				e.printStackTrace();
			}

		}
		
		return savedId;

	}
	
	public List<Blog> getUserBlogs(long id) {
		List<Blog> blogs = new ArrayList<Blog>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DbUtil.getConnection();
			String sql = "SELECT b.id, b.title, b.description, b.created_date, b.created_time, b.visibility FROM blogs b WHERE b.user_id=?";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Blog blog = new Blog();
				blog.setBlogId( rs.getLong(1) );
				blog.setTitle( rs.getString(2) );
				blog.setDescription( rs.getString(3) );
				blog.setCreatedDate( rs.getDate(4).toLocalDate() );
				blog.setCreatedTime( rs.getTime(5).toLocalTime() );
				blog.setVisibility( rs.getBoolean(6) );
				blogs.add( blog );
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				
				e.printStackTrace();
			}

		}

		return blogs;
		
	}
	
	public long updateBlogByAuthor(Blog blog) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long updatedId = 0L;
		
		try {
			connection = DbUtil.getConnection();
			String sql = "UPDATE blogs SET title = ?, description = ?) WHERE id = ? RETURNING id";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, blog.getTitle());
			pstmt.setString(2, blog.getDescription());
			pstmt.setLong(3, blog.getBlogId());
			//pstmt.setDate(4, Date.valueOf(LocalDate.now()));
			//pstmt.setTime(5, Time.valueOf(LocalTime.now()));
			//pstmt.setBoolean(6, false);

			rs = pstmt.executeQuery();
			rs.next();
			updatedId = rs.getLong(0);
		 
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				
				e.printStackTrace();
			}

		}
		
		return updatedId;

	}
	
}
