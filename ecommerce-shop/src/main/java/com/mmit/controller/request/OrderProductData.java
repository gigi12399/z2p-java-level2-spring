package com.mmit.controller.request;

public class OrderProductData {
	private int productId;
	private int qty;
	private int sub_total;
	
	public int getSub_total() {
		return sub_total;
	}
	public void setSub_total(int sub_total) {
		this.sub_total = sub_total;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	@Override
	public String toString() {
		return "OrderProductData [productId=" + productId + ", qty=" + qty + ", sub_total=" + sub_total + "]";
	}
	
	
}
