package com.lesson.memo.controller;

import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lesson.memo.model.Admin;
import com.lesson.memo.repository.AdminRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
	
	public AdminController(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
	}
	
//ログインページ
	@GetMapping("/signin")
	public String login() {
		return "admin/signin";
	}
	
//新規登録ページ
	@GetMapping("/signup")
	public String signup(Model model) {
		 model.addAttribute("admin", new Admin());
	     return "admin/signup";
	}
	
//登録処理
	@PostMapping("/signup")
	public String register(@ModelAttribute @Valid Admin admin,BindingResult result) {
		if (result.hasErrors()) {
            return "admin/signup";
        }
		
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
	    adminRepository.save(admin);
		return "redirect:/admin/signin";
	}

}
