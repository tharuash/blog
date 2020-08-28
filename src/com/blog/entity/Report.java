package com.blog.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Report {

	private long reportId;
	private String description;
	private Blog blog;
	private User user;
	private LocalDate createdDate;
	private LocalTime createdTime;

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalTime createdTime) {
		this.createdTime = createdTime;
	}

}
