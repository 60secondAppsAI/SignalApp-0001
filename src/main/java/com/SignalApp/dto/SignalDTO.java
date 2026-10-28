package com.SignalApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class SignalDTO {

	private Integer signalId;

	private Integer strategyId;

	private Integer instrumentId;

	private String signalType;

	private Double strength;

	private Date generatedAt;






}
