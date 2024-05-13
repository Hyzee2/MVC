package net.basket.db;

import java.sql.Date;

public class BasketBean {
	private String id; // 사용자 아이디  
	private String item_name; // 상품명
	private String item_price; // 상품가격 
	private String item_total; // 상품 구매 수량
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_price() {
		return item_price;
	}
	public void setItem_price(String item_price) {
		this.item_price = item_price;
	}
	public String getItem_total() {
		return item_total;
	}
	public void setItem_total(String item_total) {
		this.item_total = item_total;
	}
	@Override
	public String toString() {
		return "BasketBean [id=" + id + ", item_name=" + item_name + ", item_price=" + item_price + ", item_total="
				+ item_total + "]";
	}
	
	
	

}
