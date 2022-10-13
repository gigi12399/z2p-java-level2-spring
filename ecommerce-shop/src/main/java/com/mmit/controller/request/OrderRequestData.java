package com.mmit.controller.request;

import java.util.List;

public class OrderRequestData {
	
	private OrderReceiverData receiver;
	
	private List<OrderProductData> orderItems;
	
	public List<OrderProductData> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderProductData> orderItems) {
		this.orderItems = orderItems;
	}

	public OrderReceiverData getReceiver() {
		return receiver;
	}

	public void setReceiver(OrderReceiverData receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "OrderRequestData [receiver=" + receiver + ", orderItems=" + orderItems + "]";
	}

	
	
}
