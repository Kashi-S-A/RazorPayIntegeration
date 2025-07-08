package com.ksa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksa.dto.StudentOrder;
import com.ksa.service.StudentService;

@Controller
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/")
	public String init() {
		return "index";
	}

	@PostMapping(value = "/create-order", produces = "application/json")
	@ResponseBody
	public ResponseEntity<StudentOrder> createOrder(@RequestBody StudentOrder studentOrder) throws Exception {
		StudentOrder createdOrder = studentService.createOrder(studentOrder);
		return new ResponseEntity<StudentOrder>(createdOrder, HttpStatus.CREATED);
	}

	@PostMapping("/handle-payment-callback")
	public String handlePaymentCallback(@RequestParam Map<String, String> respPayload) {
		System.out.println(respPayload);
		studentService.updateOrder(respPayload);
		return "success";
	}
}
