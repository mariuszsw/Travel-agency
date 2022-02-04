package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import main.model.User;
import main.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/login")
	public String showLoginPage(){
		return "login";
	}
	
	@RequestMapping("/forbidden")
	public String showForbiddenError(){
		return "403";
	}
	
	@GetMapping("/signup")
	public String singup(Model model) {
		model.addAttribute("user", new User());
		return "form-singup";		
	}
	
	@PostMapping("/processSingup")
	public String processSingup(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
		
		boolean errors = false;
		
		if(!user.getPassword().equals(user.getConfirmedPassword())) {
			redirectAttributes.addAttribute("differentPasswords", "Password are different");
			errors = true;			
		}
		
		if(userService.loginExistes(user.getLogin())) {
			redirectAttributes.addAttribute("loginExists", "Login already existes in the database");
			errors = true;
		}
		
		if(errors) {			
			return "redirect:/signup";
		}		
		
		userService.createNewAccount(user);
		return "login";
	}
}
