package com.junit4;

import java.util.HashMap;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class Listener extends RunListener {

	public static long begin_mill;
	public static long end_mill;
	public static long test_begin_mill;
	public static long test_end_mill;
	private HashMap<String, Boolean> test_map;
	private HashMap<String, String> test_map_doc;
	private HashMap<String, Failure> test_map_error;
	
	public Listener() {
		System.out.println("~~~~~~~~~listener~~~~~~~~~~~~~");
	}
	
	public Listener(HashMap<String, Boolean> test_map,
			HashMap<String, String> test_map_doc,
			HashMap<String, Failure> test_map_error
			) {
		this.test_map = test_map;
		this.test_map_doc = test_map_doc;
		this.test_map_error = test_map_error;
	}

	@Override
	public void testAssumptionFailure(Failure failure) {
		// TODO Auto-generated method stub
		super.testAssumptionFailure(failure);
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
		// TODO Auto-generated method stub
		super.testFailure(failure);
		Description description = failure.getDescription();
		TestDescription text_doc = description.getAnnotation(TestDescription.class);
		String name = description.getMethodName();
//		System.out.println(this +" is failure");
		this.test_map.put(name, false);
//		System.out.println("test " + name + "( " + text_doc.value() + " )" +" is failure");
		this.test_map_error.put(name, failure);
	}

	@Override
	public void testFinished(Description description) throws Exception {
		// TODO Auto-generated method stub
		super.testFinished(description);
		String name = description.getMethodName();
		TestDescription text_doc = description.getAnnotation(TestDescription.class);
		
		Boolean value = this.test_map.get(name);
		if(value == null){
			this.test_map.put(name, true);
		}
//		System.out.println(this +" is finished");
//		System.out.println("test " + name + "( " + text_doc.value() + " )" +" is finished");
		String doc = text_doc != null ? text_doc.value():name;
		this.test_map_doc.put(name, doc);
	}

	@Override
	public void testIgnored(Description description) throws Exception {
		// TODO Auto-generated method stub
		super.testIgnored(description);
	}

	@Override
	public void testRunFinished(Result result) throws Exception {
		// TODO Auto-generated method stub
		super.testRunFinished(result);
//		System.out.println("all time " + (end_mill - begin_mill));
	}

	@Override
	public void testRunStarted(Description description) throws Exception {
		// TODO Auto-generated method stub
		super.testRunStarted(description);
		begin_mill = System.currentTimeMillis();
	}

	@Override
	public void testStarted(Description description) throws Exception {
		// TODO Auto-generated method stub
		super.testStarted(description);
		String name = description.getMethodName();
//		System.out.println("test " + name + " is started");
	}
	
}
