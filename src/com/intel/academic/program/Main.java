package com.intel.academic.program;

public class Main {

	public static void main(String[] args) {
		if(args.length != 4){
			System.out.println("Wrong number of parameters. The parameters must be :");
			System.out.println("\t-Port command drone");
			System.out.println("\t-Port command remote");
			System.out.println("\t-Port video drone");
			System.out.println("\t-Port video remote");
			System.out.println("For exemple : nameOfYourJar 8800 8887 8801 8888");


		}else{
			try {
				//			Server server = new Server(8800, 8887,
				//					8801, 8888);
				Server server = new Server(Integer.parseInt(args[0]),
						Integer.parseInt(args[1]),
						Integer.parseInt(args[2]),
						Integer.parseInt(args[3]));
				server.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
