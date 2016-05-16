package com.jiafang.service.impl;

import org.springframework.stereotype.Service;

import com.jiafang.service.TestService;

@Service
public class TestServiceImpl implements TestService{

	@Override
	public void test() {
		System.out.println("test service test");
	}

}
