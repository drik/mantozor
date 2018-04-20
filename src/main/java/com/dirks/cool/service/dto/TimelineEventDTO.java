package com.dirks.cool.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Status entity.
 */
public class TimelineEventDTO implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 72689281245142401L;

	@NotNull
    private LocalDate eventDate;
    
    private UserDTO user;

    private String title;
	
	private String type;
	
	private String details;

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "TimelineEventDTO [eventDate=" + eventDate + ", user=" + user + ", title=" + title + ", type=" + type
				+ ", details=" + details + "]";
	}

   
}
