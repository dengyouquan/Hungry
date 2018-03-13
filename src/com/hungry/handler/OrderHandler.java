package com.hungry.handler;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hungry.dao.OrderDao;
import com.hungry.entities.Order;

@Controller
public class OrderHandler {
	@Autowired
	private OrderDao orderDao;
	
	//show all orders
	@RequestMapping(value="/orders",method=RequestMethod.GET)
	public String orderList(Map<String, Object> map){
		List<Order> orders = orderDao.getAll();
		System.out.println("orderList");
		map.put("orders", orders);
		return "orderList";
	}
	
	//add a order for input
	@RequestMapping(value="/order",method=RequestMethod.GET)
	public String orderInput(Map<String, Object> map){
		map.put("order", new Order());
		System.out.println("orderInput");
		return "orderInput";
	}
	
	//add a order
	@RequestMapping(value="/order",method=RequestMethod.POST)
	public String save(@Valid Order order,BindingResult result,
			@RequestParam("number")String number,@RequestParam("memo") String memo){
		try {		
			number = new String(number.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(number);
			order.setNumber(number);
			
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
			order.setMemo(memo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(result.getErrorCount()>0){
			for(FieldError error:result.getFieldErrors()){
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			return "input";
		}else{
			order.setStartTime(new Date(System.currentTimeMillis()));
			order.setStatus(false);
			orderDao.saveOrUpdate(order);
			System.out.println("saveOrUpdate:"+order);
		}
		return "redirect:/orders";
	}
	
	//delete a order
	@RequestMapping(value="/order/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id){
		orderDao.delete(id);
		return "redirect:/orders";
	}
	
	//update a order  gain record from database
	@ModelAttribute
	public void getOrder(@RequestParam(value="id",required=false) Integer id, Map<String, Object> map){
		if(id!=null){
			map.put("order", orderDao.get(id));
			System.out.println("@ModelAttribute order"+":"+ orderDao.get(id));
		}
	}
	
	//update a order for input
	@RequestMapping(value="/order/{id}",method=RequestMethod.GET)
	public String orderInput(@PathVariable("id") Integer id,Map<String, Object> map){
		map.put("order", orderDao.get(id));
		System.out.println("update orderInput");
		return "orderInput";
	}
	
	//update a order
	@RequestMapping(value="/order",method=RequestMethod.PUT)
	public String update(@Valid Order order,
			@RequestParam("memo") String memo){
		try {
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
			order.setMemo(memo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		orderDao.saveOrUpdate(order);
		System.out.println("update order:"+order);
		return "redirect:/orders";
	}
	
}
