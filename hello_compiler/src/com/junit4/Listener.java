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
	public static HashMap<String, Boolean> test_map;
	public static HashMap<String, String> test_map_doc;
	public static HashMap<String, Failure> test_map_error;

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
		test_map.put(name, false);
//		System.out.println("test " + name + "( " + text_doc.value() + " )" +" is failure");
		test_map_error.put(name, failure);
	}

	@Override
	public void testFinished(Description description) throws Exception {
		// TODO Auto-generated method stub
		super.testFinished(description);
		String name = description.getMethodName();
		TestDescription text_doc = description.getAnnotation(TestDescription.class);
		
		Boolean value = test_map.get(name);
		if(value == null){
			test_map.put(name, true);
		}
//		System.out.println(this +" is finished");
//		System.out.println("test " + name + "( " + text_doc.value() + " )" +" is finished");
		test_map_doc.put(name, name + "( " + text_doc.value() + " )");
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
		test_map = new HashMap<String, Boolean>();
		test_map_doc = new HashMap<String, String>();
		test_map_error = new HashMap<String, Failure>();
	}

	@Override
	public void testStarted(Description description) throws Exception {
		// TODO Auto-generated method stub
		super.testStarted(description);
		String name = description.getMethodName();
//		System.out.println("test " + name + " is started");
	}
	
}
