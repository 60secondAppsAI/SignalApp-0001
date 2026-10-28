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
@Table(name="signal_inputs")
@Getter @Setter @NoArgsConstructor
public class SignalInput {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="signal_input_id")
	private Integer signalInputId;
    
  	@Column(name="signal_id")
	private Integer signalId;
    
  	@Column(name="input_name")
	private String inputName;
    
  	@Column(name="input_value")
	private Double inputValue;
    

}
