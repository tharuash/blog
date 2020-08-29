package com.blog.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.entity.User;
import com.blog.utils.DbUtil;

public class UserService {
	
	private Connection connection;

	public long updateAuthorProfile(User user) {

		PreparedStatement pstmt = null;
		long updatedId = 0L;

		try {
			connection = DbUtil.getConnection();
			String sql = "UPDATE users SET firstname=?, lastname=?, email=?, mobile=? WHERE id=?";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, user.getFirstname());
			pstmt.setString(2, user.getLastname());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getMobile());
			pstmt.setLong(5, user.getUserId());

			int rows = pstmt.executeUpdate();

			//rs.next();
			//updatedId = rs.getLong(1);
			updatedId = 1;

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				/*if (rs != null) {
					rs.close();
				}*/

				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return updatedId;

	}
	
	public User getUser(long id) {
		User user = new User();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DbUtil.getConnection();
			String sql = "SELECT u.id, u.firstname, u.lastname, u.email, u.mobile FROM users u WHERE u.id =?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();
			rs.next();
			user.setUserId( rs.getLong(1) );
			user.setFirstname( rs.getString(2) );
			user.setLastname( rs.getString(3) );
			user.setEmail(rs.getString(4));
			user.setMobile( rs.getString(5));
					
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

		return user;
		
	}
}
