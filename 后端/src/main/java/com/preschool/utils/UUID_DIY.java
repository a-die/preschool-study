package com.preschool.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
 
public class UUID_DIY {

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-","");
	}

	public static String getUUIDInOrderId(){
		String orderNo = "" ;
		String trandNo = String.valueOf((Math.random() * 9 + 1) * 1000000);
		String sdf = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
		orderNo = trandNo.toString().substring(0, 4);
		orderNo = orderNo + sdf ;
		return orderNo.substring(0,8);
	}

	public static void main(String[] args){
		for (int i = 0; i<100; i++)
			System.out.println(UUID_DIY.getUUIDInOrderId());
	}

 
}