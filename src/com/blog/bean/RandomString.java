package com.blog.bean;

import java.util.Random;

public class RandomString {
	private static String randomString;
	private static String primaryKey = "hjzgg5211314";
	public static String getPrimaryKey(){
		return primaryKey;
	}
	
	public static synchronized String produceRandomString(){
		String params = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@!#$%^&*";
		Random random = new Random();
		int len = Math.abs(random.nextInt())%50+10;
		StringBuilder sb = new StringBuilder();
		while(len-- != 0)
			sb.append(params.charAt(Math.abs(random.nextInt()%params.length())));
		randomString = sb.toString();
		return randomString;
	}
	
}
