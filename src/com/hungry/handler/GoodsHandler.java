package com.hungry.handler;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.hungry.entities.Goods;

@Controller
public class GoodsHandler {
	@Autowired
	private GoodsDao goodsDao;
	
	//show all goods
	@RequestMapping(value="/goodss",method=RequestMethod.GET)
	public String goodList(Map<String, Object> map){
		List<Goods> goodss = goodsDao.getAll();
		map.put("goodss", goodss);
		System.out.println("getall goodsList");
		return "goodsList";
	}
	
	//add a good for input
	@RequestMapping(value="/goods",method=RequestMethod.GET)
	public String goodInput(Map<String, Object> map){
		map.put("goods", new Goods());
		System.out.println("add goodsInput");
		return "goodsInput";
	}
	//add a good
	@RequestMapping(value="/goods",method=RequestMethod.POST)
	public String save(@Valid Goods goods,BindingResult result,
			@RequestParam("name") String name,@RequestParam("memo") String memo){
		try {
			name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(name);
			goods.setName(name);
			
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
			goods.setMemo(memo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(result.getErrorCount()>0){
			for(FieldError error:result.getFieldErrors()){
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			return "goodsInput";
		}else{
			goods.setPutTime(new Date(System.currentTimeMillis()));
			System.out.println("save:"+goods);
			goodsDao.saveOrUpdate(goods);
		}
		return "redirect:/goodss";
	}
	
	//delete a good
	@RequestMapping(value="/goods/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id){
		goodsDao.delete(id);
		System.out.println("delete:"+id);
		return "redirect:/goodss";
	}
	
	//update a good read from database
	@ModelAttribute
	public void getGoods(@RequestParam(name="id",required=false) Integer id,Map<String, Object> map){
		if(id!=null){
			map.put("goods", goodsDao.get(id));
			System.out.println("@ModelAttribute D1B:"+goodsDao.get(id));
		}
	}
	//update a good for input
	@RequestMapping(value="/goods/{id}",method=RequestMethod.GET)
	public String goodInput(@PathVariable("id") Integer id,Map<String, Object> map){
		map.put("goods", goodsDao.get(id));
		System.out.println("update1 goodsInput");
		return "goodsInput";
	}
	//update a good 
	@RequestMapping(value="/goods",method=RequestMethod.PUT)
	public String update(@Valid Goods goods,
			@RequestParam("memo") String memo){
		System.out.println("update");
		try {
			memo = new String(memo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(memo);
			goods.setMemo(memo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		goodsDao.saveOrUpdate(goods);
		System.out.println("udpate:"+goods);
		return "redirect:/goodss";
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	dateFormat.setLenient(false);
	binder.registerCustomEditor(Date.class, new CustomDateEditor(
	dateFormat, true)); // true:允许输入空值，false:不能为空值
	}
}
