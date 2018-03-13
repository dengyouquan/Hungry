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

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="t_merchant")
public class Merchant {
	private Integer id; 
	@NotEmpty
	private String name;
	@NotEmpty
	private String account;
	@NotEmpty
	private String password;
	private Date loginTime;
	@NotEmpty
	private String address;
	@NotEmpty
	private String tel;
	private String memo;
	private Set<Goods> goods = new HashSet<Goods>();
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
	@OneToMany(mappedBy="merchant",cascade=CascadeType.ALL)
	public Set<Goods> getGoods() {
		return goods;
	}
	public void setGoods(Set<Goods> goods) {
		this.goods = goods;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="login_time")
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
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
	public void addGoods(Goods goods){
		this.getGoods().add(goods);
	}
	public Merchant(Integer id, String name, Date loginTime, String address, String tel, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.loginTime = loginTime;
		this.address = address;
		this.tel = tel;
		this.memo = memo;
	}
	
	public Merchant(Integer id, String name, String account, String password, Date loginTime, String address,
			String tel, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.account = account;
		this.password = password;
		this.loginTime = loginTime;
		this.address = address;
		this.tel = tel;
		this.memo = memo;
	}
	public Merchant() {
		super();
	}
	
	@Override
	public String toString() {
		return "Merchant [id=" + id + ", name=" + name + ", account=" + account + ", password=" + password
				+ ", loginTime=" + loginTime + ", address=" + address + ", tel=" + tel + ", memo=" + memo + ", goods="
				+ goods + "]";
	}
	
}
