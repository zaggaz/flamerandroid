package com.appdupe.flamer.utility;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * This class sends your data through GET and POST methods
 * 
 * */
public class HttpRequest {

	DefaultHttpClient httpClient;
	HttpContext localContext;
	private String ret;

	HttpResponse response = null;
	HttpPost httpPost = null;
	HttpGet httpGet = null;
	@SuppressWarnings("rawtypes")
	Map.Entry me;
	@SuppressWarnings("rawtypes")
	Iterator i;

	public HttpRequest() {
		HttpParams myParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(myParams, 50000);
		HttpConnectionParams.setSoTimeout(myParams, 50000);
		httpClient = new DefaultHttpClient(myParams);
		localContext = new BasicHttpContext();
	}

	public void clearCookies() {
		httpClient.getCookieStore().clear();
	}

	public String sendGet(String url) {
		httpGet = new HttpGet(url);

		try {
			response = httpClient.execute(httpGet);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret = EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			Log.e("HttpRequest", "" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	public String postData(String url, List<NameValuePair> nameValuePairs)
			throws Exception {
		// Getting the response handler for handling the post response
		ResponseHandler<String> res = new BasicResponseHandler();
		HttpPost postMethod = new HttpPost(url);

		// Setting the data that is to be sent
		postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		// Execute HTTP Post Request
		String response = httpClient.execute(postMethod, res);
		return response;
	}

}