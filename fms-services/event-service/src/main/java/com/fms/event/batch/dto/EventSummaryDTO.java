/**
 * 
 */
package com.fms.event.batch.dto;

import lombok.Data;

/**
 * @author Kesavalu
 *
 */
@Data
public class EventSummaryDTO {
	
	private String eventRefId;
	private String baseLoc;
	private String month;
	private String beneficiaryName;
	private String venueAddress;
	private String councilName;
	private String project;
	private String eventName;
	private String category;
	private String eventDesc;
	private String eventDate;
	private String totalVolunteers;
	private String totalVolunteerHours;
	private String totalTravelHours;
	private String overAllVolunteerHours;
	private String livesImpacted;
	private String activityType;
	private String status;
	private String pocId;
	private String pocName;
	private String pocContactNumber;

}
