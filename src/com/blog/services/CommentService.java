package com.blog.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import com.blog.entity.Comment;
import com.blog.utils.DbUtil;

public class CommentService {
	
	private Connection connection;
	
	public long addComment(Comment comment) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long savedId = 0L;

		try {
			connection = DbUtil.getConnection();
			String sql = "INSERT INTO comments( content, created_date, created_time, visibility, user_id, blog_id) VALUES (?,?,?,?,?,?) RETURNING id";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, comment.getContent() );
			pstmt.setDate(2, Date.valueOf(LocalDate.now()));
			pstmt.setTime(3, Time.valueOf(LocalTime.now()));
			pstmt.setBoolean(4, true);
			pstmt.setLong(5, comment.getUser().getUserId());
			pstmt.setLong(6, comment.getBlog().getBlogId());

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
}
