package com.ksa.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksa.dto.StudentOrder;

public interface StudentOrderRepo extends JpaRepository<StudentOrder, Integer>{

	public StudentOrder findByRazorpayOrderId(String orderId);
}
