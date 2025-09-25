package edu.remad.tutoring3.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controls index path
 * 
 * @author edu.remad
 * @since 2025
 */
@RestController
public class IndexController {

	@GetMapping("/")
	public String index() {
		return "It is the index";
	}
}
