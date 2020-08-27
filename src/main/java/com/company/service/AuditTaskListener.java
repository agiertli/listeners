package com.company.service;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.task.TaskEvent;
import org.kie.api.task.TaskLifeCycleEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.company.service.audit.AuditConstants.*;


@Component
public class AuditTaskListener implements TaskLifeCycleEventListener {

	@Autowired
	private AuditListenerServiceImpl auditService;

	Logger logger = LoggerFactory.getLogger(AuditTaskListener.class);

	@Override
	public void afterTaskActivatedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskAddedEvent(TaskEvent event) {
		logger.info("task created event captured");
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put(JBPM_EVENT, TASK_ADDED);
		auditService.handleTaskEvent(event, BpmEventType.TASK_CREATED, metadata);

	}

	@Override
	public void afterTaskClaimedEvent(TaskEvent event) {
		logger.info("task claimed/assigned event captured");
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put(JBPM_EVENT, TASK_CLAIMED);
		auditService.handleTaskEvent(event, BpmEventType.TASK_ASSIGNED, metadata);
	}

	@Override
	public void afterTaskCompletedEvent(TaskEvent event) {


	}

	@Override
	public void afterTaskDelegatedEvent(TaskEvent event) {
		logger.info("task delegated/assigned event captured");
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put(JBPM_EVENT, TASK_DELEGATED);
		auditService.handleTaskEvent(event, BpmEventType.TASK_ASSIGNED, metadata);

	}

	@Override
	public void afterTaskExitedEvent(TaskEvent arg0) {

	}

	@Override
	public void afterTaskFailedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskForwardedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskNominatedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskReleasedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskResumedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskSkippedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskStartedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskStoppedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskSuspendedEvent(TaskEvent event) {
		logger.info("task suspend event captured");
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put(JBPM_EVENT, TASK_SUSPENDED);
		auditService.handleTaskEvent(event, BpmEventType.TASK_SUSPENDED, metadata);

	}

	@Override
	public void beforeTaskActivatedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskAddedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskClaimedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskCompletedEvent(TaskEvent event) {
		logger.info("task completed event captured");
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put(JBPM_EVENT, TASK_COMPLETED);
		auditService.handleTaskEvent(event, BpmEventType.TASK_COMPLETED, metadata);
	}

	@Override
	public void beforeTaskDelegatedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskExitedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskFailedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskForwardedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskNominatedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskReleasedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskResumedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskSkippedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskStartedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskStoppedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskSuspendedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

}
