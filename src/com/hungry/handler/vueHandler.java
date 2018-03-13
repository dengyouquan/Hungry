package com.hungry.handler;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hungry.dao.GoodsDao;
import com.hungry.dao.MerchantDao;
import com.hungry.dao.OrderDao;
import com.hungry.dao.OrderItemDao;
import com.hungry.dao.UserDao;
import com.hungry.entities.Goods;
import com.hungry.entities.Merchant;
import com.hungry.entities.Order;
import com.hungry.entities.OrderItem;
import com.hungry.entities.User;

@Controller
public class vueHandler {
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
	
	@ResponseBody
	@RequestMapping("/testJson")
	public List<Goods> testJson(){
		System.out.println("testJson");
		List<Goods>  list = goodsDao.getAll();
		System.out.println(list);
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/testJsonm",method=RequestMethod.GET)
	public Collection<Goods> testJsonm(HttpServletRequest request){
		System.out.println("testJsonm"+request.getParameter("mid"));
		Integer mid = Integer.parseInt(request.getParameter("mid"));
		System.out.println("testJsonm mid="+mid);
		Collection<Goods>  list = merchantDao.get(mid).getGoods();
		System.out.println(list);
		return list;
	}
	
	/*@RequestMapping("/user")
	public User getUser(HttpServletRequest request){
		Integer id = Integer.parseInt(request.getParameter("id"));
		System.out.println("user:"+id);
		return userDao.get(id);
	}*/
	
	//旧版登录用get方式，不冲突
	@RequestMapping(value="/userlogin",method=RequestMethod.POST)
	public String userLogin(HttpServletRequest request){
	//public User userLogin(@RequestBody User u){
		String account = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("userLogin account:"+account+":"+password);
		List<User> users = userDao.getAll();
		for(User user:users){
			if(user.getAccount().equalsIgnoreCase(account) && user.getPassword().equalsIgnoreCase(password)){
				System.out.println("success");
				//request.setAttribute("id", user.getId());
				return "redirect:/index.html?id="+user.getId();
			}
			if(user.getName().equalsIgnoreCase(account) && user.getPassword().equalsIgnoreCase(password)){
				System.out.println("success");
				//request.setAttribute("id", user.getId());
				return "redirect:/index.html?id="+user.getId();
			}
		}
		System.out.println("fail");
		return "redirect:/login.html";
	}
	
	@RequestMapping(value="/merchantlogin",method=RequestMethod.POST)
	public String merchantlogin(HttpServletRequest request){
		//public User userLogin(@RequestBody User u){
			String account = request.getParameter("username");
			String password = request.getParameter("password");
			System.out.println("merchantlogin account:"+account+":"+password);
			List<Merchant> merchants = merchantDao.getAll();
			for(Merchant merchant:merchants){
				if(merchant.getAccount().equalsIgnoreCase(account) && merchant.getPassword().equalsIgnoreCase(password)){
					System.out.println("success");
					//request.setAttribute("id", user.getId());
					return "redirect:/index.html?mid="+merchant.getId();
				}
				if(merchant.getName().equalsIgnoreCase(account) && merchant.getPassword().equalsIgnoreCase(password)){
					System.out.println("success");
					//request.setAttribute("id", user.getId());
					return "redirect:/index.html?mid="+merchant.getId();
				}
			}
			System.out.println("fail");
			return "redirect:/login.html";
		}
		
	
	//购物车结算
	@RequestMapping(value="/buy",method=RequestMethod.POST)
	public String buyGoods(HttpServletRequest request){
		System.out.println("buy");
		String str = request.getParameter("item_name_1");
		String[]  sa = str.split(":");
		Integer uid = Integer.parseInt(sa[3]);
		Integer len = Integer.parseInt(sa[2]);
		System.out.println("len:"+len+" uid="+uid);
		User user = userDao.get(uid);
		
		long l = System.currentTimeMillis();
		String s = Long.toString(l);
		if(s.length()>7)
			s = s.substring(s.length()-7, s.length());
		else
			s = s+s;
		s = user.getAccount()+s;
		Order order = new Order(null, s, null, new Date(System.currentTimeMillis()), false, null);
		System.out.println(order);
		
		//Goods goods = null;
		Double p = Double.valueOf(0);
		for(int i=1;i<=len;i++){
			String oidstr = request.getParameter("item_name_"+i);
			String[]  sa1 = oidstr.split(":");
			Integer goods_id = Integer.parseInt(sa1[0]);
			Goods goods1 = goodsDao.get(goods_id);
			p+=goods1.getPrice();
			OrderItem orderItem2 = new OrderItem(null,goods1,null);
			order.getOrderItems().add(orderItem2);
			System.out.println(orderItem2);
			orderItem2.setOrder(order);
		}
		order.setAllPrice(p);
		orderDao.saveOrUpdate(order);
		
		user.addOrder(order);
		order.setUser(user);
		userDao.saveOrUpdate(user);
		
		return "redirect:/userMessage/"+user.getId()+"/orders";
		//return "redirect:/index.html?id="+user.getId();
	}

	/*@RequestMapping(value="/userregister",method=RequestMethod.POST)
	public String userRegister(Map<String,Object> map){
			map.put("user", new User());
			return "userInput";
	}
	
	@RequestMapping(value="/merchantregister",method=RequestMethod.POST)
	public String merchantregister(Map<String,Object> map){
			map.put("merchant", new Merchant());
			return "merchantInput";
	}*/
	
	@RequestMapping(value="/userregister",method=RequestMethod.POST)
	public String userRegister(HttpServletRequest request){
		String name = request.getParameter("name");
		String account = request.getParameter("account");
		String address = request.getParameter("address");
		String tel = request.getParameter("tel");
		String memo = request.getParameter("memo");
		String password = request.getParameter("password");
		Integer age = Integer.parseInt(request.getParameter("age"));
		
		try {
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(name);
			
			address = new String(address.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(address);
			
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		User user = new User(null, name, account, password, age, new Date(System.currentTimeMillis()), address, tel, memo);
		userDao.saveOrUpdate(user);
		System.out.println("user:"+user);
		return "redirect:index.html?id="+user.getId();
	}
	
	@RequestMapping(value="/merchantregister",method=RequestMethod.POST)
	public String merchantregister(HttpServletRequest request){
		String name = request.getParameter("name");
		String account = request.getParameter("account");
		String address = request.getParameter("address");
		String tel = request.getParameter("tel");
		String memo = request.getParameter("memo");
		String password = request.getParameter("password");
		
		try {
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(name);
			
			address = new String(address.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(address);
			
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Merchant merchant = new Merchant(null, name,account,password, new Date(System.currentTimeMillis()), address, tel, memo);
		merchantDao.saveOrUpdate(merchant);
		System.out.println("merchant:"+merchant);
		return "redirect:index.html?mid="+merchant.getId();
	}

	@RequestMapping(value="/addgoods",method=RequestMethod.POST)
	public String addgoods(HttpServletRequest request) throws IllegalStateException, IOException{
		System.out.println("addgoods");
		String name = request.getParameter("name");
		System.out.println("name:"+name);
		Double price = Double.parseDouble(request.getParameter("price"));
		System.out.println("price:"+price);
		String memo = request.getParameter("memo");
		System.out.println("memo:"+memo);
		String mid = request.getParameter("mid");
		System.out.println("mid:"+mid);
		/*try {
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(name);
			
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
			
			image = new String(image.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(image);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		String path = null;
		//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            
            MultipartFile file = multiRequest.getFile("image");
           
           long l = System.currentTimeMillis();
			String s = Long.toString(l);
			if(s.length()>7)
				s = s.substring(s.length()-7, s.length());
			else
				s = s+s;
            //path="/images/"+s+"-"+file.getOriginalFilename();
            path=multiRequest.getSession().getServletContext().getRealPath("/images/")+s+"-"+file.getOriginalFilename();
            System.out.println("path:"+path);
            //上传
            file.transferTo(new File(path));
            path = path.split("Hungry3.2")[1];
            //path = path.substring(1, path.length());
            path = path.replace("\\","/");
            path = path.substring(1, path.length());
            System.out.println("path:"+path);
           /*//获取multiRequest 中所有的文件名
            Iterator<String> iter=multiRequest.getFileNames();
             
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    String path="E:/springUpload"+file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                }
                 
            }*/
           
        }
		
		Goods goods = new Goods(null, name, price, new Date(System.currentTimeMillis()), memo,path);
		goodsDao.saveOrUpdate(goods);
		Merchant merchant = merchantDao.get(Integer.parseInt(mid));
		merchant.addGoods(goods);
		goods.setMerchant(merchant);
		merchantDao.saveOrUpdate(merchant);
		System.out.println("goods:"+goods);
		return "redirect:index.html?mid="+mid;
	}
	
	
	/*@RequestMapping(value="/addgoods",method=RequestMethod.POST)
	public String addgoods(HttpServletRequest request){
		String name = request.getParameter("name");
		Double price = Double.parseDouble(request.getParameter("price"));
		String memo = request.getParameter("memo");
		String image = request.getParameter("image");
		String mid = request.getParameter("mid");
		try {
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(name);
			
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
			
			image = new String(image.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(image);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Goods goods = new Goods(null, name, price, new Date(System.currentTimeMillis()), memo,image);
		goodsDao.saveOrUpdate(goods);
		Merchant merchant = merchantDao.get(Integer.parseInt(mid));
		merchant.addGoods(goods);
		goods.setMerchant(merchant);
		merchantDao.saveOrUpdate(merchant);
		System.out.println("goods:"+goods);
		return "redirect:index.html?mid="+mid;
	}*/
	
}
