package com.SignalApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignalSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer signalId;
	
	private Integer strategyId;
	
	private Integer instrumentId;
	
	private String signalType;
	
	private Double strength;
	
	private Date generatedAt;
	
}
