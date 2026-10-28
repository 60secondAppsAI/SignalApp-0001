package com.SignalApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.SignalApp.dao.GenericDAO;
import com.SignalApp.service.GenericService;
import com.SignalApp.service.impl.GenericServiceImpl;
import com.SignalApp.dao.SignalEventDAO;
import com.SignalApp.domain.SignalEvent;
import com.SignalApp.dto.SignalEventDTO;
import com.SignalApp.dto.SignalEventSearchDTO;
import com.SignalApp.dto.SignalEventPageDTO;
import com.SignalApp.dto.SignalEventConvertCriteriaDTO;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;
import com.SignalApp.service.SignalEventService;
import com.SignalApp.util.ControllerUtils;


@Service
public class SignalEventServiceImpl extends GenericServiceImpl<SignalEvent, Integer> implements SignalEventService {

    private final static Logger logger = LoggerFactory.getLogger(SignalEventServiceImpl.class);

	@Autowired
	SignalEventDAO signalEventDao;

	

	@Override
	public GenericDAO<SignalEvent, Integer> getDAO() {
		return (GenericDAO<SignalEvent, Integer>) signalEventDao;
	}
	
	public List<SignalEvent> findAll () {
		List<SignalEvent> signalEvents = signalEventDao.findAll();
		
		return signalEvents;	
		
	}

	public ResultDTO addSignalEvent(SignalEventDTO signalEventDTO, RequestDTO requestDTO) {

		SignalEvent signalEvent = new SignalEvent();

		signalEvent.setSignalEventId(signalEventDTO.getSignalEventId());

		signalEvent.setSignalId(signalEventDTO.getSignalId());

		signalEvent.setEventType(signalEventDTO.getEventType());

		signalEvent.setOccurredAt(signalEventDTO.getOccurredAt());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		signalEvent = signalEventDao.save(signalEvent);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<SignalEvent> getAllSignalEvents(Pageable pageable) {
		return signalEventDao.findAll(pageable);
	}

	public Page<SignalEvent> getAllSignalEvents(Specification<SignalEvent> spec, Pageable pageable) {
		return signalEventDao.findAll(spec, pageable);
	}

	public ResponseEntity<SignalEventPageDTO> getSignalEvents(SignalEventSearchDTO signalEventSearchDTO) {
	
			Integer signalEventId = signalEventSearchDTO.getSignalEventId(); 
 			Integer signalId = signalEventSearchDTO.getSignalId(); 
 			String eventType = signalEventSearchDTO.getEventType(); 
 			Date occurredAt = signalEventSearchDTO.getOccurredAt();
 			String sortBy = signalEventSearchDTO.getSortBy();
			String sortOrder = signalEventSearchDTO.getSortOrder();
			String searchQuery = signalEventSearchDTO.getSearchQuery();
			Integer page = signalEventSearchDTO.getPage();
			Integer size = signalEventSearchDTO.getSize();

	        Specification<SignalEvent> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, signalEventId, "signalEventId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, signalId, "signalId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, eventType, "eventType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, occurredAt, "occurredAt");
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("eventType")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<SignalEvent> signalEvents = this.getAllSignalEvents(spec, pageable);
		
		//System.out.println(String.valueOf(signalEvents.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(signalEvents.getTotalPages()));
		
		List<SignalEvent> signalEventsList = signalEvents.getContent();
		
		SignalEventConvertCriteriaDTO convertCriteria = new SignalEventConvertCriteriaDTO();
		List<SignalEventDTO> signalEventDTOs = this.convertSignalEventsToSignalEventDTOs(signalEventsList,convertCriteria);
		
		SignalEventPageDTO signalEventPageDTO = new SignalEventPageDTO();
		signalEventPageDTO.setSignalEvents(signalEventDTOs);
		signalEventPageDTO.setTotalElements(signalEvents.getTotalElements());
		return ResponseEntity.ok(signalEventPageDTO);
	}

	public List<SignalEventDTO> convertSignalEventsToSignalEventDTOs(List<SignalEvent> signalEvents, SignalEventConvertCriteriaDTO convertCriteria) {
		
		List<SignalEventDTO> signalEventDTOs = new ArrayList<SignalEventDTO>();
		
		for (SignalEvent signalEvent : signalEvents) {
			signalEventDTOs.add(convertSignalEventToSignalEventDTO(signalEvent,convertCriteria));
		}
		
		return signalEventDTOs;

	}
	
	public SignalEventDTO convertSignalEventToSignalEventDTO(SignalEvent signalEvent, SignalEventConvertCriteriaDTO convertCriteria) {
		
		SignalEventDTO signalEventDTO = new SignalEventDTO();

		signalEventDTO.setSignalEventId(signalEvent.getSignalEventId());

		signalEventDTO.setSignalId(signalEvent.getSignalId());

		signalEventDTO.setEventType(signalEvent.getEventType());

		signalEventDTO.setOccurredAt(signalEvent.getOccurredAt());
		
		return signalEventDTO;
	}

	public ResultDTO updateSignalEvent(SignalEventDTO signalEventDTO, RequestDTO requestDTO) {
		
		SignalEvent signalEvent = signalEventDao.getById(signalEventDTO.getSignalEventId());
		
		signalEvent.setSignalEventId(ControllerUtils.setValue(signalEvent.getSignalEventId(), signalEventDTO.getSignalEventId()));
		
		signalEvent.setSignalId(ControllerUtils.setValue(signalEvent.getSignalId(), signalEventDTO.getSignalId()));
		
		signalEvent.setEventType(ControllerUtils.setValue(signalEvent.getEventType(), signalEventDTO.getEventType()));
		
		signalEvent.setOccurredAt(ControllerUtils.setValue(signalEvent.getOccurredAt(), signalEventDTO.getOccurredAt()));

        signalEvent = signalEventDao.save(signalEvent);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public SignalEventDTO getSignalEventDTOById(Integer signalEventId) {
	
		SignalEvent signalEvent = signalEventDao.getById(signalEventId);
		
		SignalEventConvertCriteriaDTO convertCriteria = new SignalEventConvertCriteriaDTO();
		return(this.convertSignalEventToSignalEventDTO(signalEvent,convertCriteria));
	}

}
