package com.fms.event.watch;

import java.util.EventListener;

import org.springframework.stereotype.Component;

import com.fms.event.batch.EventSummaryBatchLauncher;
@Component
public interface FileListener extends EventListener {
	
	public void onCreated(FileEvent event,EventSummaryBatchLauncher eventSummaryBatchLauncher);

	public void onModified(FileEvent event,EventSummaryBatchLauncher eventSummaryBatchLauncher);

	public void onDeleted(FileEvent event,EventSummaryBatchLauncher eventSummaryBatchLauncher);
}