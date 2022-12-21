package com.app.entity;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;


@Table(name="loans")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loans {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name = "loan_number")
	private int loanNumber;
	
	@Column(name = "customer_id")
	private int customerId;
	
	@Column(name="start_dt")
	private Date startDt;
	
	@Column(name = "loan_type")
	private String loanType;
	
	@Column(name = "total_loan")
	private int totalLoan;
	
	@Column(name = "amount_paid")
	private int amountPaid;
	
	@Column(name = "outstanding_amount")
	private int outstandingAmount;
	
	@Column(name = "create_dt")
	private String createDt;


	
}
