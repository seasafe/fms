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
import lombok.ToString;

/**
 * @author Kesavalu
 *
 */
@Data
@Entity
@Table(name = "t_event_poc_details")
@EqualsAndHashCode(exclude = {"event"},callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EventPOC extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "event_poc_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventPOCId;
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "employee_name")
	private String employeeName;
	@Column(name = "contact_number")
	private String contactNumber;
	@ManyToOne
	@JoinColumn(name = "eventId")
	@ToString.Exclude
	@JsonIgnore
	private Event event;

}
