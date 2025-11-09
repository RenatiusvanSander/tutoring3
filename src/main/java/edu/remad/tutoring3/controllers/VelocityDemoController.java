package edu.remad.tutoring3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller demonstrates Velocity views
 * 
 * @author edu.remad
 * @since 2025
 */
@Controller
@RequestMapping("/velocity")
public class VelocityDemoController {

	/**
	 * @return string-encoded name of default velocity template
	 */
	@GetMapping("/index")
	public String index() {
		return "Default";
	}
}
