package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {
	
	@GetMapping("myBalance")
	public String balanceController() { return "Here balacen details from DB";}
}
