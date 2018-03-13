package com.hungry.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class adminManagerHandler {
	@RequestMapping(value="/adminlogin")
	public String adminLogin(@RequestParam("account") String account,
			@RequestParam("password") String password,HttpServletRequest request){
		System.out.println("admin login");
		if(account.equals("admin") && password.equals("admin")){
			request.setAttribute("login", "success");
			//虚拟管理员id为0
			request.setAttribute("id", "0");
			System.out.println("adminlogin");
			return "adminlogin";
		}
		request.setAttribute("login", "fail");
		System.out.println("login fail");
		return "redirect:/";
	}
}
