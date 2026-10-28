package com.SignalApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.SignalApp.domain.SignalEvent;
import com.SignalApp.dto.SignalEventDTO;
import com.SignalApp.dto.SignalEventSearchDTO;
import com.SignalApp.dto.SignalEventPageDTO;
import com.SignalApp.dto.SignalEventConvertCriteriaDTO;
import com.SignalApp.service.GenericService;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SignalEventService extends GenericService<SignalEvent, Integer> {

	List<SignalEvent> findAll();

	ResultDTO addSignalEvent(SignalEventDTO signalEventDTO, RequestDTO requestDTO);

	ResultDTO updateSignalEvent(SignalEventDTO signalEventDTO, RequestDTO requestDTO);

    Page<SignalEvent> getAllSignalEvents(Pageable pageable);

    Page<SignalEvent> getAllSignalEvents(Specification<SignalEvent> spec, Pageable pageable);

	ResponseEntity<SignalEventPageDTO> getSignalEvents(SignalEventSearchDTO signalEventSearchDTO);
	
	List<SignalEventDTO> convertSignalEventsToSignalEventDTOs(List<SignalEvent> signalEvents, SignalEventConvertCriteriaDTO convertCriteria);

	SignalEventDTO getSignalEventDTOById(Integer signalEventId);



	
}
