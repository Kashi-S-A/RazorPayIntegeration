package com.ksa.service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ksa.dto.StudentOrder;
import com.ksa.repo.StudentOrderRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class StudentService {

	@Autowired
	private StudentOrderRepo repo;

	@Value("${razorpay.key.id}")
	private String razorpayKey;

	@Value("${razorpay.secret.key}")
	private String razorpaySecret;

	private RazorpayClient razorpayClient;

	public StudentOrder createOrder(StudentOrder stuOrder) throws Exception {

		JSONObject orderReq = new JSONObject();

		orderReq.put("amount", stuOrder.getAmount() * 100);// amount in paisa
		orderReq.put("currency", "INR");
		orderReq.put("receipt", stuOrder.getEmail());

		this.razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);

		// create order in razor_pay
		Order order = razorpayClient.orders.create(orderReq);

		stuOrder.setRazorpayOrderId(order.get("id"));
		stuOrder.setOrderStatus(order.get("status"));

		repo.save(stuOrder);

		return stuOrder;
	}

	public StudentOrder updateOrder(Map<String, String> responsePayLoad) {
		String razorPayOrderId = responsePayLoad.get("razorpay_order_id");
		StudentOrder order = repo.findByRazorpayOrderId(razorPayOrderId);

		order.setOrderStatus("PAYMENT_COMPLETED");

		StudentOrder updatedOrder = repo.save(order);
		
		//send email

		return updatedOrder;
	}
}
