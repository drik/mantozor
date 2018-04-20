package com.dirks.cool.service.dto;


import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the StatusStats.
 */
public class StatusStateStatsDTO implements Serializable {
   
    private StateDTO state;
    
    private List<StatusStatsDTO> statusesStats;
    
    private Long count;
    
    private double percentage;
	
	public StateDTO getState() {
		return state;
	}

	public void setState(StateDTO state) {
		this.state = state;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public List<StatusStatsDTO> getStatusesStats() {
		return statusesStats;
	}

	public void setStatusesStats(List<StatusStatsDTO> statusesStats) {
		this.statusesStats = statusesStats;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		long temp;
		temp = Double.doubleToLongBits(percentage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((statusesStats == null) ? 0 : statusesStats.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatusStateStatsDTO other = (StatusStateStatsDTO) obj;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (Double.doubleToLongBits(percentage) != Double.doubleToLongBits(other.percentage))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (statusesStats == null) {
			if (other.statusesStats != null)
				return false;
		} else if (!statusesStats.equals(other.statusesStats))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatusStateStatsDTO [state=" + state + ", statusesStats=" + statusesStats + ", count=" + count
				+ ", percentage=" + percentage + "]";
	}

    
}
