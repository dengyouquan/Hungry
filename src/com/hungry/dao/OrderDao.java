package com.hungry.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hungry.entities.Order;

@Repository
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class) 
public class OrderDao extends BaseDao {
	@SuppressWarnings("unchecked")
	public List<Order> getAll(){
		String hql = "from Order"; 
		return this.getSession().createQuery(hql).list();
	}
	
	public Order get(Integer id){
		return (Order)this.getSession().get(Order.class, id);
	}
	
	public void delete(Integer id){
		String hql = "delete from Order o where id=?";
		this.getSession().createQuery(hql).setInteger(0, id).executeUpdate();
		/*this.getSession().cancelQuery();
		this.getSession().close();*/
	}
	
	public void saveOrUpdate(Order order){
		this.getSession().saveOrUpdate(order);
		this.getSession().flush();
	}
}
