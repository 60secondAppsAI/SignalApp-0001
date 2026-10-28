package com.SignalApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.SignalApp.domain.SignalRule;
import com.SignalApp.dto.SignalRuleDTO;
import com.SignalApp.dto.SignalRuleSearchDTO;
import com.SignalApp.dto.SignalRulePageDTO;
import com.SignalApp.dto.SignalRuleConvertCriteriaDTO;
import com.SignalApp.service.GenericService;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SignalRuleService extends GenericService<SignalRule, Integer> {

	List<SignalRule> findAll();

	ResultDTO addSignalRule(SignalRuleDTO signalRuleDTO, RequestDTO requestDTO);

	ResultDTO updateSignalRule(SignalRuleDTO signalRuleDTO, RequestDTO requestDTO);

    Page<SignalRule> getAllSignalRules(Pageable pageable);

    Page<SignalRule> getAllSignalRules(Specification<SignalRule> spec, Pageable pageable);

	ResponseEntity<SignalRulePageDTO> getSignalRules(SignalRuleSearchDTO signalRuleSearchDTO);
	
	List<SignalRuleDTO> convertSignalRulesToSignalRuleDTOs(List<SignalRule> signalRules, SignalRuleConvertCriteriaDTO convertCriteria);

	SignalRuleDTO getSignalRuleDTOById(Integer signalRuleId);



	
}
