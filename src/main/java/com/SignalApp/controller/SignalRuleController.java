package com.SignalApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.SignalApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.SignalApp.domain.SignalRule;
import com.SignalApp.dto.SignalRuleDTO;
import com.SignalApp.dto.SignalRuleSearchDTO;
import com.SignalApp.dto.SignalRulePageDTO;
import com.SignalApp.service.SignalRuleService;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/signalRule")
@RestController
public class SignalRuleController {

	private final static Logger logger = LoggerFactory.getLogger(SignalRuleController.class);

	@Autowired
	SignalRuleService signalRuleService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<SignalRule> getAll() {

		List<SignalRule> signalRules = signalRuleService.findAll();
		
		return signalRules;	
	}

	@GetMapping(value = "/{signalRuleId}")
	@ResponseBody
	public SignalRuleDTO getSignalRule(@PathVariable Integer signalRuleId) {
		
		return (signalRuleService.getSignalRuleDTOById(signalRuleId));
	}

 	@RequestMapping(value = "/addSignalRule", method = RequestMethod.POST)
	public ResponseEntity<?> addSignalRule(@RequestBody SignalRuleDTO signalRuleDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = signalRuleService.addSignalRule(signalRuleDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/signalRules")
	public ResponseEntity<SignalRulePageDTO> getSignalRules(SignalRuleSearchDTO signalRuleSearchDTO) {
 
		return signalRuleService.getSignalRules(signalRuleSearchDTO);
	}	

	@RequestMapping(value = "/updateSignalRule", method = RequestMethod.POST)
	public ResponseEntity<?> updateSignalRule(@RequestBody SignalRuleDTO signalRuleDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = signalRuleService.updateSignalRule(signalRuleDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
