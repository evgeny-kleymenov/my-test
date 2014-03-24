package com.evgeny.exercises.mytest;

import java.util.Arrays;
import java.util.Random;

public class MyTest implements Runnable {
	private final int n;
	private final int maxrnd;
	private int sum = -1;
	/**
	 * Limit for n
	 */
	public static final int nmax = 2000;
	/**
	 * Limit for maxrnd
	 */
	public static final int mmaxrnd = 1000;

	/**
	 * Constructor
	 * @param n [1,nmax]
	 * @param maxrnd [1,mmaxrnd]
	 */
	public MyTest(int n, int maxrnd){
		if(n<=0 || n>nmax){
			throw(new IllegalArgumentException("argument n out of range [1,"+nmax+"]: "+n));
		}
		if(	maxrnd <=0 || maxrnd>1000){
			throw(new IllegalArgumentException("argument maxrnd out of range [1,"+mmaxrnd+"]: "+maxrnd));
		}
		// constructor
		this.n = n;
		this.maxrnd = maxrnd;
	}
	
	public synchronized void close(){
		// destructor
		// nothing to do
	}

	@Override
	public synchronized void run() {
		int[] a = new int[n];
		Random rnd = new Random();
		
		for(int i=0;i<n;i++){
			a[i] = 1+rnd.nextInt(maxrnd);
		}
		
		Arrays.sort(a);
		sum = 0;
		for(int i=0;i<n-1;i += 2){
			sum += a[i]*a[i+1];
		}
	}
	
	public synchronized int getSum(){
		return sum;
	}
}
