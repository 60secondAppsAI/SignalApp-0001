package com.SignalApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseSignalInputDTO {

	private Integer ownerId;

	private Integer signalInputId;

	private Integer signalInputStatus;
	
	private Integer nextOrPrevious;
}

