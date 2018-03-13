package com.hungry.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.hungry.dao.OrderDao;
import com.hungry.dao.UserDao;
import com.hungry.entities.User;

@Controller
public class UserHandler {
	@Autowired
	private UserDao userDao;
	
	//show all users
	@RequestMapping(value="/users")
	public String userList(Map<String,Object> map){
		List<User> users = userDao.getAll();
		map.put("users", users);
		System.out.println("userList");
		return "userList";
	}
	
	@RequestMapping(value="/user",method=RequestMethod.GET)
	public String userInput(Map<String,Object> map){
		System.out.println("userInput");
		map.put("user", new User());
		return "userInput";
	}
	
	//add user
	@RequestMapping(value="/user",method=RequestMethod.POST)
	public String save(@Valid User user,BindingResult result,Map<String, Object> map,
			@RequestParam("name") String name,@RequestParam("address") String address,
			@RequestParam("memo") String memo){
		try {
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(name);
			user.setName(name);
			
			address = new String(address.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(address);
			user.setAddress(address);
			
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
			user.setMemo(memo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Date d = new Date(System.currentTimeMillis());
		user.setCreateTime(d);
		if(result.getErrorCount()>0){
			for(FieldError error : result.getFieldErrors()){
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			return "userInput";
		}else{
			System.out.println("save user:"+user);
			userDao.saveOrUpdate(user);
		}
		return "redirect:/users";
	}
	
	
	@RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id){
		userDao.delete(id);
		return "redirect:/users";
	}
	
	@RequestMapping(value="/user/{id}",method=RequestMethod.GET)
	public String input(@PathVariable("id") Integer id,Map<String, Object> map){
		map.put("user", userDao.get(id));
		System.out.println("edit userInput");
		return "userInput";
	}
	
	@ModelAttribute
	public void getUser(@RequestParam(value="id",required=false) Integer id, Map<String, Object> map){
		if(id!=null){
			map.put("user", userDao.get(id));
			System.out.println("@ModelAttribute+DB:"+userDao.get(id));
		}
	}
	@RequestMapping(value="/user",method=RequestMethod.PUT)
	public String update(@Valid User user,
			@RequestParam("address") String address,@RequestParam("memo") String memo){
		try {
			address = new String(address.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(address);
			user.setAddress(address);
			
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
			user.setMemo(memo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("update:"+user);
		userDao.saveOrUpdate(user);
		return "redirect:/users";
	}
}
