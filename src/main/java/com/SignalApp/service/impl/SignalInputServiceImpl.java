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
import com.SignalApp.dao.SignalInputDAO;
import com.SignalApp.domain.SignalInput;
import com.SignalApp.dto.SignalInputDTO;
import com.SignalApp.dto.SignalInputSearchDTO;
import com.SignalApp.dto.SignalInputPageDTO;
import com.SignalApp.dto.SignalInputConvertCriteriaDTO;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;
import com.SignalApp.service.SignalInputService;
import com.SignalApp.util.ControllerUtils;


@Service
public class SignalInputServiceImpl extends GenericServiceImpl<SignalInput, Integer> implements SignalInputService {

    private final static Logger logger = LoggerFactory.getLogger(SignalInputServiceImpl.class);

	@Autowired
	SignalInputDAO signalInputDao;

	

	@Override
	public GenericDAO<SignalInput, Integer> getDAO() {
		return (GenericDAO<SignalInput, Integer>) signalInputDao;
	}
	
	public List<SignalInput> findAll () {
		List<SignalInput> signalInputs = signalInputDao.findAll();
		
		return signalInputs;	
		
	}

	public ResultDTO addSignalInput(SignalInputDTO signalInputDTO, RequestDTO requestDTO) {

		SignalInput signalInput = new SignalInput();

		signalInput.setSignalInputId(signalInputDTO.getSignalInputId());

		signalInput.setSignalId(signalInputDTO.getSignalId());

		signalInput.setInputName(signalInputDTO.getInputName());

		signalInput.setInputValue(signalInputDTO.getInputValue());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		signalInput = signalInputDao.save(signalInput);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<SignalInput> getAllSignalInputs(Pageable pageable) {
		return signalInputDao.findAll(pageable);
	}

	public Page<SignalInput> getAllSignalInputs(Specification<SignalInput> spec, Pageable pageable) {
		return signalInputDao.findAll(spec, pageable);
	}

	public ResponseEntity<SignalInputPageDTO> getSignalInputs(SignalInputSearchDTO signalInputSearchDTO) {
	
			Integer signalInputId = signalInputSearchDTO.getSignalInputId(); 
 			Integer signalId = signalInputSearchDTO.getSignalId(); 
 			String inputName = signalInputSearchDTO.getInputName(); 
  			String sortBy = signalInputSearchDTO.getSortBy();
			String sortOrder = signalInputSearchDTO.getSortOrder();
			String searchQuery = signalInputSearchDTO.getSearchQuery();
			Integer page = signalInputSearchDTO.getPage();
			Integer size = signalInputSearchDTO.getSize();

	        Specification<SignalInput> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, signalInputId, "signalInputId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, signalId, "signalId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, inputName, "inputName"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("inputName")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<SignalInput> signalInputs = this.getAllSignalInputs(spec, pageable);
		
		//System.out.println(String.valueOf(signalInputs.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(signalInputs.getTotalPages()));
		
		List<SignalInput> signalInputsList = signalInputs.getContent();
		
		SignalInputConvertCriteriaDTO convertCriteria = new SignalInputConvertCriteriaDTO();
		List<SignalInputDTO> signalInputDTOs = this.convertSignalInputsToSignalInputDTOs(signalInputsList,convertCriteria);
		
		SignalInputPageDTO signalInputPageDTO = new SignalInputPageDTO();
		signalInputPageDTO.setSignalInputs(signalInputDTOs);
		signalInputPageDTO.setTotalElements(signalInputs.getTotalElements());
		return ResponseEntity.ok(signalInputPageDTO);
	}

	public List<SignalInputDTO> convertSignalInputsToSignalInputDTOs(List<SignalInput> signalInputs, SignalInputConvertCriteriaDTO convertCriteria) {
		
		List<SignalInputDTO> signalInputDTOs = new ArrayList<SignalInputDTO>();
		
		for (SignalInput signalInput : signalInputs) {
			signalInputDTOs.add(convertSignalInputToSignalInputDTO(signalInput,convertCriteria));
		}
		
		return signalInputDTOs;

	}
	
	public SignalInputDTO convertSignalInputToSignalInputDTO(SignalInput signalInput, SignalInputConvertCriteriaDTO convertCriteria) {
		
		SignalInputDTO signalInputDTO = new SignalInputDTO();

		signalInputDTO.setSignalInputId(signalInput.getSignalInputId());

		signalInputDTO.setSignalId(signalInput.getSignalId());

		signalInputDTO.setInputName(signalInput.getInputName());

		signalInputDTO.setInputValue(signalInput.getInputValue());
		
		return signalInputDTO;
	}

	public ResultDTO updateSignalInput(SignalInputDTO signalInputDTO, RequestDTO requestDTO) {
		
		SignalInput signalInput = signalInputDao.getById(signalInputDTO.getSignalInputId());
		
		signalInput.setSignalInputId(ControllerUtils.setValue(signalInput.getSignalInputId(), signalInputDTO.getSignalInputId()));
		
		signalInput.setSignalId(ControllerUtils.setValue(signalInput.getSignalId(), signalInputDTO.getSignalId()));
		
		signalInput.setInputName(ControllerUtils.setValue(signalInput.getInputName(), signalInputDTO.getInputName()));
		
		signalInput.setInputValue(ControllerUtils.setValue(signalInput.getInputValue(), signalInputDTO.getInputValue()));

        signalInput = signalInputDao.save(signalInput);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public SignalInputDTO getSignalInputDTOById(Integer signalInputId) {
	
		SignalInput signalInput = signalInputDao.getById(signalInputId);
		
		SignalInputConvertCriteriaDTO convertCriteria = new SignalInputConvertCriteriaDTO();
		return(this.convertSignalInputToSignalInputDTO(signalInput,convertCriteria));
	}

}
