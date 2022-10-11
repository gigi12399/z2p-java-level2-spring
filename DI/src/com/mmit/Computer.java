package com.mmit;

public class Computer {
	// private Hardisk harDisk = Hardisk();
	private Hardisk harDisk;
	
	public Computer(Hardisk hardisk) { // inject via constructor
		this.harDisk = hardisk;
	}
	public Computer() {
		
	}
	 
	
	public void setHarDisk(Hardisk harDisk) { // inject via setter
		this.harDisk = harDisk;
	}
	public void execute() {
		harDisk.storage();
		System.out.println("execute method");
	}
	
}
