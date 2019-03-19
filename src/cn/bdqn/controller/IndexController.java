package cn.bdqn.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.bdqn.entity.User;

@Controller
public class IndexController {

	//显示注册页面
	@RequestMapping("/register.html")
	public String showRegister(@ModelAttribute("user")User user){
		return "register";
	}
	 
	//显示登录页面
	@RequestMapping("/login.html")
	public String showlogin(){
		return "login";
	}
	
	@RequestMapping("/doRegister.html")
	public String doRegister(
			@Valid
			User user,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "register";
		}
		System.out.println(user.getUserName());
		return "frame";
	}
	
	@RequestMapping("/main.html")
	public String showIndex(){
		//int a = 5/0;
		return "frame";
	}
	@RequestMapping("/upload.html")
	public String showUpload(){
		return "upload";
	}
	@RequestMapping("/upload2.html")
	public String showUpload2(){
		return "upload2";
	}
	
}
