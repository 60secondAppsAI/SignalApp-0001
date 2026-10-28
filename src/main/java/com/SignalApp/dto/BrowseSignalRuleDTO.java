package com.SignalApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseSignalRuleDTO {

	private Integer ownerId;

	private Integer signalRuleId;

	private Integer signalRuleStatus;
	
	private Integer nextOrPrevious;
}

