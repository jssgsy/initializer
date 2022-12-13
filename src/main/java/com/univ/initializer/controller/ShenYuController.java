package com.univ.initializer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author univ 2022/12/12 15:17
 */
@RestController
@RequestMapping("/shenyu")
public class ShenYuController {

	@GetMapping("/get")
	public String getConfig() {
		System.out.println("------shenyu接入成功------");
		return "ok";
	}

}
