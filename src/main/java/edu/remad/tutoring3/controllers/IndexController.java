package edu.remad.tutoring3.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping("/index")
	public String index() {
		return "It is the index";
	}
}
