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
import com.blog.entity.Report;
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
				user.setUserId(rs.getLong(1));
				user.setFirstname(rs.getString(2));
				user.setLastname(rs.getString(3));
				Blog blog = new Blog();
				blog.setBlogId(rs.getLong(4));
				blog.setTitle(rs.getString(5));
				blog.setDescription(rs.getString(6));
				blog.setCreatedDate(rs.getDate(7).toLocalDate());
				blog.setCreatedTime(rs.getTime(8).toLocalTime());
				blog.setUser(user);
				blogs.add(blog);
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
			String sql = "SELECT u.id, u.firstname, u.lastname, b.id, b.title, b.description, b.created_date, b.created_time, b.admin_feedback FROM blogs b INNER JOIN users u ON b.user_id = u.id WHERE b.id =?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();
			rs.next();
			User user = new User();
			user.setUserId(rs.getLong(1));
			user.setFirstname(rs.getString(2));
			user.setLastname(rs.getString(3));
			blog.setBlogId(rs.getLong(4));
			blog.setTitle(rs.getString(5));
			blog.setDescription(rs.getString(6));
			blog.setCreatedDate(rs.getDate(7).toLocalDate());
			blog.setCreatedTime(rs.getTime(8).toLocalTime());
			blog.setAdminFeedback(rs.getString(9));
			blog.setUser(user);

			pstmt.close();
			rs.close();

			String sql1 = "SELECT u.id, u.firstname, u.lastname, c.id, c.content, c.created_date, c.created_time FROM comments c INNER JOIN users u ON c.user_id = u.id WHERE c.blog_id =?";
			pstmt = connection.prepareStatement(sql1);
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();

			List<Comment> blogComments = new ArrayList<Comment>();

			while (rs.next()) {
				User commentedUser = new User();
				commentedUser.setUserId(rs.getLong(1));
				commentedUser.setFirstname(rs.getString(2));
				commentedUser.setLastname(rs.getString(3));
				Comment comment = new Comment();
				comment.setCommentId(rs.getLong(4));
				comment.setContent(rs.getString(5));
				comment.setCreatedDate(rs.getDate(6).toLocalDate());
				comment.setCreatedTime(rs.getTime(7).toLocalTime());
				comment.setUser(commentedUser);
				blogComments.add(comment);

			}

			blog.setComments(blogComments);

			pstmt.close();
			rs.close();

			String sql2 = "SELECT u.id, u.firstname, u.lastname, r.id, r.content, r.action FROM reports r INNER JOIN users u ON r.user_id = u.id WHERE r.blog_id =?";
			pstmt = connection.prepareStatement(sql2);
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();

			List<Report> blogReports = new ArrayList<Report>();

			while (rs.next()) {
				User reportedUser = new User();
				reportedUser.setUserId(rs.getLong(1));
				reportedUser.setFirstname(rs.getString(2));
				reportedUser.setLastname(rs.getString(3));
				Report report = new Report();
				report.setReportId(rs.getLong(4));
				report.setDescription(rs.getString(5));
				report.setAction(rs.getString(6));
				report.setUser(reportedUser);
				blogReports.add(report);
			}

			blog.setReports(blogReports);

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
			savedId = rs.getLong(1);

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
				blog.setBlogId(rs.getLong(1));
				blog.setTitle(rs.getString(2));
				blog.setDescription(rs.getString(3));
				blog.setCreatedDate(rs.getDate(4).toLocalDate());
				blog.setCreatedTime(rs.getTime(5).toLocalTime());
				blog.setVisibility(rs.getBoolean(6));
				blogs.add(blog);
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
			String sql = "UPDATE blogs SET title = ?, description = ? WHERE id = ? RETURNING id";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, blog.getTitle());
			pstmt.setString(2, blog.getDescription());
			pstmt.setLong(3, blog.getBlogId());
			// pstmt.setDate(4, Date.valueOf(LocalDate.now()));
			// pstmt.setTime(5, Time.valueOf(LocalTime.now()));
			// pstmt.setBoolean(6, false);

			rs = pstmt.executeQuery();
			rs.next();
			updatedId = rs.getLong(1);

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

	public long deleteBlogByAuthor(long blogId) {

		PreparedStatement pstmt = null;

		try {

			connection = DbUtil.getConnection();
			String sql = "DELETE FROM comments WHERE blog_id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, blogId);

			System.out.println("Inside");
			pstmt.executeUpdate();

			pstmt.close();

			String sql1 = "DELETE FROM reports WHERE blog_id = ?";
			pstmt = connection.prepareStatement(sql1);
			pstmt.setLong(1, blogId);

			pstmt.executeUpdate();
			// rs.next();

			pstmt.close();
			// rs.close();

			String sql2 = "DELETE FROM blogs WHERE id = ?";
			pstmt = connection.prepareStatement(sql2);
			pstmt.setLong(1, blogId);

			pstmt.executeUpdate();
			// rs.next();

			return blogId;

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				/*
				 * if (rs != null) { rs.close(); }
				 */

				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return 0;

	}

	public long setAdminFeedbackForReportedBlog(Blog blog) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long updatedId = 0L;

		try {
			connection = DbUtil.getConnection();
			String sql = "UPDATE blogs SET admin_feedback = ?, visibility = ? WHERE id = ? RETURNING id";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, blog.getAdminFeedback());
			pstmt.setBoolean(2, false);
			pstmt.setLong(3, blog.getBlogId());

			rs = pstmt.executeQuery();
			rs.next();
			updatedId = rs.getLong(1);
			
			rs.close();
			pstmt.close();
			
			String sql1 = "UPDATE reports SET closed = ? WHERE blog_id = ?";
			pstmt = connection.prepareStatement(sql1);

			pstmt.setBoolean(1, true);
			pstmt.setLong(2, blog.getBlogId());

			pstmt.executeUpdate();
			
			
			

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
	
	public List<Blog> searchBlogs(String query) {
		List<Blog> blogs = new ArrayList<Blog>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			connection = DbUtil.getConnection();
			String sql = "SELECT u.id, u.firstname, u.lastname, b.id, b.title, b.description, b.created_date, b.created_time FROM blogs b INNER JOIN users u ON b.user_id = u.id WHERE b.title LIKE ? AND b.visibility != 'false'";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, '%'+query+'%');
			rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getLong(1));
				user.setFirstname(rs.getString(2));
				user.setLastname(rs.getString(3));
				Blog blog = new Blog();
				blog.setBlogId(rs.getLong(4));
				blog.setTitle(rs.getString(5));
				blog.setDescription(rs.getString(6));
				blog.setCreatedDate(rs.getDate(7).toLocalDate());
				blog.setCreatedTime(rs.getTime(8).toLocalTime());
				blog.setUser(user);
				blogs.add(blog);
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

}
