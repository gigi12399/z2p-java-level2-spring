package com.mmit.controller.request;

import java.io.Serializable;

public class calculateTotalData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int total;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
