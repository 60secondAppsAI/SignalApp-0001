package com.SignalApp.dao;

import java.util.List;
import java.util.Date;

import com.SignalApp.dao.GenericDAO;
import com.SignalApp.domain.SignalEvent;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface SignalEventDAO extends GenericDAO<SignalEvent, Integer> {
  
	List<SignalEvent> findAll();
	


}

