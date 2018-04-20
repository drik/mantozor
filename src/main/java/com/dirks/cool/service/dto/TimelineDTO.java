package com.dirks.cool.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Status entity.
 */
public class TimelineDTO implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 72699481245142401L;

	@NotNull
    private LocalDate timelineDate;
    
    private List<TimelineEventDTO> events ;

	public LocalDate getTimelineDate() {
		return timelineDate;
	}

	public void setTimelineDate(LocalDate timelineDate) {
		this.timelineDate = timelineDate;
	}

	public List<TimelineEventDTO> getEvents() {
		return events;
	}

	public void setEvents(List<TimelineEventDTO> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return "TimelineDTO [timelineDate=" + timelineDate + ", events=" + events + "]";
	}

	
   
}
