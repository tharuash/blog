package com.blog.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.entity.User;
import com.blog.services.dto.LoginDto;
import com.blog.utils.DbUtil;


public class AuthService {
	
	private Connection connection;
	
	public LoginDto validateUser(String email, String password) {
		LoginDto loginDto = new LoginDto();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = DbUtil.getConnection();
			String sql = "SELECT 1 FROM users u WHERE u.email = ?";

			pstmt = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			rs.next();
			
			if( rs.getInt(1) > 0) {
				rs.close();
				pstmt.close();
				
				String sql1 = "SELECT * FROM users u WHERE u.email = ? AND u.password = ?";
				pstmt = connection.prepareStatement(sql1,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				pstmt.setString(1, email);
				pstmt.setString(2, password);
				
				rs = pstmt.executeQuery();
				rs.next();
				
				if( rs.getLong(1) > 0) {
					User signedUser = new User();
					signedUser.setUserId(rs.getLong(1));
					signedUser.setRole(rs.getString(7));
					
					loginDto.setUser(signedUser);
					loginDto.setAuthorized(true);
					
				}else {
					
					loginDto.setAuthorized(false);
					loginDto.setError("Given password is incorrect");
				}
				
				
				
			}else {
				
				loginDto.setAuthorized(false);
				loginDto.setError("Not a registered email.");
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
