package com.pony.model;

import java.io.Serializable;

public class ReviewModel implements Serializable{

	private String reviewId;
	private String reviewUserId;
	private String reviewTime;
	private String reviewMessage;
	private String reviewMessageId;
	private String reviewUserName;

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(String reviewUserId) {
		this.reviewUserId = reviewUserId;
	}

	public String getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getReviewMessage() {
		return reviewMessage;
	}

	public void setReviewMessage(String reviewMessage) {
		this.reviewMessage = reviewMessage;
	}

	public String getReviewMessageId() {
		return reviewMessageId;
	}

	public void setReviewMessageId(String reviewMessageId) {
		this.reviewMessageId = reviewMessageId;
	}

	public String getReviewUserName() {
		return reviewUserName;
	}

	public void setReviewUserName(String reviewUserName) {
		this.reviewUserName = reviewUserName;
	}

}
