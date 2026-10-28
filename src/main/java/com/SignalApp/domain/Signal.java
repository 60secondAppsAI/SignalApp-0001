package com.SignalApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="signals")
@Getter @Setter @NoArgsConstructor
public class Signal {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="signal_id")
	private Integer signalId;
    
  	@Column(name="strategy_id")
	private Integer strategyId;
    
  	@Column(name="instrument_id")
	private Integer instrumentId;
    
  	@Column(name="signal_type")
	private String signalType;
    
  	@Column(name="strength")
	private Double strength;
    
  	@Column(name="generated_at")
	private Date generatedAt;
    

}
