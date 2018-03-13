package com.hungry.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hungry.entities.OrderItem;

@Repository
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class) 
public class OrderItemDao extends BaseDao {
	@SuppressWarnings("unchecked")
	public List<OrderItem> getAll(){
		String hql = "from OrderItem"; 
		return this.getSession().createQuery(hql).list();
	}
	
	public OrderItem get(Integer id){
		return (OrderItem)this.getSession().get(OrderItem.class, id);
	}
	
	/*public OrderItem getGoods(Integer id){
		String hql = "from OrderItem o where goods_id=?";
		List<OrderItem> os =  this.getSession().createQuery(hql).setInteger(0, id).list();
		return os.get(0);
	}*/
	
	public void delete(Integer id){
		String hql = "delete from OrderItem o where id=?";
		this.getSession().createQuery(hql).setInteger(0, id).executeUpdate();
		/*this.getSession().cancelQuery();
		this.getSession().close();*/
	}
	 
	public void saveOrUpdate(OrderItem orderItem){
		this.getSession().saveOrUpdate(orderItem);
		this.getSession().flush();
	}
}
