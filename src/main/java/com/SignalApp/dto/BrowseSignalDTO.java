package com.SignalApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseSignalDTO {

	private Integer ownerId;

	private Integer signalId;

	private Integer signalStatus;
	
	private Integer nextOrPrevious;
}

