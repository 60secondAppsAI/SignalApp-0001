package com.SignalApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseSignalEventDTO {

	private Integer ownerId;

	private Integer signalEventId;

	private Integer signalEventStatus;
	
	private Integer nextOrPrevious;
}

