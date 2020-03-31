/**
 * 
 */
package com.fms.event.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;
import com.fms.event.jackson.View;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Kesavalu
 *
 */
@Data
@Entity
@Table(name = "t_events")
@EqualsAndHashCode(exclude = "participants",callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Event extends Audit {

	public Event(String eventRefId) {
		super();
		this.eventRefId=eventRefId;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "event_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.Summary.class)
	private Long eventId;
	@Column(name = "event_reference_id",unique = true)
	@JsonView(View.Summary.class)
	private String eventRefId;
	@Size(max = 100)
	//@NotNull
	@Column(name = "event_name")
	@JsonView(View.Summary.class)
	private String eventName;
	@Size(max = 500)
	@Column(name = "eventDescription")
	@JsonView(View.Summary.class)
	private String eventDesc;
	@Column(name = "event_date")
	@JsonView(View.Summary.class)
	private Date eventDate;
	
	@Column(name = "project")
	@JsonView(View.Summary.class)
	private String project;
	@Column(name = "base_location")
	@JsonView(View.Summary.class)
	private String baseLocation;
	@Column(name = "beneficiary")
	@JsonView(View.Summary.class)
	private String beneficiary;
	@Column(name = "council")
	@JsonView(View.Summary.class)
	private String council;
	@Column(name = "status")
	@JsonView(View.Summary.class)
	private String status;
	@Column(name = "category")
	@JsonView(View.SummaryWithDetail.class)
	private String category;
	@Size(max = 500)
	@Column(name = "venue_address")
	@JsonView(View.Summary.class)
	private String venueAddress;
	@Column(name = "IIEP_category")
	@JsonView(View.SummaryWithDetail.class)
	private String iiepCategory;
	@Column(name = "total_volunteers")
	@JsonView(View.Summary.class)
	private int totalVolunteers;
	@Column(name = "total_volunteer_hours")
	@JsonView(View.Summary.class)
	private int totalVolunteerHours;
	@Column(name = "total_travel_hours")
	@JsonView(View.Summary.class)
	private int totalTravelHours;
	@Column(name = "overall_volunteer_hours")
	@JsonView(View.Summary.class)
	private int overAllVolunteerHours;
	@Column(name = "lives_impacted")
	@JsonView(View.Summary.class)
	private int liveImpacted;
	@Column(name = "activityType")
	@JsonView(View.SummaryWithDetail.class)
	private int activityType;
	
	@JsonView(View.SummaryWithDetail.class)
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "event_participant_id")
	@ToString.Exclude
	private Set<EventParticipant> participants;
	
	@JsonView(View.SummaryWithDetail.class)
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "eventPOCId")
	@ToString.Exclude
	private Set<EventPOC> eventPOCs;

}
