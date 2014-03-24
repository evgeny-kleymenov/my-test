package com.evgeny.exercises.mytest;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int n = 1000;
		int m = 100;
		
		Thread[] t = new Thread[n];
		MyTest[] mt = new MyTest[n];
		
		for(int i=0;i<n;i++){
			mt[i] = new MyTest(i+1, m);
			t[i] = new Thread(mt[i]);
			t[i].start();
		}
		
		for(int i=0;i<n;i++){
			try {
				t[i].join();
				int sum = mt[i].getSum();
				System.out.println("n = "+(i+1)+", sum = "+sum);
			} catch (InterruptedException e) {
				System.out.println("n = "+(i+1)+", cannot calculate sum: "+e.toString());
			}
		}
		System.out.println("***** done *****");
	}

}
