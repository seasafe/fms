package com.fms.event.batch;

import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;
import org.springframework.stereotype.Component;

import com.fms.event.batch.dto.EventSummaryDTO;
@Component
public class EventSummaryExcelRowMapper implements RowMapper<EventSummaryDTO> {

    @Override
    public EventSummaryDTO mapRow(RowSet rowSet) throws Exception {
    	EventSummaryDTO event = new EventSummaryDTO();

        event.setEventRefId(rowSet.getColumnValue(0));
        event.setMonth(rowSet.getColumnValue(1));
        event.setBaseLoc(rowSet.getColumnValue(2));
        event.setBeneficiaryName(rowSet.getColumnValue(3));
        event.setVenueAddress(rowSet.getColumnValue(4));
        event.setCouncilName(rowSet.getColumnValue(5));
        event.setProject(rowSet.getColumnValue(6));
        event.setCategory(rowSet.getColumnValue(7));
        event.setEventName(rowSet.getColumnValue(8));
        event.setEventDesc(rowSet.getColumnValue(9));
        event.setEventDate(rowSet.getColumnValue(10));
        event.setTotalVolunteers(rowSet.getColumnValue(11));
        event.setTotalVolunteerHours(rowSet.getColumnValue(12));
        event.setTotalTravelHours(rowSet.getColumnValue(13));
        event.setOverAllVolunteerHours(rowSet.getColumnValue(14));
        event.setLivesImpacted(rowSet.getColumnValue(15));
        event.setActivityType(rowSet.getColumnValue(16));
        event.setStatus(rowSet.getColumnValue(17));
        event.setPocId(rowSet.getColumnValue(18));
        event.setPocName(rowSet.getColumnValue(19));
        event.setPocContactNumber(rowSet.getColumnValue(20));
        return event;
    }
}
