package com.hungry.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hungry.common.EscapeUnescape;
import com.hungry.dao.GoodsDao;
import com.hungry.dao.MerchantDao;
import com.hungry.dao.OrderDao;
import com.hungry.dao.OrderItemDao;
import com.hungry.dao.UserDao;
import com.hungry.entities.Goods;
import com.hungry.entities.Order;
import com.hungry.entities.OrderItem;
import com.hungry.entities.User;

@Controller
public class UserManagerHandler {
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private OrderItemDao orderItemDao;
	
	//user login
	@RequestMapping(value="/userlogin")
	public String userLogin(Map<String,Object> map,@RequestParam("account") String account,
			@RequestParam("password") String password,
			HttpServletRequest request,HttpServletResponse response,HttpSession session){
		List<User> users = userDao.getAll();
		for(User user:users){
			if(user.getAccount().equalsIgnoreCase(account) && user.getPassword().equalsIgnoreCase(password)){
				//map.put("login", "success");
				request.setAttribute("login", "success");
				request.setAttribute("id", user.getId());
				//session.setAttribute("login", "success");
				System.out.println("success");
				return "userlogin";
			}
			if(user.getName().equalsIgnoreCase(account) && user.getPassword().equalsIgnoreCase(password)){
				//map.put("login", "success");
				request.setAttribute("login", "success");
				request.setAttribute("id", user.getId());
				//session.setAttribute("login", "success");
				System.out.println("success");
				return "userlogin";
			}
		}
		/*map.put("login", "fail");
		request.setAttribute("login", "fail");
		try {
			response.getWriter().append("fail");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		map.put("login", "fail");
		request.setAttribute("login", "fail");
		session.setAttribute("login", "fail");
		System.out.println("login fail");
		return "redirect:/";
		//return "forward:/";
	}
	
	
	@RequestMapping(value="/userMessage/{id}",method=RequestMethod.GET)
	public String userMessage(@PathVariable("id") Integer id,Map<String, Object> map){
		map.put("user", userDao.get(id));
		System.out.println("userMessage");
		return "userMessage";
	}
	
	//show all orders
		@RequestMapping(value="/userMessage/{uid}/orders",method=RequestMethod.GET)
		public String orderList(@PathVariable("uid") Integer id,Map<String, Object> map){
			User user = userDao.get(id);
			map.put("user", user);
			map.put("orders", user.getOrders());
			System.out.println("morderList show orders");
			return "morderList";
		}
		
		//add a order for input
		@RequestMapping(value="/userMessage/{uid}/order",method=RequestMethod.GET)
		public String orderInput(Map<String, Object> map,@PathVariable("uid") Integer id){
			map.put("order", new Order());
			User user = userDao.get(id);
			map.put("user", user);
			System.out.println("userMessage add ordersInput");
			return "morderInput";
		}
		
		//add a order
		@RequestMapping(value="/userMessage/{uid}/order",method=RequestMethod.POST)
		public String save(@Valid Order order,BindingResult result,
				@RequestParam("number")String number,@RequestParam("memo") String memo,
				@PathVariable("uid") Integer id){
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
				return "morderInput";
			}else{
				order.setStartTime(new Date(System.currentTimeMillis()));
				order.setStatus(false);
				order.setId(null);
				orderDao.saveOrUpdate(order);
				System.out.println("add order:"+order);
				User user = userDao.get(id);
				//双向联系
				user.addOrder(order);
				order.setUser(user);
				userDao.saveOrUpdate(user);
			}
			return  "redirect:/userMessage/"+id+"/orders";
		}
		
		//delete a order
		@RequestMapping(value="/userMessage/{uid}/order/{id}",method=RequestMethod.DELETE)
		public String delete(@PathVariable("uid") Integer uid,@PathVariable("id") Integer id,Map<String, Object> map){
			//map.put("user", userDao.get(uid));
			System.out.println("uid"+uid+">id"+id);
			orderDao.delete(id);
			System.out.println("delete:"+id);
			return "redirect:/userMessage/"+uid+"/orders";
		}
		
		//update a order  gain record from database
		@ModelAttribute
		public void getOrder(@RequestParam(value="id",required=false) Integer id, Map<String, Object> map){
			if(id!=null){
				map.put("order", orderDao.get(id));
				System.out.println("userMessage @ModelAttribute order"+":"+ orderDao.get(id));
			}
		}
		
		//update a order for input
		@RequestMapping(value="/userMessage/{uid}/order/{id}",method=RequestMethod.GET)
		public String orderInput(@PathVariable("uid") Integer uid,@PathVariable("id") Integer id,Map<String, Object> map){
			map.put("user", userDao.get(uid));
			map.put("order", orderDao.get(id));
			map.put("orders", orderDao.getAll());
			System.out.println("update morderInput");
			return "morderInput";
		}
		
		//update a order
		@RequestMapping(value="/userMessage/{uid}/order",method=RequestMethod.PUT)
		public String update(@Valid Order order,@PathVariable("uid") Integer uid,
				@RequestParam("memo") String memo){
			try {
				memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
				System.out.println(memo);
				order.setMemo(memo);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			orderDao.saveOrUpdate(order);
			System.out.println("update userMessage order:"+order);
			return "redirect:/userMessage/"+uid+"/orders";
		}
		
		
		//show goodss for order
		@RequestMapping(value="/userMessage/{uid}/order/{id}/goodss",method=RequestMethod.GET)
		public String showgoods(@PathVariable("uid") Integer uid,@PathVariable("id") Integer id,Map<String, Object> map){
			map.put("user", userDao.get(uid));
			map.put("order", orderDao.get(id));
			map.put("orders", orderDao.getAll());
			Set<OrderItem> orderItems =orderDao.get(id).getOrderItems();
			List<Goods> goodss = new ArrayList<Goods>();
			map.put("flag1", "user");
			for(OrderItem orderItem:orderItems){
				goodss.add(orderItem.getGoods());
				System.out.println(orderItem.toString());
			}
			map.put("goodss", goodss);
			System.out.println("test");
			for(Goods g:goodss){
				System.out.println(g.toString());
			}
			//map.put("goodss", goodsDao.getAll());
			System.out.println("show user order goodss morderInput");
			return "mgoodsList";
		}
		
		
		//show all merchant for buy
		@RequestMapping(value="/userMessage/{uid}/buy",method=RequestMethod.GET)
		public String buyMerchantsList(@PathVariable("uid") Integer id,Map<String, Object> map){
			User user = userDao.get(id);
			map.put("user", user);
			map.put("orders", user.getOrders());
			map.put("merchants", merchantDao.getAll());
			map.put("goodss", goodsDao.getAll());
			map.put("flag", "user");
			System.out.println("buyMerchantList show orders");
			return "buyMerchantList";
		}
		
		@RequestMapping(value="/userMessage/{uid}/merchant/{mid}/goodss",method=RequestMethod.GET)
		public String buyGoodsList(@PathVariable("uid") Integer uid,@PathVariable("mid") Integer mid,Map<String, Object> map){
			User user = userDao.get(uid);
			map.put("user", user);
			map.put("merchant", merchantDao.get(mid));
			map.put("goodss", merchantDao.get(mid).getGoods());
			map.put("flag", "user");
			System.out.println("buyGoodsList show orders");
			return "buyGoodsList";
		}
		
		@RequestMapping(value="/userMessage/{uid}/merchant/{mid}/goods/{oid}/buy",method=RequestMethod.GET)
		public String buyGoods(@PathVariable("uid") Integer uid,@PathVariable("mid") Integer mid,@PathVariable("oid") Integer oid,
				Map<String, Object> map,HttpServletRequest request,HttpSession session,RedirectAttributes redirectAttributes){
			User user = userDao.get(uid);
			map.put("user", user);
			Goods goods = goodsDao.get(oid);
			long l = System.currentTimeMillis();
			String s = Long.toString(l);
			if(s.length()>7)
				s = s.substring(s.length()-7, s.length());
			else
				s = s+s;
			s = user.getAccount()+s;
			Order order = new Order(null, s, goods.getPrice(), new Date(System.currentTimeMillis()), false, null);
			OrderItem orderitem = new OrderItem();
			
			//如果orderitem与goods已经有双向关系，跳过
			boolean flag = false;
			List<OrderItem> orderitems = orderItemDao.getAll();
			for(OrderItem o:orderitems){
				if(o.getGoods()==null) continue;
				else if(o.getGoods().equals(goods)){
					flag = true;
					break;
				}
			}
			
			if(!flag){
				//建立双向联系
				orderitem.setGoods(goods);
				goods.getOrderItems().add(orderitem);
				orderItemDao.saveOrUpdate(orderitem);
			}
			
			order.getOrderItems().add(orderitem);
			orderitem.setOrder(order);
			orderDao.saveOrUpdate(order);
			
			user.addOrder(order);
			order.setUser(user);
			userDao.saveOrUpdate(user);
			//无效，使用了redirect
			map.put("user", userDao.get(uid));
			map.put("merchant", merchantDao.get(mid));
			map.put("order", order);
			//保存到request中,也无效
			request.setAttribute("uid", uid);
			request.setAttribute("mid", mid);
			request.setAttribute("oid", oid);
			//保存到session中,取不到
			session.setAttribute("uid", uid);
			session.setAttribute("mid", mid);
			session.setAttribute("oid", oid);
			System.out.println("buy goods");
			redirectAttributes.addAttribute("uid", uid);
			redirectAttributes.addAttribute("mid", mid);
			redirectAttributes.addAttribute("oid", oid);
			//return "buysuccess";
			return "redirect:/orders";
		}
		
		
		/*public String buyGoodss(@PathVariable("uid") Integer uid,@PathVariable("mid") Integer mid,
				Map<String, Object> map,HttpServletRequest request,HttpSession session,RedirectAttributes redirectAttributes){
			User user = userDao.get(uid);
			map.put("user", user);
			Cookie[]  cs = request.getCookies();
			Cookie c = null;
			for(int i=0;i<cs.length;i++){
				//不能用==比较，地址不同
				//if(cs[i].getName()==uid.toString()){
				if(cs[i].getName().equals(uid.toString())){
					c = cs[i];
				}
			}
			System.out.println(c);
			
			String value = c.getValue();
			String value1 = EscapeUnescape.unescape(value);
			String value2 = EscapeUnescape.unescape(value1);
			value = value2;
			String[] values = value.split(",");
			String memo = value;
			//System.out.println(memo);
			Goods goods = null;
			Double p = Double.valueOf(0);
			for(int i=0;i<values.length;i++){
				System.out.println(values[i]);
				String[] temp = values[i].split(":");
				System.out.println(temp[0]);
				System.out.println(Integer.parseInt(temp[0]));
				goods = goodsDao.get(Integer.parseInt(temp[0]));
				p+=goods.getPrice()*Integer.parseInt(temp[1]);
			}
			long l = System.currentTimeMillis();
			String s = Long.toString(l);
			if(s.length()>7)
				s = s.substring(s.length()-7, s.length());
			else
				s = s+s;
			s = user.getAccount()+s;
			Order order = new Order(null, s, p, new Date(System.currentTimeMillis()), false, memo);
			System.out.println(order);
			OrderItem orderitem = new OrderItem();
			
			//如果orderitem与goods已经有双向关系，跳过
			boolean flag = false;
			List<OrderItem> orderitems = orderItemDao.getAll();
			for(OrderItem o:orderitems){
				if(o.getGoods()==null) continue;
				else if(o.getGoods().equals(goods)){
					flag = true;
					break;
				}
			}
			
			if(!flag){
				//建立双向联系
				System.out.println(flag);
				orderitem.setGoods(goods);
				goods.setOrderitem(orderitem);
				orderItemDao.saveOrUpdate(orderitem);
			}
			
			System.out.println("flag");
			order.getOrderItems().add(orderitem);
			orderitem.setOrder(order);
			orderDao.saveOrUpdate(order);
			System.out.println("orderDao");
			
			user.addOrder(order);
			order.setUser(user);
			userDao.saveOrUpdate(user);
			System.out.println("userDao");
			
			redirectAttributes.addAttribute("uid", uid);
			redirectAttributes.addAttribute("mid", mid);
			return "redirect:/orders";
		}
*/
		@RequestMapping(value="/userMessage/{uid}/merchant/{mid}/goods/buys",method=RequestMethod.GET)
		public String buyGoodss(@PathVariable("uid") Integer uid,@PathVariable("mid") Integer mid,
				Map<String, Object> map,HttpServletRequest request,HttpSession session,RedirectAttributes redirectAttributes){
			User user = userDao.get(uid);
			map.put("user", user);
			Cookie[]  cs = request.getCookies();
			Cookie c = null;
			for(int i=0;i<cs.length;i++){
				//不能用==比较，地址不同
				//if(cs[i].getName()==uid.toString()){
				if(cs[i].getName().equals(uid.toString())){
					c = cs[i];
				}
			}
			System.out.println(c);
			
			String value = c.getValue();
			String value1 = EscapeUnescape.unescape(value);
			String value2 = EscapeUnescape.unescape(value1);
			value = value2;
			String[] values = value.split(",");
			String memo = value;
			//System.out.println(memo);
			Goods goods = null;
			Double p = Double.valueOf(0);
			for(int i=0;i<values.length;i++){
				String[] temp = values[i].split(":");
				goods = goodsDao.get(Integer.parseInt(temp[0]));
				p+=goods.getPrice()*Integer.parseInt(temp[1]);
			}
			long l = System.currentTimeMillis();
			String s = Long.toString(l);
			if(s.length()>7)
				s = s.substring(s.length()-7, s.length());
			else
				s = s+s;
			s = user.getAccount()+s;
			Order order = new Order(null, s, p, new Date(System.currentTimeMillis()), false, memo);
			System.out.println(order);
			
			//如果orderitem与goods已经有双向关系，跳过
			/*boolean flag = false;
			List<OrderItem> orderitems = orderItemDao.getAll();
			for(OrderItem o:orderitems){
				if(o.getGoods()==null) continue;
				else if(o.getGoods().equals(goods)){
					flag = true;
					break;
				}
			}
			
			if(!flag){
				//建立双向联系
				System.out.println(flag);
				orderitem.setGoods(goods);
				goods.setOrderitem(orderitem);
				orderItemDao.saveOrUpdate(orderitem);
			}*/
			
			System.out.println("flag");
			for(int i=0;i<values.length;i++){
				String[] temp = values[i].split(":");
				Integer goods_id = Integer.parseInt(temp[0]);
				Goods goods1 = goodsDao.get(goods_id);
				OrderItem orderItem2 = new OrderItem(null,goods1,null);
				//orderItem2 = orderItemDao.getGoods(Integer.parseInt(temp[0]));
				order.getOrderItems().add(orderItem2);
				System.out.println(orderItem2);
				orderItem2.setOrder(order);
			}
			orderDao.saveOrUpdate(order);
			System.out.println("orderDao");
			
			user.addOrder(order);
			order.setUser(user);
			userDao.saveOrUpdate(user);
			System.out.println("userDao");
			
			redirectAttributes.addAttribute("uid", uid);
			redirectAttributes.addAttribute("mid", mid);
			return "redirect:/orders";
		}

}
