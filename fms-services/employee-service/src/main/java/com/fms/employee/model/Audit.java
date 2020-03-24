/**
 * 
 */
package com.fms.employee.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Kesavalu
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Audit {

	@Column(name = "created_by")
    @CreatedBy
    private String createdBy;
 
    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;
    
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDate createdDate;
 
    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDate modifiedDate;
}
