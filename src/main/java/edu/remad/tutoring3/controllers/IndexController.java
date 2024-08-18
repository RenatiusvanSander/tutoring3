package edu.remad.tutoring3.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class IndexController {

	@GetMapping(path="index", produces = MediaType.APPLICATION_JSON_VALUE)
	public String index() {
		return "gdgdgdgdgdg hgdgdgdgd";
	}
}
