/**
 * 
 */
package com.fms.event.bean;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @author Kesavalu
 *
 */
@Data
@Builder
public class EventRequest {

	private Long eventId;
	private String eventRefId;
	private String eventName;
	private String eventDesc;
	private Date eventDate;
	private String project;
	private String baseLocation;
	private String beneficiary;
	private String council;
	private String status;
	private String category;
	private String venueAddress;
	private String iiepCategory;
	private int totalVolunteers;
	private int totalVolunteerHours;
	private int totalTravelHours;
	private int liveImpacted;
	private int activityType;
	
		
}
