package com.mmit;

public class DI_Demo {

	public static void main(String[] args) {
		var harDisk = new Hardisk();
		//var computer = new Computer(harDisk);
		var computer = new Computer();
		computer.setHarDisk(harDisk);
		computer.execute();
	}
	

}
