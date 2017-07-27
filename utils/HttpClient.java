package com.manager.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * HTTP服务
 * @author xu
 *
 */

public class HttpClient {
	private static Logger logger=Logger.getLogger(HttpClient.class);
	public static DefaultHttpClient getclient(){
		
		return new DefaultHttpClient();
		
	}
	
	//get请求
   public String get(String uri) throws ClientProtocolException, IOException{
	   HttpGet get=new HttpGet(uri);
	   HttpResponse response=HttpClient.getclient().execute(get);
	   response.setHeader("Content-type", "application/json");
	   HttpEntity entity=response.getEntity();
	   String str = EntityUtils.toString(entity);
	   System.out.println("接收到的信息为"+str);
	   return str;
   }
   
   
   //post请求
  public static void post(String uri) throws ClientProtocolException, IOException{
	  HttpPost post=new HttpPost(uri);
	  post.setHeader("Content-type", "application/json");
	  HttpResponse response=HttpClient.getclient().execute(post);
	   HttpEntity entity=response.getEntity();
	   String str = EntityUtils.toString(entity);
	   logger.info("接收到的信息为"+str);
  }
  
  //put请求
  public static void put(String uri) throws Exception{
	  HttpPut put=new HttpPut(uri);
	  put.setHeader("Content-type", "application/json");
	  put.setHeader("Accept-Language", "zh-CN");
	  List<NameValuePair> values = new ArrayList<NameValuePair>();
	  values.add(new BasicNameValuePair("id", "1"));
	  values.add(new BasicNameValuePair("name", "xiaohong"));
	  UrlEncodedFormEntity entity;
	  entity = new UrlEncodedFormEntity(values);
	  put.setEntity(entity);
	  HttpResponse response = HttpClient.getclient().execute(put);
	  HttpEntity resentity = response.getEntity();
	  String str = EntityUtils.toString(resentity);
	  logger.info("接收到的信息为"+str);
	  
  }
  
  
  

}
