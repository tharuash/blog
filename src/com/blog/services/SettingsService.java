package com.blog.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.entity.Settings;
import com.blog.utils.DbUtil;

public class SettingsService {

	private Connection connection;
	
	public Settings getSettings() {
		Settings settings = new Settings();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DbUtil.getConnection();
			String sql = "SELECT s.color, s.name, s.id FROM settings s";
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			rs.next();
			settings.setColor(rs.getString(1));
			settings.setName(rs.getString(2));
					
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

		return settings;
		
	}
	
	public long addSetting(Settings settings) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long updatedId = 0L;

		try {
			connection = DbUtil.getConnection();
			String sql = "UPDATE settings SET color=? , name=? WHERE id=? RETURNING id";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, settings.getColor() );
			pstmt.setString(2, settings.getName() );
			pstmt.setLong(3, 1L);

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
}
