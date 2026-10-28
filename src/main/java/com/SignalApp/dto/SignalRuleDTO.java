package com.SignalApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class SignalRuleDTO {

	private Integer signalRuleId;

	private String name;

	private String description;

	private String ruleType;

	private Boolean active;






}
