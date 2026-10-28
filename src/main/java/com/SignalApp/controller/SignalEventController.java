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

import com.SignalApp.domain.SignalEvent;
import com.SignalApp.dto.SignalEventDTO;
import com.SignalApp.dto.SignalEventSearchDTO;
import com.SignalApp.dto.SignalEventPageDTO;
import com.SignalApp.service.SignalEventService;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/signalEvent")
@RestController
public class SignalEventController {

	private final static Logger logger = LoggerFactory.getLogger(SignalEventController.class);

	@Autowired
	SignalEventService signalEventService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<SignalEvent> getAll() {

		List<SignalEvent> signalEvents = signalEventService.findAll();
		
		return signalEvents;	
	}

	@GetMapping(value = "/{signalEventId}")
	@ResponseBody
	public SignalEventDTO getSignalEvent(@PathVariable Integer signalEventId) {
		
		return (signalEventService.getSignalEventDTOById(signalEventId));
	}

 	@RequestMapping(value = "/addSignalEvent", method = RequestMethod.POST)
	public ResponseEntity<?> addSignalEvent(@RequestBody SignalEventDTO signalEventDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = signalEventService.addSignalEvent(signalEventDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/signalEvents")
	public ResponseEntity<SignalEventPageDTO> getSignalEvents(SignalEventSearchDTO signalEventSearchDTO) {
 
		return signalEventService.getSignalEvents(signalEventSearchDTO);
	}	

	@RequestMapping(value = "/updateSignalEvent", method = RequestMethod.POST)
	public ResponseEntity<?> updateSignalEvent(@RequestBody SignalEventDTO signalEventDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = signalEventService.updateSignalEvent(signalEventDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
