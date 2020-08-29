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
import com.blog.entity.Report;
import com.blog.entity.User;
import com.blog.services.dto.AdminReportsDto;
import com.blog.utils.DbUtil;

public class ReportService {

	private Connection connection;

	public long addReport(Report report) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long savedId = 0L;

		try {
			connection = DbUtil.getConnection();
			String sql = "INSERT INTO reports( content, created_date, created_time, action, closed, user_id, blog_id) VALUES (?,?,?,?,?,?,?) RETURNING id";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, report.getDescription());
			pstmt.setDate(2, Date.valueOf(LocalDate.now()));
			pstmt.setTime(3, Time.valueOf(LocalTime.now()));
			pstmt.setString(4, "Pending Review");
			pstmt.setBoolean(5, false);
			pstmt.setLong(6, report.getUser().getUserId());
			pstmt.setLong(7, report.getBlog().getBlogId());

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
	
	public List<AdminReportsDto> getAdminReportedBlogs() {
		List<AdminReportsDto> adminReportsDtos = new ArrayList<AdminReportsDto>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DbUtil.getConnection();
			String sql = "SELECT fb.id, fb.title, fb.description, fb.count, u.id, u.firstname, u.lastname, u.email,\n" + 
					"u.mobile FROM (SELECT b.id, b.title, b.description, count(r.id), b.user_id FROM blogs b\n" + 
					"INNER JOIN reports r ON b.id = r.blog_id WHERE r.closed = 'false' AND b.visibility= 'true' GROUP BY b.id \n" + 
					"ORDER BY count(r.id) DESC) fb INNER JOIN users u ON fb.user_id = u.id;";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Blog blog = new Blog();
				blog.setBlogId( rs.getLong(1) );
				blog.setTitle( rs.getString(2) );
				blog.setDescription( rs.getString(3) );
				int reportCount = rs.getInt(4);
				User blogOwner = new User();
				blogOwner.setUserId( rs.getLong(5) );
				blogOwner.setFirstname( rs.getString(6) );
				blogOwner.setLastname( rs.getString(7) );
				blogOwner.setEmail( rs.getString(8) );
				blogOwner.setMobile( rs.getString(9) );
				blog.setUser(blogOwner);
				AdminReportsDto adminReportsDto = new AdminReportsDto();
				adminReportsDto.setBlog(blog);
				adminReportsDto.setReportCount(reportCount);
				
				adminReportsDtos.add(adminReportsDto);
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

		return adminReportsDtos;
		
	}
}
