package com.company.service;

import java.util.Map;

import org.kie.api.event.process.ProcessEvent;
import org.kie.api.task.TaskEvent;

public interface AuditListenerService {

	public void handleTaskEvent(TaskEvent taskEvent, BpmEventType bpmEventType, Map<String, Object> metadata);
	public void handleProcessEvent(ProcessEvent taskEvent, BpmEventType bpmEventType, Map<String, Object> metadata);

}
