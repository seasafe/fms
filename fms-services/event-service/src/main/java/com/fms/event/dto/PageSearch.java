/**
 * 
 */
package com.fms.event.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.Data;

/**
 * @author Kesavalu
 *
 */
@Data
public class PageSearch<T>{
	public int page;
	public int size;
	public String sort;
	public T searchCriteria;
	public boolean isGlobalSearch;

	public PageRequest getPageable(){
		if(sort!=null) {
			String[] temp = sort.split(",");
			return PageRequest.of(page,size, temp.length==2 ? Sort.by("Asc".equals(temp[1])?Direction.ASC:Direction.DESC, temp[0]):Sort.unsorted());
		}else {
			return PageRequest.of(page, size);
		}
	}
}