package com.hungry.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hungry.entities.User;

@Repository
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class) 
public class UserDao extends BaseDao {
	@SuppressWarnings("unchecked") 
	public List<User> getAll(){
		String hql = "from User";
		return this.getSession().createQuery(hql).list();
	}
	
	public User get(Integer id){
		return (User)this.getSession().get(User.class, id);
	}
	
	public void delete(Integer id){
		String hql = "delete from User u where id=?";
		this.getSession().createQuery(hql).setInteger(0, id).executeUpdate();
	}
	
	public void saveOrUpdate(User user){
		this.getSession().saveOrUpdate(user);
		this.getSession().flush();
	}
}
