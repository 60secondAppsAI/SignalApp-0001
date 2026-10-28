package com.SignalApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.SignalApp.domain.SignalInput;
import com.SignalApp.dto.SignalInputDTO;
import com.SignalApp.dto.SignalInputSearchDTO;
import com.SignalApp.dto.SignalInputPageDTO;
import com.SignalApp.dto.SignalInputConvertCriteriaDTO;
import com.SignalApp.service.GenericService;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SignalInputService extends GenericService<SignalInput, Integer> {

	List<SignalInput> findAll();

	ResultDTO addSignalInput(SignalInputDTO signalInputDTO, RequestDTO requestDTO);

	ResultDTO updateSignalInput(SignalInputDTO signalInputDTO, RequestDTO requestDTO);

    Page<SignalInput> getAllSignalInputs(Pageable pageable);

    Page<SignalInput> getAllSignalInputs(Specification<SignalInput> spec, Pageable pageable);

	ResponseEntity<SignalInputPageDTO> getSignalInputs(SignalInputSearchDTO signalInputSearchDTO);
	
	List<SignalInputDTO> convertSignalInputsToSignalInputDTOs(List<SignalInput> signalInputs, SignalInputConvertCriteriaDTO convertCriteria);

	SignalInputDTO getSignalInputDTOById(Integer signalInputId);



	
}
