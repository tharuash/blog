package com.blog.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.entity.User;
import com.blog.services.dto.LoginDto;
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
	
	public LoginDto createUser(User user) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		LoginDto loginDto = new LoginDto();
		
		try {
			connection = DbUtil.getConnection();
			String sql = "SELECT 1 FROM users u WHERE u.email = ?";

			pstmt = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, user.getEmail());
			
			rs = pstmt.executeQuery();
			
			
			if(rs.next() && rs.getInt(1) > 0) {
				loginDto.setAuthorized(false);
				loginDto.setError("Given email is already registered");
				
				rs.close();
				pstmt.close();
				
			} else {
				
				rs.close();
				pstmt.close();
				
				String sql1 = "INSERT INTO users( firstname, lastname, email, password, mobile, role) VALUES (?,?,?,?,?,?) RETURNING id";
				pstmt = connection.prepareStatement(sql1,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				pstmt.setString(1, user.getFirstname());
				pstmt.setString(2, user.getLastname());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getPassword());
				pstmt.setString(5, user.getMobile());
				pstmt.setString(6, "author");
				
				rs = pstmt.executeQuery();
				rs.next();
				
				if( rs.getLong(1) > 0) {
					User signedUser = new User();
					signedUser.setUserId(rs.getLong(1));
					
					loginDto.setUser(signedUser);
					loginDto.setAuthorized(true);
					
				}else {
					loginDto.setAuthorized(false);
					loginDto.setError("Error in creating user. Please retry");
					
				}
			}
			
		} catch (Exception e) {
			System.out.println("Error: " + e);
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return loginDto;

	}
}
