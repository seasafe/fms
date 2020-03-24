/**
 * 
 */
package com.fms.event.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Kesavalu
 *
 */
@Data
@Entity
@Table(name = "t_event_participants")
@EqualsAndHashCode(exclude = {"event"},callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EventParticipant extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "event_participant_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventParticipantId;
	@Column(name = "employeeId")
	private Long employeeId;
	@Column(name = "employee_name")
	private String employeeName;
	@Column(name = "volunteer_hours")
	private int volunteerHours;
	@Column(name = "travel_hours")
	private int travelHours;
	@Column(name = "business_unit")
	private String businessUnit;
	@Column(name = "lives_impacted")
	private int livesImpacted;
	@ManyToOne
	@JoinColumn(name = "eventId")
	@JsonIgnore
	private Event event;

}
