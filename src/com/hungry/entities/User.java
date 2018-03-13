package com.hungry.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name="t_user")
public class User { 
	private Integer id; 
	@NotEmpty
	private String name;
	@NotEmpty
	private String account;
	@NotEmpty
	private String password;
	@Max(100)
	@Min(0)
	private Integer age;
	private Date createTime;
	@NotEmpty
	private String address;
	@NotEmpty
	private String tel;
	private String memo;
	private Set<Order> orders = new HashSet<Order>();
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Column(name="create_time")
	//@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void addOrder(Order order){
		this.getOrders().add(order);
	}
	public User(Integer id, String name, String account, String password, Integer age, Date createTime, String address,
			String tel, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.account = account;
		this.password = password;
		this.age = age;
		this.createTime = createTime;
		this.address = address;
		this.tel = tel;
		this.memo = memo;
	}
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", account=" + account + ", password=" + password + ", age=" + age
				+ ", createTime=" + createTime + ", address=" + address + ", tel=" + tel + ", memo=" + memo
				+ ", orders=" + orders + "]";
	}
}
