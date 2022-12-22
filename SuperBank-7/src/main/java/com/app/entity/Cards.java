package com.app.entity;
import java.sql.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Cards {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name = "card_id")
	private int cardId;

	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "card_type")
	private String cardType;

	@Column(name = "total_limit")
	private int totalLimit;

	@Column(name = "amount_used")
	private int amountUsed;

	@Column(name = "available_amount")
	private int availableAmount;

	@Column(name = "create_dt")
	private Date createDt;


	
}
