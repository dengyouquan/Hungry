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
import com.hungry.dao.OrderItemDao;
import com.hungry.entities.Goods;
import com.hungry.entities.Merchant;
import com.hungry.entities.OrderItem;
import com.hungry.entities.User;

@Controller
public class MerchantManagerHandler {
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderItemDao orderItemDao;
	
	//merchant login
	@RequestMapping(value="/merchantlogin")
	public String merchantLogin(Map<String,Object> map,@RequestParam("account") String account,
			@RequestParam("password") String password,
			HttpServletRequest request,HttpServletResponse response,HttpSession session){
		List<Merchant> merchants = merchantDao.getAll();
		for(Merchant merchant:merchants){
			System.out.println("bijiao");
			System.out.println("merchant:"+merchant);
			if(merchant.getAccount()==null || merchant.getName()==null || merchant.getPassword()==null){
				request.setAttribute("login", "empty");
			}
				
			else if(merchant.getAccount().equalsIgnoreCase(account) && merchant.getPassword().equalsIgnoreCase(password)){
				//map.put("login", "success");
				request.setAttribute("login", "success");
				request.setAttribute("id", merchant.getId());
				//session.setAttribute("login", "success");
				System.out.println("success");
				return "merchantlogin";
			}
			else if(merchant.getName().equalsIgnoreCase(account) && merchant.getPassword().equalsIgnoreCase(password)){
				//map.put("login", "success");
				request.setAttribute("login", "success");
				request.setAttribute("id", merchant.getId());
				//session.setAttribute("login", "success");
				System.out.println("success");
				return "merchantlogin";
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
	
	//show merchant message
	@RequestMapping(value="/merchantMessage/{id}",method=RequestMethod.GET)
	public String merchantMessage(@PathVariable("id") Integer id,Map<String, Object> map){
		map.put("merchant", merchantDao.get(id));
		System.out.println("merchantMessage");
		return "merchantMessage";
	}
	
	//show all goods
	@RequestMapping(value="/merchantMessage/{id}/goodss",method=RequestMethod.GET)
	public String showGoodss(@PathVariable("id") Integer id,Map<String, Object> map){
		System.out.println("merchantMassage");
		Merchant merchant = merchantDao.get(id);
		map.put("merchant", merchant);
		map.put("goodss", merchant.getGoods());
		map.put("flag1", "merchant");
		System.out.println("merchantMessage showGoodss");
		return "mgoodsList";
	}
	
	//add a goods for input
		@RequestMapping(value="/merchantMessage/{id}/goods",method=RequestMethod.GET)
		public String goodInput(Map<String, Object> map,@PathVariable("id") Integer id){
			map.put("goods", new Goods());
			Merchant merchant = merchantDao.get(id);
			map.put("merchant", merchant);
			System.out.println("merchantMessage add goodsInput");
			return "mgoodsInput";
		}
		//add a goods 
		//增加goods时同时增加OrderItem，便于管理
		@RequestMapping(value="/merchantMessage/{id}/goods",method=RequestMethod.POST)
		public String save(@Valid Goods goods,BindingResult result,
				@RequestParam("name") String name,@RequestParam("memo") String memo,
				@PathVariable("id") Integer id){
			try {
				name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
				goods.setName(name);
				
				memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
				goods.setMemo(memo);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if(result.getErrorCount()>0){
				for(FieldError error:result.getFieldErrors()){
					System.out.println(error.getField()+":"+error.getDefaultMessage());
				}
				return "mgoodsInput";
			}else{
				goods.setPutTime(new Date(System.currentTimeMillis()));
				goods.setId(null);
				System.out.println("merchantMessage save:"+goods);
				goodsDao.saveOrUpdate(goods);
				Merchant merchant = merchantDao.get(id);
				//建立双向联系
				merchant.addGoods(goods);
				goods.setMerchant(merchant);
				System.out.println("merchantMessage save:"+merchant);
				merchantDao.saveOrUpdate(merchant);
				
				OrderItem orderItem = new OrderItem();
				orderItem.setGoods(goods);
				goods.getOrderItems().add(orderItem);
				orderItemDao.saveOrUpdate(orderItem);
			}
			String s = "redirect:/merchantMessage/"+id+"/goodss";
			System.out.println(s);
			return s;
		}
		
		//delete a good
		@RequestMapping(value="/merchantMessage/{mid}/goods/{id}",method=RequestMethod.DELETE)
		public String delete(@PathVariable("mid") Integer mid,@PathVariable("id") Integer id){
			goodsDao.delete(id);
			System.out.println("delete:"+id);
			return "redirect:/merchantMessage/"+mid+"/goodss";
		}
		
		//update a good read from database
		@ModelAttribute
		public void getGoods(@RequestParam(name="id",required=false) Integer id,Map<String, Object> map){
			if(id!=null){
				map.put("goods", goodsDao.get(id));
				System.out.println("merchantMessage @ModelAttribute DB:"+goodsDao.get(id));
			}
		}
		//update a good for input
		@RequestMapping(value="/merchantMessage/{mid}/goods/{id}",method=RequestMethod.GET)
		public String goodInput(@PathVariable("id") Integer id,@PathVariable("mid") Integer mid,
				Map<String, Object> map){
			map.put("goods", goodsDao.get(id));
			map.put("merchant", merchantDao.get(mid));
			System.out.println("merchantMessage update1 goodsInput");
			return "mgoodsInput";
		}
		//update a good 
		@RequestMapping(value="/merchantMessage/{id}/goods",method=RequestMethod.PUT)
		public String update(@Valid Goods goods,
				@RequestParam("memo") String memo,
				@PathVariable("id") Integer id){
			System.out.println("update");
			try {
				memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
				System.out.println(memo);
				goods.setMemo(memo);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			goodsDao.saveOrUpdate(goods);
			System.out.println("merchantMessage udpate:"+goods);
			String s = "redirect:/merchantMessage/"+id+"/goodss";
			System.out.println(s);
			return s;
		}
		
		@InitBinder
		public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
		dateFormat, true)); // true:允许输入空值，false:不能为空值
		}
		
		//show all merchant for buy
		@RequestMapping(value="/merchantMessage/{mid}/query",method=RequestMethod.GET)
		public String buyMerchantsList(@PathVariable("mid") Integer mid,Map<String, Object> map){
			map.put("merchant", merchantDao.get(mid));
			map.put("merchants", merchantDao.getAll());
			map.put("goodss", goodsDao.getAll());
			map.put("flag", "merchant");
			System.out.println("buyMerchantList show orders");
			return "buyMerchantList";
		}
		
		@RequestMapping(value="/merchantMessage/{uid}/merchant/{mid}/goodss",method=RequestMethod.GET)
		public String buyGoodsList(@PathVariable("uid") Integer uid,@PathVariable("mid") Integer mid,Map<String, Object> map){
			map.put("merchant", merchantDao.get(mid));
			map.put("goodss", merchantDao.get(mid).getGoods());
			map.put("flag", "merchant");
			System.out.println("buyGoodsList show orders");
			return "buyGoodsList";
		}
}
