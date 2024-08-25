package edu.remad.tutoring3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/velocity")
public class VelocityDemoController {
	
	@GetMapping("/index")
	public String index() {
		return "Default";
	}
}
