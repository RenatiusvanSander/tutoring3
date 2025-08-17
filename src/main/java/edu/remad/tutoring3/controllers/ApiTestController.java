package edu.remad.tutoring3.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiTestController {

	@GetMapping("/test")
	public String test() {
		return "sadsadsa";
	}
}
