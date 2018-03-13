package com.hungry.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hungry.dao.MerchantDao;
import com.hungry.dao.UserDao;
import com.hungry.entities.Merchant;
import com.hungry.entities.User;

//可以不要，用js的history.back()即可返回上一页

@Controller
public class ReturnHandler {
	@Autowired
	private UserDao userDao;
	@Autowired
	private MerchantDao merchantDao;
	
	@RequestMapping(value="/userMessage/{id}/return",method=RequestMethod.GET)
	public String returnUserMessage(@PathVariable("id") Integer id,Map<String, Object> map){
		User user = userDao.get(id);
		map.put("user", user);
		System.out.println("return userlogin");
		//System.out.println("redirect:userlogin/"+id);
		return "redirect:/userlogin?account="+user.getAccount()+"&password="+user.getPassword();
	}
	
	@RequestMapping(value="/merchantMessage/{id}/return",method=RequestMethod.GET)
	public String returnMerchantMessage(@PathVariable("id") Integer id,Map<String, Object> map){
		Merchant merchant = merchantDao.get(id);
		map.put("merchant", merchant);
		System.out.println("return merchantMessage");
		return "redirect:/merchantlogin?account="+merchant.getAccount()+"&password="+merchant.getPassword();
	}
	
	@RequestMapping(value="/visitorMessage/{id}/return",method=RequestMethod.GET)
	public String returnMerchantMessageV(@PathVariable("id") Integer id,Map<String, Object> map){
		Merchant merchant = merchantDao.get(id);
		map.put("merchant", merchant);
		System.out.println("return index");
		return "redirect:/";
	}
	
	@RequestMapping(value="/{id}/return",method=RequestMethod.GET)
	public String returnUserLogin(@PathVariable("id") Integer id,Map<String, Object> map){
		return "redirect:/";
	}
	//@RequestMapping(value="/userMessage/{uid }/merchantMessage/{mid }/orders/{oid}/return",method=RequestMethod.GET)
	//{uid }不能加空格Missing URI template variable 'uid' for method parameter of type Integer
	@RequestMapping(value="/userMessage/{uid}/merchantMessage/{mid}/orders/{oid}/return",method=RequestMethod.GET)
	public String returnBUyGoodsList(@PathVariable("uid") Integer uid,@PathVariable("mid") Integer mid,@PathVariable("oid") Integer oid,Map<String, Object> map){
		User user = userDao.get(uid);
		map.put("user", user);
		Merchant merchant = merchantDao.get(mid);
		map.put("merchant", merchant);
		map.put("orders", userDao.get(uid).getOrders());
		System.out.println("redirect:/userMessage/"+uid+"/merchant/"+mid+"/goodss");
		return "redirect:/userMessage/"+uid+"/merchant/"+mid+"/goodss";
	}
	
	
	@RequestMapping(value="/userMessage/{uid}/buy/return",method=RequestMethod.GET)
	public String returnBuyMerchantList(@PathVariable("uid") Integer uid,
			Map<String, Object> map,RedirectAttributes redirectAttributes){
		User user = userDao.get(uid);
		//redirectAttributes.addAttribute("user", user);
		//redirectAttributes.addAttribute("merchants", merchantDao.getAll());
		System.out.println("redirect:/userMessage/"+uid+"/buy");
		return "redirect:/userMessage/"+uid+"/buy";
	}
	
	@RequestMapping(value="/merchantMessage/{mid}/query/return",method=RequestMethod.GET)
	public String returnBuyMerchantListM(@PathVariable("mid") Integer mid,
			Map<String, Object> map,RedirectAttributes redirectAttributes){
		//redirectAttributes.addAttribute("user", user);
		//redirectAttributes.addAttribute("merchants", merchantDao.getAll());
		System.out.println("redirect:/merchantMessage/"+mid+"/query");
		return "redirect:/merchantMessage/"+mid+"/query";
	}
	
	@RequestMapping(value="/visitorMessage/{mid}/query/return",method=RequestMethod.GET)
	public String returnBuyMerchantListV(@PathVariable("mid") Integer mid,
			Map<String, Object> map,RedirectAttributes redirectAttributes){
		//redirectAttributes.addAttribute("user", user);
		//redirectAttributes.addAttribute("merchants", merchantDao.getAll());
		System.out.println("redirect:/merchantMessage/"+mid+"/query");
		return "redirect:/visitorMessage/"+mid+"/query";
	}
	
	@RequestMapping(value="/userMessage/{uid}/orders/return",method=RequestMethod.GET)
	public String returnUserMessage(@PathVariable("uid") Integer uid,
			Map<String, Object> map,RedirectAttributes redirectAttributes){
		User user = userDao.get(uid);
		//redirectAttributes.addAttribute("user", user);
		//redirectAttributes.addAttribute("merchants", merchantDao.getAll());
		System.out.println("redirect:/userMessage/"+uid);
		//redirect后 会在UserManagerHandler中加载
		//@RequestMapping(value="/userMessage/{id}",method=RequestMethod.GET)
		//
		return "redirect:/userMessage/"+uid;
	}
	
	@RequestMapping(value="/merchantMessage/{mid}/goods/return",method=RequestMethod.GET)
	public String returnMerchantMessage(@PathVariable("mid") Integer mid,
			Map<String, Object> map,RedirectAttributes redirectAttributes){
		//redirectAttributes.addAttribute("user", user);
		//redirectAttributes.addAttribute("merchants", merchantDao.getAll());
		System.out.println("redirect:/userMessage/"+mid);
		//redirect后 会在UserManagerHandler中加载
		//@RequestMapping(value="/userMessage/{id}",method=RequestMethod.GET)
		//
		return "redirect:/merchantMessage/"+mid;
	}
}
