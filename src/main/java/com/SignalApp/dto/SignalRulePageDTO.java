package com.SignalApp.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignalRulePageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<SignalRuleDTO> signalRules;
}





