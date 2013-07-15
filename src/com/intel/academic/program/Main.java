package com.intel.academic.program;

public class Main {

	public static void main(String[] args) {
		try {
			Server server = new Server(8800, 8887,
					8801, 8888);
			server.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
