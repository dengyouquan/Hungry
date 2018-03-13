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

import com.hungry.dao.MerchantDao;
import com.hungry.entities.Merchant;

@Controller
public class MerchantHandler {
	@Autowired
	private MerchantDao merchantDao;
	
	//show all merchants
	@RequestMapping(value="/merchants",method=RequestMethod.GET)
	public String merchantList(Map<String, Object> map){
		List<Merchant> merchants = merchantDao.getAll();
		map.put("merchants", merchants);
		System.out.println("getall merchantList");
		return "merchantList";
	}
	
	
	//add a merchant for input
	@RequestMapping(value="/merchant",method=RequestMethod.GET)
	public String merchantInput(Map<String, Object> map){
		map.put("merchant", new Merchant());
		System.out.println("add merchantInput");
		return "merchantInput";
	}
	//add a merchant
	@RequestMapping(value="/merchant",method=RequestMethod.POST)
	public String save(@Valid Merchant merchant,BindingResult result,
			@RequestParam("name") String name,@RequestParam("address") String address,
			@RequestParam("memo") String memo){
		try {
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(name);
			merchant.setName(name);
			
			address = new String(address.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(address);
			merchant.setAddress(address);
			
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
			merchant.setMemo(memo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		merchant.setLoginTime(new Date(System.currentTimeMillis()));
		if(result.getErrorCount()>0){
			for(FieldError error:result.getFieldErrors()){
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			return "merchantInput";
		}
		else{
			System.out.println("save£º"+merchant);
			merchantDao.saveOrUpdate(merchant);
			//System.out.println("save£º"+merchant);
		}
		return "redirect:/merchants";
	}
	
	//delete a merchant
	@RequestMapping(value="/merchant/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id){
		merchantDao.delete(id);
		System.out.println("delete:"+id);
		return "redirect:/merchants";
	}
	
	
	//update a merchant read from database
	@ModelAttribute
	public void getMerchant(@RequestParam(name="id",required=false) Integer id,Map<String , Object> map){
		if(id!=null){
			map.put("merchant", merchantDao.get(id));
			System.out.println("@ModelAttribute+DB:"+merchantDao.get(id));
		}
	}
	//update a merchant for input
	@RequestMapping(value="/merchant/{id}",method=RequestMethod.GET)
	public String merchantInput(@PathVariable("id") Integer id,Map<String, Object> map){
		map.put("merchant", merchantDao.get(id));
		System.out.println("update merchantInput");
		return "merchantInput";
	}
	//update a merchant
	@RequestMapping(value="/merchant",method=RequestMethod.PUT)
	public String update(@Valid Merchant merchant,
			@RequestParam("address") String address,
			@RequestParam("memo") String memo){
		try {
			address = new String(address.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(address);
			merchant.setAddress(address);
			
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
			merchant.setMemo(memo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		merchantDao.saveOrUpdate(merchant);
		System.out.println("update merchant:"+merchant);
		return "redirect:/merchants";
	}
	
}
