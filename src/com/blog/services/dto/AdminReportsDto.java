package com.blog.services.dto;


import com.blog.entity.Blog;

public class AdminReportsDto {

	private Blog blog;
	private int reportCount;

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public int getReportCount() {
		return reportCount;
	}

	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}

}
