package com.SignalApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignalRuleSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer signalRuleId;
	
	private String name;
	
	private String description;
	
	private String ruleType;
	
	private Boolean active;
	
}
