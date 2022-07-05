package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class mainController {
	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {
			return "index";
	}
	
	@RequestMapping(value = {"/home"}, method = RequestMethod.GET)
	public String home(Model model) {
			return "home";
	}
	
	@RequestMapping(value = {"/admin/home"}, method = RequestMethod.GET)
	public String adminHome(Model model) {
			return "admin/home";
	}

}
