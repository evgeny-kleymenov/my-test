package com.evgeny.exercises.mytest;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class MyTestTest {

    @Rule
    public Timeout globalTimeout = new Timeout(10000); // 10 seconds max per method tested
	
	@Test
	public void testConstructor() {
		// test object creation
		int nmax = MyTest.nmax;
		int mmaxrnd = MyTest.mmaxrnd;
		
		assertTrue("positive nmax", nmax>0);
		assertTrue("positive mmaxrnd", mmaxrnd>0);
		assertTrue("mmaxrnd not too small", mmaxrnd>2);
		assertTrue("nmax not too small", nmax>2);
		
		// index out of range
		int[][] pars = {{0,1},{-1,1},{1,-1},{1,0},{nmax+1,1},{1,mmaxrnd+1}};
		for (int[] par : pars) {
			try {
				new MyTest(par[0], par[1]);
				fail("IllegalArgumentException expected but not thrown: n="+par[0]+", maxrand="+par[1]);
			} catch (IllegalArgumentException ex) {
//				assertTrue("IllegalArgumentException thrown: n="+par[0]+", maxrand="+par[1], true);
			}
		}
		
		int[][] pars2 = {{1,1},{nmax,1},{1,mmaxrnd},{nmax,mmaxrnd}};
		for (int[] par : pars2) {
			new MyTest(par[0], par[1]);
		}
	}


	@Test
	public void testMultiThreading() {
		
		int nmax = MyTest.nmax;
		int mmaxrnd = MyTest.mmaxrnd;
		
		assertTrue("positive nmax", nmax>0);
		assertTrue("positive mmaxrnd", mmaxrnd>0);
		int sMin = nmax/2;
		// check that Integer.MAX_VALUE >= sMin*mmaxrnd*mmaxrnd
		assertTrue("mmaxrnd and nmax limit (nmax="+nmax+", mmaxrnd="+mmaxrnd+")",Integer.MAX_VALUE/sMin/mmaxrnd/mmaxrnd>0);
		
	
		Thread[] t = new Thread[nmax];
		MyTest[] mt = new MyTest[nmax];
		
		for(int i=0;i<nmax;i++){
			mt[i] = new MyTest(i+1, mmaxrnd);
			t[i] = new Thread(mt[i]);
		}
		
		for(int i=0;i<nmax;i++){
			t[i].start();
		}
		
		for(int i=0;i<nmax;i++){
			try {
				t[i].join();
			} catch (InterruptedException e) {
				fail("thread interrupted (n="+(i+1)+", maxrnd="+mmaxrnd+"): "+e.toString());
			}
		}
		
		for(int i=0;i<nmax;i++){
			assertTrue("non-negative getSum()", mt[i].getSum()>=0);
			int sumMin = (i+1)/2;
			int sumMax = sumMin*mmaxrnd*mmaxrnd;
			assertTrue("getSum limit ["+sumMin+", "+sumMax+"] : "+
					mt[i].getSum(),
					mt[i].getSum()>=sumMin && 
					mt[i].getSum()<=sumMax);
		}
	}
}
