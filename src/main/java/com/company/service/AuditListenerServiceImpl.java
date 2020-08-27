package com.company.service;

import static com.company.service.audit.AuditConstants.CONTAINER_ID;
import static com.company.service.audit.AuditConstants.CORRELATION_KEY;
import static com.company.service.audit.AuditConstants.EVENT_TYPE;
import static com.company.service.audit.AuditConstants.PROCESS_DEFINITION_KEY;
import static com.company.service.audit.AuditConstants.PROCESS_INSTANCE_ID;
import static com.company.service.audit.AuditConstants.PROCESS_VAR_PREFIX;
import static com.company.service.audit.AuditConstants.TASK_ASSIGNEE;
import static com.company.service.audit.AuditConstants.TASK_ID;
import static com.company.service.audit.AuditConstants.TASK_TYPE;
import static com.company.service.audit.AuditConstants.TASK_VAR_PREFIX;
import static com.company.service.audit.AuditConstants.TIMESTAMP;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.UserTaskService;
import org.jbpm.workflow.instance.impl.WorkflowProcessInstanceImpl;
import org.kie.api.event.process.ProcessEvent;
import org.kie.api.task.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditListenerServiceImpl implements AuditListenerService {

	Logger logger = LoggerFactory.getLogger(AuditListenerServiceImpl.class);

	@Autowired
	private ListenerConfiguration config;

	@Autowired
	private ProcessService processService;

	@Autowired
	private UserTaskService taskService;

	@PostConstruct
	public void init() {

		logger.info("audit config created:" + config);

	}

	@Override
	public void handleTaskEvent(TaskEvent taskEvent, BpmEventType bpmEventType, Map<String, Object> metadata) {
		if (config.getEnabledEventTypes().isEmpty() || config.getEnabledEventTypes().contains(bpmEventType)) {

			Map<String, Object> payload = createTaskPayload(taskEvent, bpmEventType, metadata);

			logger.info("Payload constructed \n {}", payload);

			// send the payload over JMS
		}
	}

	@Override
	public void handleProcessEvent(ProcessEvent processEvent, BpmEventType bpmEventType, Map<String, Object> metadata) {
		// TODO Auto-generated method stub

		Map<String, Object> payload = createProcessPayload(processEvent, bpmEventType, metadata);

		logger.info("Payload constructed \n {}", payload);

	}

	private Map<String, Object> createProcessPayload(ProcessEvent processEvent, BpmEventType bpmEventType,
			Map<String, Object> metadata) {
		Map<String, Object> payload = new HashMap<String, Object>();

		WorkflowProcessInstanceImpl processImpl = (WorkflowProcessInstanceImpl) processEvent.getProcessInstance();

//		WorkflowProcessInstanceImpl wip = (WorkflowProcessInstanceImpl) processService
//				.getProcessInstance(processEvent.getProcessInstance().getId());

		payload.put(PROCESS_INSTANCE_ID, processEvent.getProcessInstance().getId());
		payload.put(PROCESS_DEFINITION_KEY, processEvent.getProcessInstance().getProcessId());
		payload.put(CORRELATION_KEY, processImpl.getCorrelationKey());
		payload.put(CONTAINER_ID, processImpl.getDeploymentId());
		payload.put(EVENT_TYPE, bpmEventType);
		payload.put(TIMESTAMP, processEvent.getEventDate());

		if (metadata != null && !metadata.isEmpty()) {
			metadata.forEach((k, v) -> {
				logger.info("Copying metadata key {} - value {} ", k, v);
				payload.put(k, v);
			});
		}

		processImpl.getVariables().forEach((k, v) -> {
			logger.info("Copying process var {} with value {}", k, v);
			payload.put(PROCESS_VAR_PREFIX + k, v);
		});

		// send the payload over JMS

		return payload;
	}

	private Map<String, Object> createTaskPayload(TaskEvent taskEvent, BpmEventType bpmEventType,
			Map<String, Object> metadata) {

		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put(TASK_ID, taskEvent.getTask().getId());
		logger.info("task type {}", taskEvent.getTask().getTaskType());
		payload.put(TASK_TYPE, taskEvent.getTask().getTaskType());

		if (taskEvent.getTask().getTaskData().getActualOwner() != null) {
			payload.put(TASK_ASSIGNEE, taskEvent.getTask().getTaskData().getActualOwner().getId());
		} else {
			payload.put(TASK_ASSIGNEE, "");
		}

		taskService.getTaskInputContentByTaskId(taskEvent.getTask().getId()).forEach((k, v) -> {
			logger.info("Copying task var {} with value {}", k, v);
			payload.put(TASK_VAR_PREFIX + k, v);
		});

		WorkflowProcessInstanceImpl processImpl = (WorkflowProcessInstanceImpl) processService
				.getProcessInstance(taskEvent.getTask().getTaskData().getProcessInstanceId());

		if (config.isEnableProcessVariablesForTaskEvents()) {

			processImpl.getVariables().forEach((k, v) -> {
				logger.info("Copying process var {} with value {}", k, v);
				payload.put(PROCESS_VAR_PREFIX + k, v);
			});
		}

		payload.put(TIMESTAMP, taskEvent.getEventDate());
		payload.put(CONTAINER_ID, processImpl.getDeploymentId());
		payload.put(PROCESS_INSTANCE_ID, processImpl.getId());
		payload.put(PROCESS_DEFINITION_KEY, processImpl.getProcessId());
		payload.put(CORRELATION_KEY, processImpl.getCorrelationKey());
		payload.put(EVENT_TYPE, bpmEventType);

		if (metadata != null && !metadata.isEmpty()) {
			metadata.forEach((k, v) -> {
				logger.info("Copying metadata key {} - value {} ", k, v);
				payload.put(k, v);
			});
		}

		return payload;
	}

}
