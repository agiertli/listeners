package com.company.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jbpm.audit")
public class ListenerConfiguration {

	Logger logger = LoggerFactory.getLogger(ListenerConfiguration.class);

	private List<BpmEventType> enabledEventTypes = new ArrayList<BpmEventType>();
	private boolean auditEnabled = false;
	private boolean enableProcessVariablesForTaskEvents = false;

	public boolean isAuditEnabled() {
		return auditEnabled;
	}

	public void setAuditEnabled(boolean auditEnabled) {
		this.auditEnabled = auditEnabled;
	}

	public boolean isEnableProcessVariablesForTaskEvents() {
		return enableProcessVariablesForTaskEvents;
	}

	public void setEnableProcessVariablesForTaskEvents(boolean enableProcessVariablesForTaskEvents) {
		this.enableProcessVariablesForTaskEvents = enableProcessVariablesForTaskEvents;
	}

	public List<BpmEventType> getEnabledEventTypes() {
		return enabledEventTypes;
	}

	public void setEnabledEventTypes(List<BpmEventType> enabledEventTypes) {
		this.enabledEventTypes = enabledEventTypes;
	}

	@Override
	public String toString() {
		return "ListenerConfiguration [logger=" + logger + ", enabledEventTypes=" + enabledEventTypes
				+ ", auditEnabled=" + auditEnabled + ", enableProcessVariablesForTaskEvents="
				+ enableProcessVariablesForTaskEvents + "]";
	}

}
