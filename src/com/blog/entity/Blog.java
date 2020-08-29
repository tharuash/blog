package com.blog.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Blog {

	private long blogId;
	private User user;
	private String title;
	private String description;
	private LocalDate createdDate;
	private LocalTime createdTime;
	private boolean visibility;
	private List<Comment> comments;
	private List<Report> reports;
	private String adminFeedback;
	
	public Blog() {
		
	}

	public Blog(long blogId, User user, String title, String description, LocalDate createdDate, LocalTime createdTime,
			boolean visibility, List<Comment> comments) {
		super();
		this.blogId = blogId;
		this.user = user;
		this.title = title;
		this.description = description;
		this.createdDate = createdDate;
		this.createdTime = createdTime;
		this.visibility = visibility;
		this.comments = comments;
	}

	public long getBlogId() {
		return blogId;
	}

	public void setBlogId(long blogId) {
		this.blogId = blogId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	
	public List<Report> getReports() {
		return reports;
	}
	
	public void setAdminFeedback(String adminFeedback) {
		this.adminFeedback = adminFeedback;
	}
	
	public String getAdminFeedback() {
		return adminFeedback;
	}

}
