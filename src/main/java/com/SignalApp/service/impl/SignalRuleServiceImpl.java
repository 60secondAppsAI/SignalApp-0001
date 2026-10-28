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
import com.SignalApp.dao.SignalRuleDAO;
import com.SignalApp.domain.SignalRule;
import com.SignalApp.dto.SignalRuleDTO;
import com.SignalApp.dto.SignalRuleSearchDTO;
import com.SignalApp.dto.SignalRulePageDTO;
import com.SignalApp.dto.SignalRuleConvertCriteriaDTO;
import com.SignalApp.dto.common.RequestDTO;
import com.SignalApp.dto.common.ResultDTO;
import com.SignalApp.service.SignalRuleService;
import com.SignalApp.util.ControllerUtils;


@Service
public class SignalRuleServiceImpl extends GenericServiceImpl<SignalRule, Integer> implements SignalRuleService {

    private final static Logger logger = LoggerFactory.getLogger(SignalRuleServiceImpl.class);

	@Autowired
	SignalRuleDAO signalRuleDao;

	

	@Override
	public GenericDAO<SignalRule, Integer> getDAO() {
		return (GenericDAO<SignalRule, Integer>) signalRuleDao;
	}
	
	public List<SignalRule> findAll () {
		List<SignalRule> signalRules = signalRuleDao.findAll();
		
		return signalRules;	
		
	}

	public ResultDTO addSignalRule(SignalRuleDTO signalRuleDTO, RequestDTO requestDTO) {

		SignalRule signalRule = new SignalRule();

		signalRule.setSignalRuleId(signalRuleDTO.getSignalRuleId());

		signalRule.setName(signalRuleDTO.getName());

		signalRule.setDescription(signalRuleDTO.getDescription());

		signalRule.setRuleType(signalRuleDTO.getRuleType());

		signalRule.setActive(signalRuleDTO.getActive());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		signalRule = signalRuleDao.save(signalRule);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<SignalRule> getAllSignalRules(Pageable pageable) {
		return signalRuleDao.findAll(pageable);
	}

	public Page<SignalRule> getAllSignalRules(Specification<SignalRule> spec, Pageable pageable) {
		return signalRuleDao.findAll(spec, pageable);
	}

	public ResponseEntity<SignalRulePageDTO> getSignalRules(SignalRuleSearchDTO signalRuleSearchDTO) {
	
			Integer signalRuleId = signalRuleSearchDTO.getSignalRuleId(); 
 			String name = signalRuleSearchDTO.getName(); 
 			String description = signalRuleSearchDTO.getDescription(); 
 			String ruleType = signalRuleSearchDTO.getRuleType(); 
  			String sortBy = signalRuleSearchDTO.getSortBy();
			String sortOrder = signalRuleSearchDTO.getSortOrder();
			String searchQuery = signalRuleSearchDTO.getSearchQuery();
			Integer page = signalRuleSearchDTO.getPage();
			Integer size = signalRuleSearchDTO.getSize();

	        Specification<SignalRule> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, signalRuleId, "signalRuleId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			spec = ControllerUtils.andIfNecessary(spec, ruleType, "ruleType"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("ruleType")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<SignalRule> signalRules = this.getAllSignalRules(spec, pageable);
		
		//System.out.println(String.valueOf(signalRules.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(signalRules.getTotalPages()));
		
		List<SignalRule> signalRulesList = signalRules.getContent();
		
		SignalRuleConvertCriteriaDTO convertCriteria = new SignalRuleConvertCriteriaDTO();
		List<SignalRuleDTO> signalRuleDTOs = this.convertSignalRulesToSignalRuleDTOs(signalRulesList,convertCriteria);
		
		SignalRulePageDTO signalRulePageDTO = new SignalRulePageDTO();
		signalRulePageDTO.setSignalRules(signalRuleDTOs);
		signalRulePageDTO.setTotalElements(signalRules.getTotalElements());
		return ResponseEntity.ok(signalRulePageDTO);
	}

	public List<SignalRuleDTO> convertSignalRulesToSignalRuleDTOs(List<SignalRule> signalRules, SignalRuleConvertCriteriaDTO convertCriteria) {
		
		List<SignalRuleDTO> signalRuleDTOs = new ArrayList<SignalRuleDTO>();
		
		for (SignalRule signalRule : signalRules) {
			signalRuleDTOs.add(convertSignalRuleToSignalRuleDTO(signalRule,convertCriteria));
		}
		
		return signalRuleDTOs;

	}
	
	public SignalRuleDTO convertSignalRuleToSignalRuleDTO(SignalRule signalRule, SignalRuleConvertCriteriaDTO convertCriteria) {
		
		SignalRuleDTO signalRuleDTO = new SignalRuleDTO();

		signalRuleDTO.setSignalRuleId(signalRule.getSignalRuleId());

		signalRuleDTO.setName(signalRule.getName());

		signalRuleDTO.setDescription(signalRule.getDescription());

		signalRuleDTO.setRuleType(signalRule.getRuleType());

		signalRuleDTO.setActive(signalRule.getActive());
		
		return signalRuleDTO;
	}

	public ResultDTO updateSignalRule(SignalRuleDTO signalRuleDTO, RequestDTO requestDTO) {
		
		SignalRule signalRule = signalRuleDao.getById(signalRuleDTO.getSignalRuleId());
		
		signalRule.setSignalRuleId(ControllerUtils.setValue(signalRule.getSignalRuleId(), signalRuleDTO.getSignalRuleId()));
		
		signalRule.setName(ControllerUtils.setValue(signalRule.getName(), signalRuleDTO.getName()));
		
		signalRule.setDescription(ControllerUtils.setValue(signalRule.getDescription(), signalRuleDTO.getDescription()));
		
		signalRule.setRuleType(ControllerUtils.setValue(signalRule.getRuleType(), signalRuleDTO.getRuleType()));
		
		signalRule.setActive(ControllerUtils.setValue(signalRule.getActive(), signalRuleDTO.getActive()));

        signalRule = signalRuleDao.save(signalRule);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public SignalRuleDTO getSignalRuleDTOById(Integer signalRuleId) {
	
		SignalRule signalRule = signalRuleDao.getById(signalRuleId);
		
		SignalRuleConvertCriteriaDTO convertCriteria = new SignalRuleConvertCriteriaDTO();
		return(this.convertSignalRuleToSignalRuleDTO(signalRule,convertCriteria));
	}

}
