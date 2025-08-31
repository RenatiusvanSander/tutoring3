package edu.remad.tutoring3.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controls a test of OAuth2 Resource Server with access check with bearer
 * tokens
 * 
 * @author edu.remad
 * @since 2025
 */
@RequestMapping("/api")
@RestController
public class ApiTestController {

	@GetMapping("/test")
	public String test() {
		return "sadsadsa";
	}
}
