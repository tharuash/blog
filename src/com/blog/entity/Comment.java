package com.blog.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Comment {

	private long commentId;
	private User user;
	private Blog blog;
	private LocalDate createdDate;
	private LocalTime createdTime;
	private boolean visibility;
	private String content;

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
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
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

}
