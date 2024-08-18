package edu.remad.tutoring3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping("index")
	public String index() {
		return "index";
	}
	
	@GetMapping(".")
	public String mainMapping() {
		return "ffffffff";
	}
}
