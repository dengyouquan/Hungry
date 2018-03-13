package com.hungry.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hungry.dao.GoodsDao;
import com.hungry.dao.MerchantDao;
import com.hungry.dao.OrderDao;
import com.hungry.dao.OrderItemDao;
import com.hungry.entities.User;

@Controller
public class CommonHandler {
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderItemDao orderItemDao;
	
	@RequestMapping(value="/old")
	public String toLogin(){
		return "login";
	}
	
	@RequestMapping(value="/register")
	public String toRegister(Map<String,Object> map){
		map.put("user", new User());
		return "userInput";
	}
}
