package com.pony.config;



public class Consts {


	public static int TIME_OUT = 30000;

	public final static String URL = "http://192.168.0.154:8080/HouseService/";

	public final static String URL_IMAGE_LOCAL = "http://192.168.0.154:8080/HouseService/upload/";
	
	public static class APP {

		public static final String RegisterAction = "servlet/RegisterAction";
		public static final String HouseAction = "servlet/HouseAction";
		public static final String OrderAction = "servlet/OrderAction";
		public static final String NewsAction = "servlet/NewsAction";
		public static final String ShopAction = "servlet/ShopAction";

		
	}

	public static class actionId {

		public static final int resultFlag = 1;
		public static final int resultCode = 2;
		public static final int resultMsg = 3;
		public static final int resultSix = 6;
		public static final int resultSeven = 7;
	}
}
