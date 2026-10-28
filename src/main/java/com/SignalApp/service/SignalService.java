package com.SignalApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.SignalApp.domain.Signal;
import com.SignalApp.dto.SignalDTO;
import com.SignalApp.dto.SignalSearchDTO;
import com.SignalApp.dto.SignalPageDTO;
import com.SignalApp.dto.SignalConvertCriteriaDTO;
import com.SignalApp.service.GenericService;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SignalService extends GenericService<Signal, Integer> {

	List<Signal> findAll();

	ResultDTO addSignal(SignalDTO signalDTO, RequestDTO requestDTO);

	ResultDTO updateSignal(SignalDTO signalDTO, RequestDTO requestDTO);

    Page<Signal> getAllSignals(Pageable pageable);

    Page<Signal> getAllSignals(Specification<Signal> spec, Pageable pageable);

	ResponseEntity<SignalPageDTO> getSignals(SignalSearchDTO signalSearchDTO);
	
	List<SignalDTO> convertSignalsToSignalDTOs(List<Signal> signals, SignalConvertCriteriaDTO convertCriteria);

	SignalDTO getSignalDTOById(Integer signalId);



	
}
