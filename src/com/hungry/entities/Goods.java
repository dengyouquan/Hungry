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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="t_goods") 
public class Goods {
	private Integer id;
	@NotEmpty
	private String name;
	@NotNull
	private Double price;
	private Date putTime;
	private String memo;
	private Merchant merchant;
	private String image;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@JsonBackReference
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();
	//private OrderItem orderitem;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="merchant_id")
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="put_time")
	public Date getPutTime() {
		return putTime;
	}
	public void setPutTime(Date putTime) {
		this.putTime = putTime;
	}
	/*@OneToOne(mappedBy="goods")
	public OrderItem getOrderitem() {
		return orderitem;
	}
	public void setOrderitem(OrderItem orderitem) {
		this.orderitem = orderitem;
	}*/
	@OneToMany(mappedBy="goods",cascade=CascadeType.ALL)
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public Goods(Integer id, String name, Double price, Date putTime, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.putTime = putTime;
		this.memo = memo;
	}
	
	public Goods(Integer id, String name, Double price, Date putTime, String memo, Merchant merchant, String image) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.putTime = putTime;
		this.memo = memo;
		this.merchant = merchant;
		this.image = image;
	}
	
	
	
	public Goods(Integer id, String name, Double price, Date putTime, String memo, String image) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.putTime = putTime;
		this.memo = memo;
		this.image = image;
	}
	public Goods() {
		super();
	}
	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", price=" + price + ", putTime=" + putTime + ", memo=" + memo
				+  "]";
	}
}
