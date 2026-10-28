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
import com.SignalApp.dao.SignalDAO;
import com.SignalApp.domain.Signal;
import com.SignalApp.dto.SignalDTO;
import com.SignalApp.dto.SignalSearchDTO;
import com.SignalApp.dto.SignalPageDTO;
import com.SignalApp.dto.SignalConvertCriteriaDTO;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;
import com.SignalApp.service.SignalService;
import com.SignalApp.util.ControllerUtils;


@Service
public class SignalServiceImpl extends GenericServiceImpl<Signal, Integer> implements SignalService {

    private final static Logger logger = LoggerFactory.getLogger(SignalServiceImpl.class);

	@Autowired
	SignalDAO signalDao;

	

	@Override
	public GenericDAO<Signal, Integer> getDAO() {
		return (GenericDAO<Signal, Integer>) signalDao;
	}
	
	public List<Signal> findAll () {
		List<Signal> signals = signalDao.findAll();
		
		return signals;	
		
	}

	public ResultDTO addSignal(SignalDTO signalDTO, RequestDTO requestDTO) {

		Signal signal = new Signal();

		signal.setSignalId(signalDTO.getSignalId());

		signal.setStrategyId(signalDTO.getStrategyId());

		signal.setInstrumentId(signalDTO.getInstrumentId());

		signal.setSignalType(signalDTO.getSignalType());

		signal.setStrength(signalDTO.getStrength());

		signal.setGeneratedAt(signalDTO.getGeneratedAt());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		signal = signalDao.save(signal);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Signal> getAllSignals(Pageable pageable) {
		return signalDao.findAll(pageable);
	}

	public Page<Signal> getAllSignals(Specification<Signal> spec, Pageable pageable) {
		return signalDao.findAll(spec, pageable);
	}

	public ResponseEntity<SignalPageDTO> getSignals(SignalSearchDTO signalSearchDTO) {
	
			Integer signalId = signalSearchDTO.getSignalId(); 
 			Integer strategyId = signalSearchDTO.getStrategyId(); 
 			Integer instrumentId = signalSearchDTO.getInstrumentId(); 
 			String signalType = signalSearchDTO.getSignalType(); 
  			Date generatedAt = signalSearchDTO.getGeneratedAt();
 			String sortBy = signalSearchDTO.getSortBy();
			String sortOrder = signalSearchDTO.getSortOrder();
			String searchQuery = signalSearchDTO.getSearchQuery();
			Integer page = signalSearchDTO.getPage();
			Integer size = signalSearchDTO.getSize();

	        Specification<Signal> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, signalId, "signalId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, strategyId, "strategyId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, signalType, "signalType"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, generatedAt, "generatedAt");
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("signalType")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Signal> signals = this.getAllSignals(spec, pageable);
		
		//System.out.println(String.valueOf(signals.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(signals.getTotalPages()));
		
		List<Signal> signalsList = signals.getContent();
		
		SignalConvertCriteriaDTO convertCriteria = new SignalConvertCriteriaDTO();
		List<SignalDTO> signalDTOs = this.convertSignalsToSignalDTOs(signalsList,convertCriteria);
		
		SignalPageDTO signalPageDTO = new SignalPageDTO();
		signalPageDTO.setSignals(signalDTOs);
		signalPageDTO.setTotalElements(signals.getTotalElements());
		return ResponseEntity.ok(signalPageDTO);
	}

	public List<SignalDTO> convertSignalsToSignalDTOs(List<Signal> signals, SignalConvertCriteriaDTO convertCriteria) {
		
		List<SignalDTO> signalDTOs = new ArrayList<SignalDTO>();
		
		for (Signal signal : signals) {
			signalDTOs.add(convertSignalToSignalDTO(signal,convertCriteria));
		}
		
		return signalDTOs;

	}
	
	public SignalDTO convertSignalToSignalDTO(Signal signal, SignalConvertCriteriaDTO convertCriteria) {
		
		SignalDTO signalDTO = new SignalDTO();

		signalDTO.setSignalId(signal.getSignalId());

		signalDTO.setStrategyId(signal.getStrategyId());

		signalDTO.setInstrumentId(signal.getInstrumentId());

		signalDTO.setSignalType(signal.getSignalType());

		signalDTO.setStrength(signal.getStrength());

		signalDTO.setGeneratedAt(signal.getGeneratedAt());
		
		return signalDTO;
	}

	public ResultDTO updateSignal(SignalDTO signalDTO, RequestDTO requestDTO) {
		
		Signal signal = signalDao.getById(signalDTO.getSignalId());
		
		signal.setSignalId(ControllerUtils.setValue(signal.getSignalId(), signalDTO.getSignalId()));
		
		signal.setStrategyId(ControllerUtils.setValue(signal.getStrategyId(), signalDTO.getStrategyId()));
		
		signal.setInstrumentId(ControllerUtils.setValue(signal.getInstrumentId(), signalDTO.getInstrumentId()));
		
		signal.setSignalType(ControllerUtils.setValue(signal.getSignalType(), signalDTO.getSignalType()));
		
		signal.setStrength(ControllerUtils.setValue(signal.getStrength(), signalDTO.getStrength()));
		
		signal.setGeneratedAt(ControllerUtils.setValue(signal.getGeneratedAt(), signalDTO.getGeneratedAt()));

        signal = signalDao.save(signal);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public SignalDTO getSignalDTOById(Integer signalId) {
	
		Signal signal = signalDao.getById(signalId);
		
		SignalConvertCriteriaDTO convertCriteria = new SignalConvertCriteriaDTO();
		return(this.convertSignalToSignalDTO(signal,convertCriteria));
	}

}
