package pl.kowalewskislodkowski.movierental.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

	@GetMapping("")
	public String toHomePage() {		
		return "home";
	}

	@GetMapping("/login")
	public String toLoginPage(){
		return "login";
	}

	@GetMapping("/sellerPanel")
	public String toSellerPanel(){
		return "sellerPanel/sellerPanel";
	}

}
