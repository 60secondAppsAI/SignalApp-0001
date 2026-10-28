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

import com.SignalApp.domain.Signal;
import com.SignalApp.dto.SignalDTO;
import com.SignalApp.dto.SignalSearchDTO;
import com.SignalApp.dto.SignalPageDTO;
import com.SignalApp.service.SignalService;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/signal")
@RestController
public class SignalController {

	private final static Logger logger = LoggerFactory.getLogger(SignalController.class);

	@Autowired
	SignalService signalService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Signal> getAll() {

		List<Signal> signals = signalService.findAll();
		
		return signals;	
	}

	@GetMapping(value = "/{signalId}")
	@ResponseBody
	public SignalDTO getSignal(@PathVariable Integer signalId) {
		
		return (signalService.getSignalDTOById(signalId));
	}

 	@RequestMapping(value = "/addSignal", method = RequestMethod.POST)
	public ResponseEntity<?> addSignal(@RequestBody SignalDTO signalDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = signalService.addSignal(signalDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/signals")
	public ResponseEntity<SignalPageDTO> getSignals(SignalSearchDTO signalSearchDTO) {
 
		return signalService.getSignals(signalSearchDTO);
	}	

	@RequestMapping(value = "/updateSignal", method = RequestMethod.POST)
	public ResponseEntity<?> updateSignal(@RequestBody SignalDTO signalDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = signalService.updateSignal(signalDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
