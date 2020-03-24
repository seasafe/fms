/**
 * 
 */
package com.fms.event.bean;

import java.util.List;

import lombok.Data;

/**
 * @author Kesavalu
 *
 */
@Data
public class EventSearchRequest {

	List<EventRequest> eventSearchCriteria;
}
