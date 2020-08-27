package com.company.service;

import static com.company.service.audit.AuditConstants.PROCESS_STARTED;
import static com.company.service.audit.AuditConstants.JBPM_EVENT;
import static com.company.service.audit.AuditConstants.PROCESS_COMPLETED;
import static com.company.service.audit.AuditConstants.SIGNAL_RECEIVED;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.workflow.instance.node.EventNodeInstance;
import org.jbpm.workflow.instance.node.TimerNodeInstance;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditProcessListener implements ProcessEventListener {

	@Autowired
	private AuditListenerServiceImpl auditService;

	Logger logger = LoggerFactory.getLogger(AuditProcessListener.class);

	@Override
	public void beforeProcessStarted(ProcessStartedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterProcessStarted(ProcessStartedEvent event) {

		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put(JBPM_EVENT, PROCESS_STARTED);
		logger.info("process started event captured");
		auditService.handleProcessEvent(event, BpmEventType.PROCESS_CREATED, metadata);

	}

	@Override
	public void beforeProcessCompleted(ProcessCompletedEvent event) {

	}

	@Override
	public void afterProcessCompleted(ProcessCompletedEvent event) {
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put(JBPM_EVENT, PROCESS_COMPLETED);
		logger.info("process completed event captured");
		auditService.handleProcessEvent(event, BpmEventType.PROCESS_COMPLETED, metadata);
	}

	@Override
	public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {

	}

	@Override
	public void beforeNodeLeft(ProcessNodeLeftEvent event) {
		if (event.getNodeInstance() instanceof EventNodeInstance) {

			EventNodeInstance signal = (EventNodeInstance) event.getNodeInstance();

			logger.info("event Type {}", signal.getEventType());
			signal.getMetaData().forEach((k, v) -> {

				logger.info("signal key {}, signal value {}", k, v);
			});

			Map<String, Object> metadata = new HashMap<String, Object>();
			metadata.put(JBPM_EVENT, SIGNAL_RECEIVED);
			logger.info("signal received  event captured");
			auditService.handleProcessEvent(event, BpmEventType.SIGNAL_RECEIVED, metadata);

		} else if (event.getNodeInstance() instanceof TimerNodeInstance) {

			logger.info("Timer fired - listener");

		}
	}

	@Override
	public void afterNodeLeft(ProcessNodeLeftEvent event) {

	}

	@Override
	public void beforeVariableChanged(ProcessVariableChangedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterVariableChanged(ProcessVariableChangedEvent event) {
		// TODO Auto-generated method stub

	}

}
