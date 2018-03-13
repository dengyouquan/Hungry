package com.hungry.handler;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hungry.dao.GoodsDao;
import com.hungry.dao.MerchantDao;
import com.hungry.dao.OrderDao;
import com.hungry.entities.Goods;
import com.hungry.entities.Merchant;

@Controller
public class visitorHandler {
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private OrderDao orderDao;
	
		
		//show all merchant for buy
		@RequestMapping(value="/visitorMessage/{mid}/query",method=RequestMethod.GET)
		public String buyMerchantsList(@PathVariable("mid") Integer mid,Map<String, Object> map){
			map.put("merchants", merchantDao.getAll());
			map.put("goodss", goodsDao.getAll());
			map.put("flag", "visitor");
			System.out.println("buyMerchantList show orders");
			return "buyMerchantList";
		}
		
		@RequestMapping(value="/visitorMessage/{uid}/merchant/{mid}/goodss",method=RequestMethod.GET)
		public String buyGoodsList(@PathVariable("uid") Integer uid,@PathVariable("mid") Integer mid,Map<String, Object> map){
			map.put("merchant", merchantDao.get(mid));
			map.put("goodss", merchantDao.get(mid).getGoods());
			map.put("flag", "visitor");
			System.out.println("buyGoodsList show orders");
			return "buyGoodsList";
		}
}
