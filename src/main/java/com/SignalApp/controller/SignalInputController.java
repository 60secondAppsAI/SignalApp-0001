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

import com.SignalApp.domain.SignalInput;
import com.SignalApp.dto.SignalInputDTO;
import com.SignalApp.dto.SignalInputSearchDTO;
import com.SignalApp.dto.SignalInputPageDTO;
import com.SignalApp.service.SignalInputService;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/signalInput")
@RestController
public class SignalInputController {

	private final static Logger logger = LoggerFactory.getLogger(SignalInputController.class);

	@Autowired
	SignalInputService signalInputService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<SignalInput> getAll() {

		List<SignalInput> signalInputs = signalInputService.findAll();
		
		return signalInputs;	
	}

	@GetMapping(value = "/{signalInputId}")
	@ResponseBody
	public SignalInputDTO getSignalInput(@PathVariable Integer signalInputId) {
		
		return (signalInputService.getSignalInputDTOById(signalInputId));
	}

 	@RequestMapping(value = "/addSignalInput", method = RequestMethod.POST)
	public ResponseEntity<?> addSignalInput(@RequestBody SignalInputDTO signalInputDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = signalInputService.addSignalInput(signalInputDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/signalInputs")
	public ResponseEntity<SignalInputPageDTO> getSignalInputs(SignalInputSearchDTO signalInputSearchDTO) {
 
		return signalInputService.getSignalInputs(signalInputSearchDTO);
	}	

	@RequestMapping(value = "/updateSignalInput", method = RequestMethod.POST)
	public ResponseEntity<?> updateSignalInput(@RequestBody SignalInputDTO signalInputDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = signalInputService.updateSignalInput(signalInputDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
