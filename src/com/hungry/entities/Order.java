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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.sun.xml.internal.bind.v2.runtime.Name;

@Entity
@Table(name="t_order")
public class Order {
	private Integer id; 
	@NotEmpty
	private String number;
	// HV000030: No validator could be found for type: java.lang.Double.
	//改@NotEmpty为@NotNull
	@NotNull
	private Double allPrice;
	private Date startTime;
	//true 已送 false 未送
	//默认false
	private Boolean status;
	private String memo;
	private User user;
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Column(name="all_price")
	public Double getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(Double allPrice) {
		this.allPrice = allPrice;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="start_time")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@OneToMany(mappedBy="order",cascade=CascadeType.ALL)
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", number=" + number + ", allPrice=" + allPrice + ", startTime=" + startTime
				+ ", status=" + status + ", memo=" + memo + "]";
	}
	public Order(Integer id, String number, Double allPrice, Date startTime, Boolean status, String memo) {
		super();
		this.id = id;
		this.number = number;
		this.allPrice = allPrice;
		this.startTime = startTime;
		this.status = status;
		this.memo = memo;
	}
	
	public Order() {
		super();
	}
	
}
