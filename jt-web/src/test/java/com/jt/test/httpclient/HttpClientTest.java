package com.jt.test.httpclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {
	
	/**
	 * 1.创建请求对象httpClient
	 * 2.准备url
	 * 3.通过get/post进行url封装
	 * 4.利用httpClient发送请求,获取httpResponse
	 * 5.解析返回值结果.获取数据
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * 
	 */
	@Test
	public void httpGet() throws ClientProtocolException, IOException{
		CloseableHttpClient client = 
				HttpClients.createDefault();
		String url = "https://www.12306.cn/index/";
		HttpGet get = new HttpGet(url);
		HttpPost post = new HttpPost(url);
		CloseableHttpResponse response = 
		client.execute(post);
		if(response.getStatusLine().getStatusCode()
				== 200){
			//请求正确实行
			String result = 
			EntityUtils.toString(response.getEntity());
			System.out.println(result);
		}
	}
	
	
	
}
