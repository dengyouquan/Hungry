package com.hungry.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hungry.entities.Goods;


@Repository
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class) 
public class GoodsDao extends BaseDao {
	@SuppressWarnings("unchecked")
	public List<Goods> getAll(){ 
		String hql = "from Goods";
		return this.getSession().createQuery(hql).list();
	}
	
	public Goods get(Integer id){
		return (Goods)this.getSession().get(Goods.class, id);
	}
	
	public void delete(Integer id){
		String hql = "delete from Goods g where id=?";
		this.getSession().createQuery(hql).setInteger(0, id).executeUpdate();
	}
	
	public void saveOrUpdate(Goods goods){
		this.getSession().saveOrUpdate(goods);
		this.getSession().flush();
	}
}
