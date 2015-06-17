package com.soloappinfo.util;


public class Config {
//	public String BASE_URL = "http://54.69.98.168:8080/childrenguard-server/";
	/*public String BASE_URL = "http://169.7.231.151:8080/childrenguard-server/";
	public String BASE_URL_MVC = BASE_URL + "mvc/";
	public String BASE_URL_COMETD =  BASE_URL +  "cometd/";*/
	
	public String BASE_URL ;
	public String BASE_URL_MVC ;
	public String TERMS_OF_SERVICE_PATH;
	public String PRIVACY_POLICY_PATH;
	public boolean IS_TRIAL;
	public String PAY_VERSION_MARKET_URL;
	
	private Config(){}
	
	public static Config getInstance(){
		return ConfigHolder.config;
	}
	
	static class ConfigHolder{
		public final static Config config = new Config();
	}

	@Override
	public String toString() {
		return "Config [BASE_URL=" + BASE_URL + ", BASE_URL_MVC="
				+ BASE_URL_MVC + ", TERMS_OF_SERVICE_PATH="
				+ TERMS_OF_SERVICE_PATH + ", PRIVACY_POLICY_PATH="
				+ PRIVACY_POLICY_PATH + ", IS_TRIAL=" + IS_TRIAL
				+ ", PAY_VERSION_MARKET_URL=" + PAY_VERSION_MARKET_URL + "]";
	}

}
