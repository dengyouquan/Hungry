package com.hungry.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hungry.entities.Merchant;


@Repository
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class) 
public class MerchantDao extends BaseDao {
	@SuppressWarnings("unchecked") 
	public List<Merchant> getAll(){
		String hql = "from Merchant";
		return this.getSession().createQuery(hql).list();
	}
	
	public Merchant get(Integer id){
		return (Merchant)this.getSession().get(Merchant.class, id);
	}
	
	public void delete(Integer id){
		String hql = "delete from Merchant m where id=?";
		this.getSession().createQuery(hql).setInteger(0, id).executeUpdate();
	}
	
	public void saveOrUpdate(Merchant merchant){
		this.getSession().saveOrUpdate(merchant);
		this.getSession().flush();
	}
}
