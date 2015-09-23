/**
 * ============================================================================
 *
 * Copyright (C) 2011 Android Museum Systems.  All rights reserved. The content
 * presented herein may not, under any circumstances, be reproduced, in
 * whole or in any part or form, without written permission from
 * Museum Systems.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are NOT permitted. Neither the name of Judo Systems,
 * nor the names of contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * ============================================================================
 *
 * Author: Admin
 *
 *
 * Revision History
 * ----------------------------------------------------------------------------
 * Date                Author              Comment, bug number, fix description
 *
 * Jan 2, 2012      tuan@edge-works.net           version 1.0
 *
 * ----------------------------------------------------------------------------
 */

package com.appdupe.flamer.utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appdupe.flamernofb.R;

public class Ultilities {
	private static final String TAG = "Ultilities";
	private static Bitmap bgimage;

	private static Bitmap CallImage;
	private static Uri uri;
	private static int time;
	private static String color;
	public static Boolean isDirectoryIsCreated = false;
	public static float imagexPostion;
	public static float imageyPostion;
	public static float pagewidth;
	public static float pageheigth;
	private File createdirectoty;
	private File createdFileName;
	private InputStream is;

	SimpleDateFormat inFormat = null;
	// private String datestr;
	Date date = null;
	SimpleDateFormat outFormat;
	private static boolean mDebugLog = true;
	private static String mDebugTag = "Ultilities";

	void logDebug(String msg) {

		if (mDebugLog) {
			Log.d(mDebugTag, msg);
		}
	}

	void logError(String msg) {

		if (mDebugLog) {
			Log.e(mDebugTag, msg);
		}
	}

	// private int screenHeight;
	// private int screenWidth;

	// private int topbarheight=0;

	public String getCurrentGmtTime() {
		String curentdateTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		curentdateTime = sdf.format(new Date());

		return curentdateTime;
	}

	public Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xffff0000;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setFilterBitmap(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawOval(rectF, paint);

		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth((float) 4);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;

	}

	public List<NameValuePair> getLoginParameter(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		namevaluepairs.add(new BasicNameValuePair("ent_first_name", params[0])); // mandatory
		namevaluepairs.add(new BasicNameValuePair("ent_last_name", params[1]));
		namevaluepairs.add(new BasicNameValuePair("ent_fbid", params[2]));
		namevaluepairs.add(new BasicNameValuePair("ent_email", params[3])); // mandatory
		namevaluepairs.add(new BasicNameValuePair("ent_sex", params[4])); // mandatory

		namevaluepairs.add(new BasicNameValuePair("ent_city", params[5]));
		namevaluepairs.add(new BasicNameValuePair("ent_country", params[6]));
		namevaluepairs.add(new BasicNameValuePair("ent_curr_lat", params[7]));
		namevaluepairs.add(new BasicNameValuePair("ent_curr_long", params[8]));
		namevaluepairs.add(new BasicNameValuePair("ent_tag_line", params[9]));
		namevaluepairs.add(new BasicNameValuePair("ent_pers_desc", params[10]));
		namevaluepairs.add(new BasicNameValuePair("ent_dob", params[11]));
		namevaluepairs.add(new BasicNameValuePair("ent_pref_sex", params[12]));
		namevaluepairs.add(new BasicNameValuePair("ent_pref_lower_age",
				params[13]));
		namevaluepairs.add(new BasicNameValuePair("ent_pref_upper_age",
				params[14]));
		namevaluepairs
				.add(new BasicNameValuePair("ent_pref_radius", params[15]));
		namevaluepairs.add(new BasicNameValuePair("ent_likes", params[16]));
		namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[17])); // mandatory
		namevaluepairs
				.add(new BasicNameValuePair("ent_push_token", params[18])); // mandatory
		namevaluepairs.add(new BasicNameValuePair("ent_qbid", params[19])); // mandatory
		namevaluepairs
				.add(new BasicNameValuePair("ent_device_type", params[20])); // mandatory
		namevaluepairs.add(new BasicNameValuePair("ent_auth_type", params[21])); // mandatory

		return namevaluepairs;
	}

	public List<NameValuePair> getUploadParameter(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		namevaluepairs.add(new BasicNameValuePair("ent_sess_token", params[0]));
		namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
		namevaluepairs.add(new BasicNameValuePair("ent_prof_url", params[2]));
		namevaluepairs.add(new BasicNameValuePair("ent_other_urls", params[3]));

		return namevaluepairs;
	}

	public List<NameValuePair> getUploadChunkParameter(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		namevaluepairs.add(new BasicNameValuePair("ent_sess_token", params[0]));
		namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
		namevaluepairs
				.add(new BasicNameValuePair("ent_image_chunk", params[2]));
		namevaluepairs.add(new BasicNameValuePair("ent_image_name", params[3]));
		namevaluepairs.add(new BasicNameValuePair("ent_image_flag", params[4]));

		logDebug("getUploadChunkParameter  ent_sess_token  " + params[0]);
		logDebug("getUploadChunkParameter  ent_dev_id  " + params[1]);
		logDebug("getUploadChunkParameter  ent_image_chunk  " + params[2]);
		logDebug("getUploadChunkParameter  ent_image_name  " + params[3]);
		logDebug("getUploadChunkParameter  ent_image_flag  " + params[4]);

		return namevaluepairs;
	}

	public List<NameValuePair> getDeleteParameter(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		namevaluepairs.add(new BasicNameValuePair("ent_sess_token", params[0]));
		namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
		namevaluepairs.add(new BasicNameValuePair("ent_image_name", params[2]));
		namevaluepairs.add(new BasicNameValuePair("ent_image_flag", params[3]));

		return namevaluepairs;
	}

	public List<NameValuePair> getUploadSingleImage(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		logDebug("getUploadSingleImage  " + params[3]);
		if (Integer.parseInt(params[3]) == 1) {
			namevaluepairs.add(new BasicNameValuePair("ent_sess_token",
					params[0]));
			namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
			namevaluepairs
					.add(new BasicNameValuePair("ent_prof_url", params[2]));
		} else {
			namevaluepairs.add(new BasicNameValuePair("ent_sess_token",
					params[0]));
			namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
			namevaluepairs.add(new BasicNameValuePair("ent_other_urls",
					params[2]));
		}

		// ent_image_flag

		return namevaluepairs;
	}

	public List<NameValuePair> getParameterForUpdateUserPersionlDescription(
			String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		namevaluepairs.add(new BasicNameValuePair("ent_sess_token", params[0]));
		namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));

		namevaluepairs.add(new BasicNameValuePair("ent_pers_desc", params[2]));

		// ent_image_flag

		return namevaluepairs;
	}

	public List<NameValuePair> getFindMatchParameter(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		namevaluepairs.add(new BasicNameValuePair("ent_user_fbid", params[0]));
		// namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));

		// ent_image_flag

		return namevaluepairs;
	}

	public List<NameValuePair> getUpdateTokeParameter(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		namevaluepairs.add(new BasicNameValuePair("ent_sess_token", params[0]));
		namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
		namevaluepairs.add(new BasicNameValuePair("ent_fbid", params[2]));

		return namevaluepairs;
	}

	public List<NameValuePair> getUserProfileParameter(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		// namevaluepairs.add(new BasicNameValuePair("ent_sess_token",
		// params[0]));
		// namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
		namevaluepairs.add(new BasicNameValuePair("ent_user_fbid", params[0]));

		// ent_image_flag

		return namevaluepairs;
	}

	public List<NameValuePair> getUserPrefrenceParameter(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		// namevaluepairs.add(new BasicNameValuePair("ent_sess_token",
		// params[0]));
		// namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
		namevaluepairs.add(new BasicNameValuePair("ent_sex", params[2]));

		namevaluepairs.add(new BasicNameValuePair("ent_pref_sex", params[3]));
		namevaluepairs.add(new BasicNameValuePair("ent_pref_lower_age",
				params[4]));
		namevaluepairs.add(new BasicNameValuePair("ent_pref_upper_age",
				params[5]));
		namevaluepairs
				.add(new BasicNameValuePair("ent_pref_radius", params[6]));
		namevaluepairs.add(new BasicNameValuePair("ent_distance_type",
				params[7]));
		namevaluepairs.add(new BasicNameValuePair("ent_user_fbid", params[8]));

		return namevaluepairs;
	}

	// public List<NameValuePair> getLogOutParameter(String[] params) {
	// List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
	// namevaluepairs.add(new BasicNameValuePair("ent_sess_token", params[0]));
	// namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
	//
	// // ent_image_flag
	//
	// return namevaluepairs;
	// }

	public List<NameValuePair> getDeleteUserAccountParameter(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		// namevaluepairs.add(new BasicNameValuePair("ent_sess_token",
		// params[0]));
		// namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
		namevaluepairs.add(new BasicNameValuePair("ent_user_fbid", params[1]));
		// ent_image_flag

		return namevaluepairs;
	}

	public List<NameValuePair> getInviteActionParameter(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		// namevaluepairs.add(new BasicNameValuePair("ent_sess_token",
		// params[0]));
		namevaluepairs.add(new BasicNameValuePair("ent_user_fbid", params[0]));
		namevaluepairs
				.add(new BasicNameValuePair("ent_invitee_fbid", params[1]));
		namevaluepairs
				.add(new BasicNameValuePair("ent_user_action", params[2]));

		// ent_image_flag

		return namevaluepairs;
	}

	public List<NameValuePair> getUserLikedParameter(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		namevaluepairs.add(new BasicNameValuePair("ent_user_fbid", params[0]));
		// namevaluepairs.add(new BasicNameValuePair("ent_sess_token",
		// params[0]));
		// namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
		// namevaluepairs.add(new BasicNameValuePair("ent_datetime",
		// params[2]));

		// ent_image_flag

		return namevaluepairs;
	}

	public int getStatusBarHeight(Activity activity) {
		int result = 0;
		int resourceId = activity.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = activity.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	@SuppressLint("NewApi")
	public static int getWidth(Context mContext) {
		int width = 0;
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		if (Build.VERSION.SDK_INT > 12) {
			Point size = new Point();
			display.getSize(size);
			width = size.x;
		} else {
			width = display.getWidth(); // deprecated
		}
		return width;
	}

	@SuppressLint("NewApi")
	public static int getHeight(Context mContext) {
		int height = 0;
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		if (Build.VERSION.SDK_INT > 12) {
			Point size = new Point();
			display.getSize(size);
			height = size.y;
		} else {
			height = display.getHeight(); // deprecated
		}
		return height;
	}

	public int addsHeight(Activity activity) {
		// Log.i(TAG, "getImageHeightAndWidth");

		// int imageHeightAndWidth[]= new int[2];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int topMatgin = 0;

		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// Log.i(TAG, "getImageHeightAndWidth mdpi");
			topMatgin = 51;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220)) {

			// Log.i(TAG, "getImageHeightAndWidth ldpi");
			topMatgin = 30;

		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// Log.i(TAG, "getImageHeightAndWidth hdpi");
			topMatgin = 76;

		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {
			topMatgin = 104;

		} else {
			topMatgin = 95;
		}
		return topMatgin;
	}

	public String makeHttpRequest(String url, String method,
			List<NameValuePair> params) {

		InputStream is = null;
		// Making HTTP request
		try {

			// check for request method
			if (method == "POST") {
				// request method is POST
				// defaultHttpClient

				// HttpParams httpParameters = new BasicHttpParams();
				// HttpConnectionParams.setConnectionTimeout(httpParameters,
				// 60000);
				// HttpConnectionParams.setSoTimeout(httpParameters, 60000);
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				// HttpParams httpParameters = new BasicHttpParams();
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				// System.out.println("--------Orignal URL-------"+params);
				// logDebug("Orignal URL   params "+params);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

				/*
				 * HttpGet httpGet = new HttpGet(url); HttpParams httpParameters
				 * = new BasicHttpParams(); // Set the timeout in milliseconds
				 * until a connection is established. // The default value is
				 * zero, that means the timeout is not used. int
				 * timeoutConnection = 3000;
				 * HttpConnectionParams.setConnectionTimeout(httpParameters,
				 * timeoutConnection); // Set the default socket timeout
				 * (SO_TIMEOUT) // in milliseconds which is the timeout for
				 * waiting for data. int timeoutSocket = 5000;
				 * HttpConnectionParams.setSoTimeout(httpParameters,
				 * timeoutSocket);
				 * 
				 * DefaultHttpClient httpClient = new
				 * DefaultHttpClient(httpParameters); HttpResponse response =
				 * httpClient.execute(httpGet);
				 */

			} else if (method == "GET") {
				// request method is GET
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams
						.setConnectionTimeout(httpParameters, 20000);
				HttpConnectionParams.setSoTimeout(httpParameters, 20000);
				DefaultHttpClient httpClient = new DefaultHttpClient(
						httpParameters);
				// DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				System.out.println("--------Orignal URL-------" + params);
				System.out.println("***paramString***" + paramString);
				url += "?" + paramString;
				System.out.println("***url***" + url);
				HttpGet httpGet = new HttpGet(url);

				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				Log.e("is^", is.toString());
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String response = null;
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			response = sb.toString();
			// json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		// try
		// {
		// jObj = new JSONObject(json);
		// }
		// catch (JSONException e)
		// {
		// Log.e("JSON Parser", "Error parsing data " + e.toString());
		// }
		//
		// return JSON String
		// return jObj;
		return response;

	}

	public String postDataToServer(String url) throws Throwable {

		HttpPost request = new HttpPost(url);
		StringBuilder sb = new StringBuilder();

		String requestData = prepareRequest();
		StringEntity entity = new StringEntity(requestData);
		entity.setContentType("application/x-www-form-urlencoded;charset=UTF-8");// text/plain;charset=UTF-8
		request.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

		request.setHeader("Accept", "application/json");
		request.setEntity(entity);
		// Send request to WCF service
		HttpResponse response = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// DefaultHttpClient httpClient = getNewHttpClient();
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10 * 1000);
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				10 * 1000);
		try {

			response = httpClient.execute(request);
		} catch (SocketException se) {
			Log.e("SocketException", se + "");
			throw se;
		}
		/*
		 * Patch to prevent user from hitting the request twice by clicking the
		 * view [button, link etc] twice.
		 */
		finally {
		}

		InputStream in = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);

		}
		Log.d("response in Post method", sb.toString() + "");
		return sb.toString();
	}

	public String prepareRequest() {
		return "ent_first_name=" + "sunil dubey" + "&ent_fbid=" + "12323"
				+ "ent_email=" + "sunil@3embed.com" + "&ent_sex=" + "1"
				+ "&ent_dev_id=" + "213234344543546" + "&ent_qbid=" + "233434"
				+ "&ent_device_type=" + "1" + "&ent_auth_type=" + "1";

		// return "ent_first_name="+"sunil dubey&opt=all";
	}

	public static String callhttpPostRequest(String url) {
		System.out.println("utility url..." + url);
		url = url.replaceAll(" ", "%20");
		String resp = null;
		HttpPost httpRequest;
		try {
			httpRequest = new HttpPost(url);
			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 60000;
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			int timeoutSocket = 60000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			HttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpResponse response = httpClient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
			final long contentLength = bufHttpEntity.getContentLength();
			if ((contentLength >= 0)) {
				InputStream is = bufHttpEntity.getContent();
				int tobeRead = is.available();
				System.out.println("Utility callhttpRequest tobeRead.."
						+ tobeRead);
				ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
				int ch;

				while ((ch = is.read()) != -1) {
					bytestream.write(ch);
				}

				resp = new String(bytestream.toByteArray());
				System.out.println("Utility callhttpRequest resp.." + resp);
			}
		} catch (MalformedURLException e) {
			System.out.println("Utility callhttpRequest.." + e);
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			System.out.println("Utility callhttpRequest.." + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Utility callhttpRequest.." + e);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Utility Exception.." + e);
		}
		return resp;
	}

	public String POST(/*
						 * String Username, String IsAuthenticated, String
						 * Password
						 */) {
		String Returned = null;
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost(
				"http://108.166.190.172:81/tinderClone");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			// Your DATA
			nameValuePairs
					.add(new BasicNameValuePair("ent_first_name", "sunil"));
			nameValuePairs.add(new BasicNameValuePair("ent_fbid", "123"));
			nameValuePairs.add(new BasicNameValuePair("ent_email",
					"sunil@3embed"));
			nameValuePairs.add(new BasicNameValuePair("ent_sex", "1"));
			nameValuePairs.add(new BasicNameValuePair("ent_dev_id", "1"));
			nameValuePairs.add(new BasicNameValuePair("ent_qbid", "143545"));
			nameValuePairs.add(new BasicNameValuePair("ent_device_type", "2"));
			nameValuePairs.add(new BasicNameValuePair("ent_auth_type", "1"));
			nameValuePairs.add(new BasicNameValuePair("ent_submit", "submit"));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
					HTTP.UTF_8));

			HttpResponse response = httpclient.execute(httppost);

			HttpEntity resEntity = response.getEntity();
			Returned = EntityUtils.toString(resEntity);

			System.out.println(Returned);

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

			e.printStackTrace();
		}

		return Returned;
	}

	// public Bitmap getBitmapFormGellary(Intent intent,Context
	// mContext,Activity activity)
	// {
	//
	// Log.d(TAG, "getBitmapFormGellary");
	// Bitmap imagefromgallery=null;
	// Uri selectedImage = intent.getData();
	// Log.d(TAG, "getBitmapFormGellary    selectedImage "+selectedImage);
	// int []
	// imageHieghtandWidht=getImageHeightAndWidthForCameraandGellaryImage(activity);
	// //Log.i(TAG,
	// "onActivityResult  getBitmapFormCamraAndGellary image height "+imageHieghtandWidht[0]);
	// //Log.i(TAG,
	// "onActivityResult  getBitmapFormCamraAndGellary image width "+imageHieghtandWidht[1]);
	// //Log.i(TAG,
	// "onActivityResult  getBitmapFormCamraAndGellary imageHieghtandWidht "+imageHieghtandWidht);
	// byte [] byteArray=null;
	//
	// if (selectedImage!=null)
	// {
	// try
	// {
	// // byteArray=Utility.scaleImage(mContext, selectedImage);
	// Log.d(TAG, "getBitmapFormGellary    byteArray "+byteArray);
	// } catch (IOException e)
	// {
	// byteArray=null;
	// Log.e(TAG, "getBitmapFormGellary    IOException "+e);
	// e.printStackTrace();
	// }
	// if (byteArray!=null)
	// {
	// Bitmap mBitmapUnscaleImage=bynaryArryToBitmap(byteArray);
	// Log.d(TAG,
	// "getBitmapFormGellary    mBitmapUnscaleImage "+mBitmapUnscaleImage);
	// //Log.i(TAG,
	// "onActivityResult  getBitmapFormCamraAndGellary mBitmapUnscaleImage "+mBitmapUnscaleImage);
	// //ScalingUtilities scalingUtilities=new ScalingUtilities();
	// //Log.i(TAG,
	// "onActivityResult  getBitmapFormCamraAndGellary scalingUtilities "+scalingUtilities);
	// //
	// imagefromgallery=scalingUtilities.createScaledBitmap(mBitmapUnscaleImage,
	// imageHieghtandWidht[1], imageHieghtandWidht[0], ScalingLogic.CROP);
	// Log.d(TAG,
	// "getBitmapFormGellary    imagefromgallery "+mBitmapUnscaleImage);
	// //Log.i(TAG,
	// "onActivityResult  getBitmapFormCamraAndGellary scalingUtilities "+scalingUtilities);
	// //mBitmapUnscaleImage.recycle();
	//
	// }
	// else
	// {
	// //Log.i(TAG,
	// "onActivityResult byte array  is null byteArray "+byteArray);
	// imagefromgallery=null;
	// }
	// }
	// else
	// {
	// //Log.i(TAG,
	// "onActivityResult uri is null selectedImage "+selectedImage);
	// imagefromgallery=null;
	// }
	//
	// return imagefromgallery;
	//
	// }

	public Bitmap getBitmapFormCamra(Intent intent, Context mContext,
			Activity activity) {

		// Log.i(TAG, "onActivityResult getBitmapFormCamraAndGellary ");
		// Log.i(TAG,
		// "onActivityResult getBitmapFormCamraAndGellary intent "+intent);
		// Log.i(TAG,
		// "onActivityResult getBitmapFormCamraAndGellary mContext "+mContext);
		// Log.i(TAG,
		// "onActivityResult getBitmapFormCamraAndGellary activity "+activity);
		Bitmap imagefromgallery = null;
		Bitmap thumbnail = (Bitmap) intent.getExtras().get("data");
		// Log.i(TAG,
		// "onActivityResult getBitmapFormCamraAndGellary thumbnail "+thumbnail);
		// int []
		// imageHieghtandWidht=getImageHeightAndWidthForCameraandGellaryImage(activity);

		if (thumbnail != null) {

			// ScalingUtilities scalingUtilities=new ScalingUtilities();

			// imagefromgallery=scalingUtilities.createScaledBitmap(thumbnail,
			// imageHieghtandWidht[1], imageHieghtandWidht[0],
			// ScalingLogic.CROP);
			// Log.i(TAG,
			// "onActivityResult  getBitmapFormCamraAndGellary imagefromgallery "+imagefromgallery);
			thumbnail.recycle();

		} else {
			// Log.i(TAG, "onActivityResult thumbnail  is null  "+thumbnail);
			imagefromgallery = null;
		}

		return imagefromgallery;

	}

	public int[] getImageHeightAndWidthForMatchedUser(Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int imageHeightAndWidth[] = new int[2];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 120;
			imagewidth = 120;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 480;
			imagewidth = 440;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 600;
			imagewidth = 760;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else {
			imagehiegth = 200;
			imagewidth = 200;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	public int[] getTopMarginForInviteLayoutAndText(Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int imageHeightAndWidth[] = new int[2];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 120;
			imagewidth = 120;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 50;
			imagewidth = 20;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 100;
			imagewidth = 70;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else {
			imagehiegth = 200;
			imagewidth = 200;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	// imageLayoutHeightandWidth
	public int[] getImageHeightAndWidthForProFileImageHomsecreen(
			Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int imageHeightAndWidth[] = new int[2];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 120;
			imagewidth = 120;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 150;
			imagewidth = 150;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 200;
			imagewidth = 200;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else {
			imagehiegth = 200;
			imagewidth = 200;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	public int[] imageLayoutHeightandWidth(Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int imageHeightAndWidth[] = new int[5];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		int topMargin;
		int likedisliketopMarging;
		int leftMargin;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 120;
			imagewidth = 120;

			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 480;
			imagewidth = 430;
			topMargin = 20;
			likedisliketopMarging = 520;
			leftMargin = 25;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
			imageHeightAndWidth[2] = topMargin;
			imageHeightAndWidth[3] = likedisliketopMarging;
			imageHeightAndWidth[4] = leftMargin;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 600;
			imagewidth = 645;
			topMargin = 100;
			likedisliketopMarging = 750;
			leftMargin = 32;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
			imageHeightAndWidth[2] = topMargin;
			imageHeightAndWidth[3] = likedisliketopMarging;
			imageHeightAndWidth[4] = leftMargin;
		} else {
			imagehiegth = 200;
			imagewidth = 200;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	public int[] getImageHeightAndWidthForGellary(Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int imageHeightAndWidth[] = new int[2];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 120;
			imagewidth = 120;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 150;
			imagewidth = 150;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 200;
			imagewidth = 200;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else {
			imagehiegth = 250;
			imagewidth = 250;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	public int[] getImageHeightAndWidthForProfileGellary(Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int imageHeightAndWidth[] = new int[2];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 400;
			imagewidth = 400;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 500;
			imagewidth = 480;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 700;
			imagewidth = 720;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else {
			imagehiegth = 250;
			imagewidth = 250;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	public int[] getImageHeightAndWidthForEditProfileScreen(Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int imageHeightAndWidth[] = new int[2];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 120;
			imagewidth = 120;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 280;
			imagewidth = 280;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 428;
			imagewidth = 428;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else {
			imagehiegth = 200;
			imagewidth = 200;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	public int[] getImageHeightAndWidthForEditProfileScreenForOther(
			Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int imageHeightAndWidth[] = new int[2];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 120;
			imagewidth = 120;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 130;
			imagewidth = 130;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 185;
			imagewidth = 185;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else {
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	public int[] getImageHeightAndWidthForAlubumListview(Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int imageHeightAndWidth[] = new int[2];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 50;
			imagewidth = 50;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 50;
			imagewidth = 50;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 50;
			imagewidth = 50;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 150;
			imagewidth = 150;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else {
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	// / interest and userfriend image height and width

	public int[] getImageHeightAndWidthForInrestAndFriendsLyout(
			Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");
		int imageHeightAndWidth[] = new int[2];
		int screenHeight = 0;
		int screenWidth = 0;

		try {
			screenHeight = getHeight(activity);
			screenWidth = getWidth(activity);
		} catch (Exception e) {
			logError("getImageHeightAndWidthForInrestAndFriendsLyout Exception "
					+ e);
		}

		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 50;
			imagewidth = 50;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 50;
			imagewidth = 50;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 190;
			imagewidth = 190;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 220;
			imagewidth = 220;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else {
			imagehiegth = 200;
			imagewidth = 200;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	public int[] getImageHeightAndWidthForAlubumGridView(Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int imageHeightAndWidth[] = new int[2];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 50;
			imagewidth = 50;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 145;
			imagewidth = 145;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 215;
			imagewidth = 215;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else {
			imagehiegth = 250;
			imagewidth = 250;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	public int[] getImageHeightForChatScreen(Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int imageHeightAndWidth[] = new int[2];
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		int imagehiegth;
		int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			imagehiegth = 50;
			imagewidth = 50;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			imagehiegth = 100;
			imagewidth = 100;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			imagehiegth = 200;
			imagewidth = 200;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");
			imagehiegth = 220;
			imagewidth = 220;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		} else {
			imagehiegth = 250;
			imagewidth = 250;
			imageHeightAndWidth[0] = imagehiegth;
			imageHeightAndWidth[1] = imagewidth;
		}

		return imageHeightAndWidth;
	}

	public int getDeviceType(Activity activity) {
		// //Log.i(TAG, "getImageHeightAndWidth");

		int devieytype = 0;
		int screenHeight = getHeight(activity);
		int screenWidth = getWidth(activity);
		// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
		// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
		// int imagehiegth;
		// int imagewidth;
		if ((screenHeight <= 500 && screenHeight >= 480)
				&& (screenWidth <= 340 && screenWidth >= 300)) {
			// //Log.i(TAG, "getImageHeightAndWidth mdpi");
			devieytype = 1;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// //Log.i(TAG, "getImageHeightAndWidth ldpi");
			devieytype = 1;

		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// //Log.i(TAG, "getImageHeightAndWidth hdpi");
			devieytype = 1;

		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {

			// //Log.i(TAG, "getImageHeightAndWidth xdpi");

			devieytype = 1;
		} else {
			devieytype = 3;
		}

		return devieytype;
	}

	// public int[] getHeightAndWidthOfSignaturePanel(Activity activity)
	// {
	// ////Log.i(TAG, "getImageHeightAndWidth");
	//
	// int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// Log.i(TAG,
	// "getHeightAndWidthOfSignaturePanel  screenHeight "+screenHeight);
	// Log.i(TAG,
	// "getHeightAndWidthOfSignaturePanel  screenWidth  "+screenWidth);
	// int imagehiegth;
	// int imagewidth;
	// if ((screenWidth <= 500 && screenWidth >= 480)&& (screenHeight <= 340 &&
	// screenHeight >= 300))
	// {
	// ////Log.i(TAG, "getImageHeightAndWidth mdpi");
	// imagehiegth=198;
	// imagewidth=472;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	//
	// }
	//
	// else if ((screenWidth <= 400 && screenWidth >= 300)&& (screenHeight <=
	// 240 && screenHeight >= 220))
	//
	// {
	//
	// Log.i(TAG, "getHeightAndWidthOfSignaturePanel ldpi");
	// imagehiegth=340;
	// imagewidth=140;
	// imageHeightAndWidth[0]=imagewidth;
	// imageHeightAndWidth[1]=imagehiegth;
	// }
	//
	// else if ((screenWidth <= 840 && screenWidth >= 780)&& (screenHeight <=
	// 500 && screenHeight >= 440))
	// {
	//
	// ////Log.i(TAG, "getImageHeightAndWidth hdpi");
	// imagehiegth=284;
	// imagewidth=790;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// else if ((screenWidth <= 1280 && screenWidth >= 840)&& (screenHeight <=
	// 720 && screenHeight >= 500))
	// {
	//
	// ////Log.i(TAG, "getImageHeightAndWidth xdpi");
	// imagehiegth=445;
	// imagewidth=1250;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// else
	// {
	// imagehiegth=445;
	// imagewidth=1250;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	//
	// return imageHeightAndWidth;
	// }

	// public int[] getHeightAndWidthOfSekkbar(Activity activity)
	// {
	// ////Log.i(TAG, "getImageHeightAndWidth");
	//
	// int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// Log.i(TAG,
	// "getHeightAndWidthOfSignaturePanel  screenHeight "+screenHeight);
	// Log.i(TAG,
	// "getHeightAndWidthOfSignaturePanel  screenWidth  "+screenWidth);
	// int imagehiegth;
	// int imagewidth;
	// if ((screenWidth <= 500 && screenWidth >= 480)&& (screenHeight <= 340 &&
	// screenHeight >= 300))
	// {
	// ////Log.i(TAG, "getImageHeightAndWidth mdpi");
	// imagehiegth= RelativeLayout.LayoutParams.WRAP_CONTENT;
	// imagewidth=171;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	//
	// }
	//
	// else if ((screenWidth <= 400 && screenWidth >= 300)&& (screenHeight <=
	// 240 && screenHeight >= 220))
	//
	// {
	//
	// Log.i(TAG, "getHeightAndWidthOfSignaturePanel ldpi");
	// imagehiegth= RelativeLayout.LayoutParams.WRAP_CONTENT;
	// imagewidth=138;
	// imageHeightAndWidth[0]=imagewidth;
	// imageHeightAndWidth[1]=imagehiegth;
	// }
	//
	// else if ((screenWidth <= 840 && screenWidth >= 780)&& (screenHeight <=
	// 500 && screenHeight >= 440))
	// {
	//
	// ////Log.i(TAG, "getImageHeightAndWidth hdpi");
	// imagehiegth=RelativeLayout.LayoutParams.WRAP_CONTENT;
	// imagewidth=256;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// else if ((screenWidth <= 1280 && screenWidth >= 840)&& (screenHeight <=
	// 720 && screenHeight >= 500))
	// {
	//
	// ////Log.i(TAG, "getImageHeightAndWidth xdpi");
	// imagehiegth=RelativeLayout.LayoutParams.WRAP_CONTENT;
	// imagewidth=385;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// else
	// {
	// imagehiegth=RelativeLayout.LayoutParams.WRAP_CONTENT;
	// imagewidth=385;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	//
	// return imageHeightAndWidth;
	// }

	// public int[] getImageHeightAndWidthForEditText(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int imagehiegth;
	// int imagewidth;
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// imagehiegth=37;
	// imagewidth=205;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// imagehiegth=28;
	// imagewidth=154;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// imagehiegth=63;
	// imagewidth=305;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	//
	// Log.d(TAG, "getImageHeightAndWidth xdpi");
	// imagehiegth=81;
	// imagewidth=457;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// else
	// {
	// imagehiegth=RelativeLayout.LayoutParams.WRAP_CONTENT;
	// imagewidth=500;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// return imageHeightAndWidth;
	// }

	// public int[] getHeightAndWidthForButton(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int imagehiegth;
	// int imagewidth;
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// imagehiegth=22;
	// imagewidth=22;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// imagehiegth=17;
	// imagewidth=17;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// imagehiegth=32;
	// imagewidth=32;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	//
	// Log.d(TAG, "getImageHeightAndWidth xdpi");
	// imagehiegth=48;
	// imagewidth=48;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// else
	// {
	// imagehiegth=32;
	// imagewidth=32;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// return imageHeightAndWidth;
	// }

	// public int getTopMarginForAddCategoryActivity(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// //int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=15;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=1;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=18;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=20;
	//
	// }
	// else
	// {
	// topMatgin=25;
	// }
	// return topMatgin;
	// }

	// public int getEditextLeftMargin(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// //int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=15;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=10;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=20;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=40;
	//
	// }
	// else
	// {
	// topMatgin=40;
	// }
	// return topMatgin;
	// }

	// public int getCheckBoxLeftPadding(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// //int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=20;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=20;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=30;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=40;
	//
	// }
	// else
	// {
	// topMatgin=40;
	// }
	// return topMatgin;
	// }

	// public int getCheckTopMargin(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// //int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=16;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=10;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=24;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=30;
	//
	// }
	// else
	// {
	// topMatgin=30;
	// }
	// return topMatgin;
	// }

	// public int getLeftMarginForLogingIcon(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// // int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=10;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=8;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=17;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=25;
	//
	// }
	// else
	// {
	// topMatgin=20;
	// }
	// return topMatgin;
	// }

	// public int getTopMarginForLogiButtonTextview(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// //int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=4;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=2;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=8;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=17;
	//
	// }
	// else
	// {
	// topMatgin=22;
	// }
	// return topMatgin;
	// }

	// public int getTextSize(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// //int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=120;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=120;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=120;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=150;
	//
	// }
	// else
	// {
	// topMatgin=150;
	// }
	// return topMatgin;
	// }

	// public int getMarginHomeScreenIcon(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// //int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=0;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=0;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=0;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=50;
	//
	// }
	// else
	// {
	// topMatgin=50;
	// }
	// return topMatgin;
	// }

	// public int getWidthOfHomescreenTexvie(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// // int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=100;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=150;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=250;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=350;
	//
	// }
	// else
	// {
	// topMatgin=300;
	// }
	// return topMatgin;
	// }

	// public int getTopMarginForHomScreenDemo(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// //int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=380;
	// }
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=250;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=610;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=940;
	//
	// }
	// else
	// {
	// topMatgin=960;
	// }
	// return topMatgin;
	// }

	// public int getTopMarginForHomScreenDemoWhenSinup(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// // int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=400;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=270;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=620;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=950;
	//
	// }
	// else
	// {
	// topMatgin=960;
	// }
	// return topMatgin;
	// }

	// public int getheigthOfTextViewForUpdateDetailActivity(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// // int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=70;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=50;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=110;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=150;
	//
	// }
	// else
	// {
	// topMatgin=100;
	// }
	// return topMatgin;
	// }

	// public int ContactNameHeight(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// // int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=40;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=30;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=50;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=75;
	//
	// }
	// else
	// {
	// topMatgin=80;
	// }
	// return topMatgin;
	// }

	public void displayMessageAndExit(Activity activity, String tiltleMassage,
			String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(tiltleMassage);
		builder.setMessage(message);
		builder.setPositiveButton("Ok", new FinishListener(activity));
		builder.setOnCancelListener(new FinishListener(activity));
		builder.setCancelable(false);
		builder.show();
	}

	public File GetPathOFDirectory(String directoryName) {
		File mDirectoryName = null;
		mDirectoryName = new File(Environment.getExternalStorageDirectory()
				+ "/" + directoryName);
		if (mDirectoryName.exists()) {
			// allready created
		} else {
			mDirectoryName = null;
		}
		return mDirectoryName;
	}

	public boolean isDirectoyExist(String directoryname) {
		boolean flag = false;
		createdirectoty = new File(Environment.getExternalStorageDirectory()
				+ "/" + directoryname);
		flag = createdirectoty.exists();
		return flag;
	}

	/*
	 * public File createDirectory(String directoryName) { File
	 * createdDirectoryName=null; return createdDirectoryName; }
	 */

	public Bitmap showImage(String path) {
		// //Log.i("showImage","loading:"+path);
		BitmapFactory.Options bfOptions = new BitmapFactory.Options();
		bfOptions.inDither = false; // Disable Dithering mode
		bfOptions.inPurgeable = true; // Tell to gc that whether it needs free
										// memory, the Bitmap can be cleared
		bfOptions.inInputShareable = true; // Which kind of reference will be
											// used to recover the Bitmap data
											// after being clear, when it will
											// be used in the future
		bfOptions.inTempStorage = new byte[32 * 1024];
		Bitmap mbitmap = null;

		File file = new File(path);
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		try {
			if (fs != null)
				mbitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null,
						bfOptions);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		// bm=BitmapFactory.decodeFile(path, bfOptions); This one causes error:
		// java.lang.OutOfMemoryError: bitmap size exceeds VM budget

		// im.setImageBitmap(bm);
		// bm.recycle();
		// bm=null;mbitmap

		return mbitmap;

	}

	public boolean isExternalStoragePresent(Activity activity) {

		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		boolean falg;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// Toast.makeText(activity, "SD card not present",
			// Toast.LENGTH_LONG).show();

			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		if (!((mExternalStorageAvailable) && (mExternalStorageWriteable))) {
			// Toast.makeText(activity, "SD card not present",
			// Toast.LENGTH_LONG).show();

		}

		if (mExternalStorageAvailable && mExternalStorageWriteable) {
			falg = true;

		} else {
			falg = false;
		}
		Log.d(color, "isExternalStoragePresent   falg" + falg);
		return falg;
	}

	public String storeUriContentToFile(Uri uri, File mTmpFile,
			Activity activity) {
		String result = null;
		try {
			if (mTmpFile == null) {
				File root = Environment.getExternalStorageDirectory();
				if (root == null)
					throw new Exception("external storage dir not found");
				mTmpFile = new File(root,
						"AndroidPdfViewer/AndroidPdfViewer_temp.pdf");
				mTmpFile.getParentFile().mkdirs();
				mTmpFile.delete();
			} else {
				mTmpFile.delete();
			}
			InputStream is = activity.getContentResolver().openInputStream(uri);
			OutputStream os = new FileOutputStream(mTmpFile);
			byte[] buf = new byte[1024];
			int cnt = is.read(buf);
			while (cnt > 0) {
				os.write(buf, 0, cnt);
				cnt = is.read(buf);
			}
			os.close();
			is.close();
			result = mTmpFile.getCanonicalPath();
			mTmpFile.deleteOnExit();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return result;
	}

	public byte[] convertFiletoByteArray(File fileName) {
		// //Log.i(TAG, "convertFiletoByteArray  fileName "+fileName);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			// //Log.i(TAG, "convertFiletoByteArray  fis "+fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// //Log.i(TAG, "convertFiletoByteArray  FileNotFoundException "+e);
		}
		// System.out.println(file.exists() + "!!");
		// InputStream in = resource.openStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum); // no doubt here is 0
				// Writes len bytes from the specified byte array starting at
				// offset off to this byte array output stream.
				// System.out.println("read " + readNum + " bytes,");
				// //Log.i(TAG, "convertFiletoByteArray  "+"read " + readNum +
				// " bytes,");
			}
			fis.close();
		} catch (IOException ex) {
			// Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE, null,
			// ex);
			// //Log.i(TAG, "convertFiletoByteArray  IOException "+ex);
		}
		byte[] bytes = bos.toByteArray();

		// below is the different part
		/*
		 * File someFile = new File("java2.pdf"); //Log.i(TAG,
		 * "convertFiletoByteArray  someFile "+someFile); FileOutputStream
		 * fos=null; try { fos = new FileOutputStream(someFile); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); //Log.i(TAG,
		 * "convertFiletoByteArray  FileNotFoundException  for creating file"
		 * +e);
		 * 
		 * } try { fos.write(bytes); } catch (IOException e) {
		 * e.printStackTrace(); //Log.i(TAG,
		 * "convertFiletoByteArray  IOException  for creating file "+e); } try {
		 * fos.flush(); } catch (IOException e) { e.printStackTrace();
		 * //Log.i(TAG,
		 * "convertFiletoByteArray  IOException  for creating file "+e); } try {
		 * fos.close(); } catch (IOException e) {
		 * 
		 * e.printStackTrace(); //Log.i(TAG,
		 * "convertFiletoByteArray  IOException  for creating file "+e); }
		 */
		return bytes;
	}

	public byte[] fileToByteArray(File mFile) {
		// File file = new File("/sdcard/E0022505.mp4");
		try {
			is = new FileInputStream(mFile);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		byte[] fileData = new byte[(int) mFile.length()];
		int read = 0;
		while (read != fileData.length) {
			try {
				read += is.read(fileData, read, fileData.length - read);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileData;
	}

	/*
	 * public String getRealPathFromURI(Uri contentUri,Activity activity) {
	 * String[] proj = { MediaStore.Images.Media.DATA }; Cursor cursor =
	 * activity.managedQuery(contentUri, proj, null, null, null); int
	 * column_index =
	 * cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	 * cursor.moveToFirst(); return cursor.getString(column_index); }
	 */

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public String getRealPathFromURI(Uri contentUri, Activity activity) {
		String[] proj = { MediaStore.Images.Media.DATA };
		CursorLoader loader = new CursorLoader(activity, contentUri, proj,
				null, null, null);
		Cursor cursor = loader.loadInBackground();
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public void addUniqueValueInArrayList(ArrayList<Integer> editImageList,
			int imageCount) {
		if (!editImageList.contains(imageCount)) {
			editImageList.add(imageCount);
		} else {
			// donoting
		}
	}

	public void DownloadPDF(String filePath, File fileName) {
		// System.out.println("Entered download function");
		// Log.i(TAG, "DownloadPDF filePath "+filePath);
		// Log.i(TAG, "DownloadPDF fileName "+fileName);
		try {
			URL url = new URL(filePath);
			// Log.i(TAG, "DownloadPDF url "+url);
			HttpURLConnection c = (HttpURLConnection) url.openConnection();
			// Log.i(TAG, "DownloadPDF c "+c);
			c.setRequestMethod("GET");
			c.setDoOutput(true);
			c.connect();
			// int fileLength = c.getContentLength();
			// Log.i(TAG, "DownloadPDF fileLength "+fileLength);
			// System.out.println("file length " + fileLength);
			// String PATH = Environment.getExternalStorageDirectory()
			// + "/Brunei/";
			// + "/download/";
			// Log.v("log_tag", "PATH: " + PATH);
			// File file = new File(PATH);
			// file.mkdirs();
			File outputFile = new File(fileName.getAbsolutePath());
			// Log.i(TAG, "DownloadPDF outputFile "+outputFile);
			FileOutputStream fos = new FileOutputStream(outputFile);
			// Log.i(TAG, "DownloadPDF fos "+fos);
			InputStream is = c.getInputStream();
			// Log.i(TAG, "DownloadPDF is "+is);
			byte[] buffer = new byte[1024];
			int len1 = 0;
			long total = 0;
			while ((len1 = is.read(buffer)) != -1) {
				total += len1;
				// publishProgress()

				// mProgress = (int) ((total * 100) / fileLength);
				/* System.out.println("mProgress is:" + mProgress); */
				// publishProgress(mProgress);
				fos.write(buffer, 0, len1);
			}
			fos.close();
			is.close();
			// System.out.println("file name is : " + outputFile.getName());
			// Log.v("log_tag", "completed download");
		} catch (Exception e) {
			Log.d("log_tag", "Error: " + e);
			// Log.i(TAG, "DownloadPDF Exception "+e);
			// if (mDialog.isShowing())
			// mDialog.cancel();
			// File file = new File(Environment.getExternalStorageDirectory()
			// .toString() + "/Brunei/" + title + ".pdf");
			// boolean deleted = fileName.delete();
			// System.out.println("file deleted");
			e.printStackTrace();
		}
	}

	public void copyFile(File src, File dst) throws IOException {
		// Log.i(TAG, "copyFile ");
		// Log.i(TAG, "copyFile File src "+src);
		// Log.i(TAG, "copyFile File dst "+dst);
		FileChannel inChannel = new FileInputStream(src).getChannel();
		FileChannel outChannel = new FileOutputStream(dst).getChannel();
		// Log.i(TAG, "copyFile inChannel "+inChannel);
		// Log.i(TAG, "copyFile outChannel "+outChannel);
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
	}

	public static String getColor() {
		return color;
	}

	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();

		// return "123456789101112DummyId2";
	}

	public static void setColor(String color) {
		Ultilities.color = color;
	}

	public InputStream getInputstream(String filepath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filepath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		InputStream is = fis;
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

	/**
	 * @param encodedString
	 * @return bitmap (from given string)
	 */

	public boolean validEmail(String email) {
		return email
				.matches("[A-Z0-9._%+-][A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{3}");
	}

	public boolean isEmailValidate(String emailid) {
		String email = emailid;
		boolean status = true;
		char firstCharacter = email.charAt(0);

		if (!Character.isLetter(firstCharacter)) {
			/*
			 * Toast.makeText(this,
			 * "Invalid Email ! First character must be a letter !",
			 * Toast.LENGTH_SHORT).show(); System.out.println(
			 * "Invalid Email ! First character must be a letter !");
			 */
			status = false;
		}
		if (email.indexOf('@') < 0) {
			/*
			 * Toast.makeText(this, "Invalid Email ! '@' is not present !!!",
			 * Toast.LENGTH_SHORT).show();
			 * System.out.println("Invalid Email ! '@' is not present !!!");
			 */
			status = false;
		} else if (email.indexOf('@') != email.lastIndexOf('@')) { /*
																	 * Toast.
																	 * makeText
																	 * (this,
																	 * "Cannot Have more than one '@' characters !!!"
																	 * , Toast.
																	 * LENGTH_SHORT
																	 * ).show();
																	 * System
																	 * .out
																	 * .println(
																	 * "Cannot Have more than one '@' characters !!!"
																	 * );
																	 */
			status = false;
		}
		if (email.indexOf('.') < 0) { /*
									 * Toast.makeText(this,
									 * "Invalid Email ! '.' is not present !!!",
									 * Toast.LENGTH_SHORT).show();
									 * System.out.println
									 * ("Invalid Email ! '.' is not present !!!"
									 * );
									 */
			status = false;
		} else if (email.indexOf('.') != email.lastIndexOf('.')) { /*
																	 * Toast.
																	 * makeText
																	 * (this,
																	 * "Cannot Have more than one '.' characters !!!"
																	 * , Toast.
																	 * LENGTH_SHORT
																	 * ).show();
																	 * System
																	 * .out
																	 * .println(
																	 * "Cannot Have more than one '.' characters !!!"
																	 * );
																	 */
			status = false;
		}
		if ((email.indexOf('@') > email.indexOf('.')) & status) { /*
																 * Toast.makeText
																 * (this,
																 * "Invalid Email ! '@' should come before '.' "
																 * , Toast.
																 * LENGTH_SHORT
																 * ).show();
																 * System
																 * .out.println(
																 * "Invalid Email ! '@' should come before '.' "
																 * );
																 */
			status = false;

		}
		int indexAt = email.indexOf('@');
		int indexDot = email.indexOf('.');
		int length = email.length();
		if (indexDot == indexAt + 1) { /*
										 * Toast.makeText(this,
										 * "No service provider spacefied ! Invalid email !"
										 * , Toast.LENGTH_SHORT).show();
										 * System.out.println(
										 * "No service provider spacefied ! Invalid email !"
										 * );
										 */
			status = false;
		}
		if (indexDot + 2 > length) { /*
									 * Toast.makeText(this,
									 * "No Domain name specified ! Invalid email !"
									 * , Toast.LENGTH_SHORT).show();
									 * System.out.println
									 * ("No Domain name specified ! Invalid email !"
									 * );
									 */
			status = false;
		}
		if (status == true) {
			char userName[] = new char[indexAt];
			char service[] = new char[indexDot - indexAt - 1];
			char domain[] = new char[length - indexDot - 1];
			for (int iuser = 0; iuser < indexAt; iuser++)
				userName[iuser] = email.charAt(iuser);
			int slimit = 0;
			for (int iservice = indexAt + 1; iservice < indexDot; iservice++) {
				service[slimit] = email.charAt(iservice);
				slimit++;
			}
			int dlimit = 0;
			for (int idomain = indexDot + 1; idomain < 1;) { /*
															 * Toast.makeText(this
															 * ,
															 * "Invalid username !"
															 * ,
															 * Toast.LENGTH_SHORT
															 * ).show();
															 * System.out
															 * .println
															 * ("Invalid username !"
															 * );
															 */
				status = false;
			}
			if (service.length < 1) { /*
									 * Toast.makeText(this,
									 * "Invalid service provider !",
									 * Toast.LENGTH_SHORT).show();
									 * System.out.println
									 * ("Invalid service provider !");
									 */
				status = false;
			}
			if (domain.length < 1) { /*
									 * Toast.makeText(this,"Invalid Domain name !"
									 * , Toast.LENGTH_SHORT).show();
									 * System.out.println
									 * ("Invalid Domain name !");
									 */
				status = false;
			}

		}

		if (status == true)
			System.out.println("Valid Email. Successfully Validated.");

		return status;
	}

	public void finishThisActivity(Activity activity) {
		activity.finish();
	}

	public String bitmapToBase64String(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		return encodedImage;
	}

	public Bitmap bynaryArryToBitmap(byte[] bynaryArray) {
		Bitmap image = null;
		System.out.println("Ultilities bynaryArryToBitmap bynaryArray "
				+ bynaryArray);
		ByteArrayInputStream imageStreamClient = new ByteArrayInputStream(
				bynaryArray);
		image = BitmapFactory.decodeStream(imageStreamClient);
		return image;
	}

	public byte[] bitMapToBytArray(Bitmap image) {
		System.out.println("Ultilities bitMapToBytArray image " + image);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 100, bos);
		byte[] bArray = bos.toByteArray();
		return bArray;
	}

	public Bitmap StringToBitMap(String encodedString) {
		try {
			// Log.i("StringToBitMap", "encodedString"+encodedString);
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			// Log.i("StringToBitMap", "encodeByte"+encodeByte);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			// Log.i("StringToBitMap", "bitmap"+bitmap);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public static int getTime() {
		return time;
	}

	public static void setTime(int time) {
		Ultilities.time = time;
	}

	public static Uri getUri() {
		return uri;
	}

	public static void setUri(Uri uri) {
		Ultilities.uri = uri;
	}

	public static Bitmap getCallImage() {
		return CallImage;
	}

	public static void setCallImage(Bitmap callImage) {
		CallImage = callImage;
	}

	public static Bitmap getBgimage() {
		return bgimage;

	}

	public static void setBgimage(Bitmap bgimage) {
		Ultilities.bgimage = bgimage;
	}

	public static List<Activity> getCleanuplist() {
		return cleanupList;
	}

	public Ultilities() {

	}

	public static final List<Activity> cleanupList = new ArrayList<Activity>();

	/**
	 * Gets the process dialog.
	 * 
	 * @param activity
	 *            the activity
	 * @return the progress dialog
	 */
	public ProgressDialog GetProcessDialog(Activity activity) {
		// prepare the dialog box
		ProgressDialog dialog = new ProgressDialog(activity);
		// make the progress bar cancelable
		dialog.setCancelable(true);
		// set a message text
		dialog.setMessage("Loading...");

		// show it
		return dialog;
	}

	/**
	 * Gets the bitmap from string.
	 * 
	 * @param image_URL
	 *            the image_ url
	 * @return the bitmap from string
	 */
	public Bitmap getBitmapFromString(String image_URL) {
		// Log.i(TAG, "getBitmapFromString ");
		// Log.i(TAG, "getBitmapFromString  "+ image_URL);
		Bitmap srcBitmap;
		if (image_URL == null)
			return null;
		BitmapFactory.Options bmOptions;
		bmOptions = new BitmapFactory.Options();
		// Log.i(TAG, "getBitmapFromString bmOptions "+bmOptions);
		bmOptions.inSampleSize = 1;
		return srcBitmap = LoadImage(image_URL, bmOptions);
	}

	public void copyImageTOSdCard(File ImageFileNmae, Bitmap mBitmap) {
		Log.d(TAG, "copyImageTOSdCard");
		FileOutputStream _fos = null;
		ByteArrayOutputStream outputStream;
		Log.d(TAG, "copyImageTOSdCard  FileOutputStream " + _fos);
		if (mBitmap != null) {
			// try {
			// _fos = new FileOutputStream(ImageFileNmae);
			//
			//
			// Log.d(TAG, "copyImageTOSdCard  FileOutputStream " + _fos);
			// // Log.i(TAG, "copyImageTOSdCard  _fos  "+_fos);
			// } catch (FileNotFoundException e) {
			// e.printStackTrace();
			// Log.e(TAG, "copyImageTOSdCard FileNotFoundException    " + e);
			// }
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

			// you can create a new file name "test.jpg" in sdcard folder.
			// File f = new File(Environment.getExternalStorageDirectory()
			// // + File.separator + IMAGE_DIRECTORY_NAME + File.separator
			// + itemName + ".jpg");
			// mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, _fos);

			// remember close de FileOutput

			try {
				_fos = new FileOutputStream(ImageFileNmae);
				_fos.write(bytes.toByteArray());
				_fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG, "copyImageTOSdCard IOException    " + e);
			} catch (Exception e) {
				Log.e(TAG, "copyImageTOSdCard Exception    " + e);
				e.printStackTrace();
			}
			try {
				_fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG, "copyImageTOSdCard IOException    " + e);
			}
		} else {

		}

		// mBitmap.recycle();
	}

	public File createAppDirectoy(String DirectoryName) {
		File _sdCard = getSdCardPath();

		File _picDir = new File(_sdCard, DirectoryName);

		if (_picDir.isDirectory()) {

			return _picDir;
		} else {
			_picDir.mkdir();

			return _picDir;
		}
	}

	public File getSdCardPath() {
		File sdCardPath = null;
		sdCardPath = Environment.getExternalStorageDirectory();
		return sdCardPath;
	}

	/**
	 * Gets the resized bitmap.
	 * 
	 * @param bm
	 *            the bm
	 * @param newHeight
	 *            the new height
	 * @param newWidth
	 *            the new width
	 * @return the resized bitmap
	 */

	public boolean deleteNon_EmptyDir(File dir) {

		if (dir.isDirectory()) {

			String[] children = dir.list();

			if (children != null && children.length > 0) {

				for (int i = 0; i < children.length; i++) {
					boolean success = deleteNon_EmptyDir(new File(dir,
							children[i]));

					if (!success) {
						return false;
					}
				}
			}

		}
		return dir.delete();
	}

	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

		if (bm == null)
			return null;
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);
		// RECREATE THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);
		return resizedBitmap;
	}

	public File createDirectory(String directoryname) {
		createdirectoty = new File(Environment.getExternalStorageDirectory()
				+ "/" + directoryname);
		if (!createdirectoty.exists()) {
			createdirectoty.mkdir();

		}

		return createdirectoty;
	}

	public File createFile(String directoryname, String filename) {

		createdirectoty = new File(Environment.getExternalStorageDirectory()
				+ "/" + directoryname);

		if (!createdirectoty.exists()) {
			createdirectoty.mkdir();

			createdFileName = new File(createdirectoty, filename);

		} else {

			createdFileName = new File(createdirectoty, filename);

		}
		return createdFileName;
	}

	public File createFileInSideDirectory(File directoryname, String filename) {
		File createDirectoryName = directoryname;
		File createdFileName;

		if (!createDirectoryName.isDirectory()) {
			createDirectoryName.mkdir();

			createdFileName = new File(createDirectoryName, filename);

		} else {

			createdFileName = new File(createDirectoryName, filename);

		}
		return createdFileName;
	}

	public File createFileAndDeleteExistingFile(String directoryname,
			String filename) {

		createdirectoty = new File(Environment.getExternalStorageDirectory()
				+ "/" + directoryname);

		if (!createdirectoty.exists()) {
			createdirectoty.mkdir();
			createdFileName = new File(createdirectoty, filename);
		} else {
			File[] FileArray = createdirectoty.listFiles();
			if (FileArray != null && FileArray.length > 0) {
				FileArray[0].delete();
			}
			createdFileName = new File(createdirectoty, filename);

			System.out.println("my signed file is from else block  is  "
					+ createdFileName);

		}
		return createdFileName;
	}

	public File createFile(File directoryname, String filename) {

		// createdirectoty = new File(Environment.getExternalStorageDirectory()
		// + "/"+directoryname);
		createdFileName = new File(directoryname, filename);

		return createdFileName;
	}

	public File isFileExist(String directoryName, String FileName) {
		File mfiFile = null;
		File sdcardPath = getSdCardPath();
		File createdirectoty = new File(
				Environment.getExternalStorageDirectory() + "/" + directoryName);
		if (createdirectoty.exists()) {
			mfiFile = new File(createdirectoty, FileName);
			if (mfiFile.isFile()) {

			} else {
				mfiFile = null;
			}
		} else {
			mfiFile = null;
		}
		return mfiFile;
	}

	public void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	/**
	 * Load image.
	 * 
	 * @param URL
	 *            the uRL
	 * @param options
	 *            the options
	 * @return the bitmap
	 */
	private Bitmap LoadImage(String URL, BitmapFactory.Options options) {
		// Log.i(TAG, "LoadImage");
		// Log.i(TAG, "LoadImage  URL "+URL);
		// Log.i(TAG, "LoadImage  options "+options);
		Bitmap bitmap = null;
		InputStream in = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			in = OpenHttpConnection(URL);
			// Log.i(TAG, "LoadImage  in "+in);
			bufferedInputStream = new BufferedInputStream(in);
			bitmap = BitmapFactory.decodeStream(bufferedInputStream, null,
					options);
			// Log.i(TAG, "LoadImage  bitmap "+bitmap);
			if (in != null) {
				in.close();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
			// Log.i(TAG, "LoadImage  IOException "+e1);
		}

		return bitmap;
	}

	/**
	 * M lock screen rotation.
	 * 
	 * @param activity
	 *            the activity
	 */
	public void mLockScreenRotation(Activity activity) {
		// Stop the screen orientation changing during an event
		switch (activity.getResources().getConfiguration().orientation) {
		case Configuration.ORIENTATION_PORTRAIT:
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;
		case Configuration.ORIENTATION_LANDSCAPE:
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			break;
		}
	}

	/**
	 * Open http connection.
	 * 
	 * @param strURL
	 *            the str url
	 * @return the input stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private InputStream OpenHttpConnection(String strURL) throws IOException {
		InputStream inputStream = null;
		URL url = new URL(strURL);
		URLConnection conn = url.openConnection();

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod("GET");
			httpConn.connect();

			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = httpConn.getInputStream();
			}
		} catch (Exception ex) {
		}

		return inputStream;
	}

	/**
	 * Show progress dialog.
	 * 
	 * @param mContext
	 *            the m context
	 * @return the dialog
	 */
	public Dialog showProgressDialog(Activity mContext) {
		Dialog mDialog = new Dialog(mContext,
				android.R.style.Theme_Translucent_NoTitleBar);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View layout = mInflater.inflate(R.layout.popup_example, null);
		mDialog.setContentView(layout);
		TextView text = (TextView) layout.findViewById(R.id.text);
		mDialog.setCancelable(false);
		// aiImage.post(new Starter(activityIndicator));
		return mDialog;
	}

	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager connectivity = null;
		boolean isNetworkAvail = false;

		try {
			connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (connectivity != null) {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();

				if (info != null) {
					for (int i = 0; i < info.length; i++) {
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {

							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connectivity != null) {
				connectivity = null;
			}
		}
		return isNetworkAvail;
	}

	public boolean isSdcardAvailable() {
		boolean flag = android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		return flag;
	}

	/**
	 * Show progress dialog.
	 * 
	 * @param mContext
	 *            the m context
	 * @return the dialog
	 */
	/*
	 * public Dialog showDialogShareFacebook(final Activity mContext, final
	 * String subject, final String body, final String image, final String
	 * appLink) { final Dialog mDialog = new Dialog(mContext,
	 * android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
	 * mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * 
	 * LayoutInflater mInflater = LayoutInflater.from(mContext); View layout =
	 * mInflater.inflate(R.layout.dialog_transparent, null);
	 * mDialog.setContentView(layout); mDialog.setCancelable(true); Button
	 * btn_share_email = (Button) layout .findViewById(R.id.btn_share_email);
	 * Button btn_share_facebook = (Button) layout
	 * .findViewById(R.id.btn_share_facebook); Button btn_share_ok = (Button)
	 * layout.findViewById(R.id.btn_ok);
	 * 
	 * btn_share_email.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) {
	 * 
	 * Intent sharingIntent = new Intent(Intent.ACTION_SEND);
	 * sharingIntent.setType("plain/text");
	 * sharingIntent.putExtra(Intent.EXTRA_CC, "tuanedgeworks@gmail.com");
	 * sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Art item not present");
	 * sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, appLink);
	 * sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
	 * 
	 * mContext.startActivity(Intent.createChooser(sharingIntent,
	 * "Share using"));
	 * 
	 * mDialog.cancel(); } });
	 * 
	 * btn_share_facebook.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) {
	 * 
	 * } });
	 * 
	 * btn_share_ok.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { mDialog.cancel(); } });
	 * 
	 * return mDialog; }
	 */

	/**
	 * Gets the data source.
	 * 
	 * @param path
	 *            the path
	 * @return the data source
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getDataSource(String path) throws IOException {
		if (!URLUtil.isNetworkUrl(path)) {
			return path;
		} else {
			URL url = new URL(path);
			URLConnection cn = url.openConnection();
			cn.connect();
			InputStream stream = cn.getInputStream();
			if (stream == null)
				throw new RuntimeException("stream is null");
			File temp = File.createTempFile("mediaplayertmp", "mp3");
			temp.deleteOnExit();
			String tempPath = temp.getAbsolutePath();
			FileOutputStream out = new FileOutputStream(temp);
			byte buf[] = new byte[128];
			do {
				int numread = stream.read(buf);
				if (numread <= 0)
					break;
				out.write(buf, 0, numread);
			} while (true);
			try {
				stream.close();
			} catch (IOException ex) {
				Log.e("Error", "error: " + ex.getMessage(), ex);
			}
			return tempPath;
		}
	}

	/**
	 * Show dialog confirm.
	 * 
	 * @param activity
	 *            the activity
	 * @param title
	 *            the title
	 * @param message
	 *            the message
	 * @param flag
	 *            the flag
	 * @return the alert dialog
	 */
	public AlertDialog showDialogConfirm(final Activity activity, String title,
			String message, final boolean flag) {
		AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
		// activity.getWindow().setBackgroundDrawableResource(R.color.orange);
		// alertDialog.setIcon(R.drawable.dialog_icon);
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				/*
				 * if (flag) { //activity.finish(); } else { return; }
				 */

			}
		});
		alertDialog.setCancelable(false);
		return alertDialog;
	}

	public Bitmap getBitmapFromSdcard(String path) {
		// Log.i("showImage","loading:"+path);
		BitmapFactory.Options bfOptions = new BitmapFactory.Options();
		bfOptions.inDither = false; // Disable Dithering mode
		bfOptions.inPurgeable = true; // Tell to gc that whether it needs free
										// memory, the Bitmap can be cleared
		bfOptions.inInputShareable = true; // Which kind of reference will be
											// used to recover the Bitmap data
											// after being clear, when it will
											// be used in the future
		bfOptions.inTempStorage = new byte[32 * 1024];
		Bitmap mbitmap = null;

		File file = new File(path);
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		try {
			if (fs != null)
				mbitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null,
						bfOptions);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		// bm=BitmapFactory.decodeFile(path, bfOptions); This one causes error:
		// java.lang.OutOfMemoryError: bitmap size exceeds VM budget

		// im.setImageBitmap(bm);
		// bm.recycle();
		// bm=null;
		return mbitmap;

	}

	public Bitmap getBitmapfromuri(Uri imageUri, Activity activity) {
		Bitmap mBitmap = null;
		if (imageUri != null) {
			try {
				mBitmap = MediaStore.Images.Media.getBitmap(
						activity.getContentResolver(), imageUri);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				mBitmap = null;
			} catch (IOException e) {
				e.printStackTrace();
				mBitmap = null;
			}
			// Update UI to reflect image being shared
		}
		return mBitmap;
	}

	/*
	 * public AlertDialog showDialogConfirmScavenger(final Activity activity,
	 * String title, String message, final boolean flag) { AlertDialog
	 * alertDialog = new AlertDialog.Builder(activity).create(); //
	 * activity.getWindow().setBackgroundDrawableResource(R.color.orange);
	 * alertDialog.setIcon(R.drawable.dialog_icon); alertDialog.setTitle(title);
	 * alertDialog.setMessage(message); alertDialog.setButton("OK", new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) {
	 * Application apps = (Application) activity.getApplication(); Chronometer
	 * time = new Chronometer(activity); int stoppedMilliseconds = 0; String
	 * chronoText = apps.getChronoText();
	 * 
	 * if (flag) { //
	 * ===================================================================== //
	 * counting timer String array[] = chronoText.split(":"); if (array.length
	 * == 2) { stoppedMilliseconds = Integer.parseInt(array[0]) * 60 1000 +
	 * (Integer.parseInt(array[1]) + 1) 1000; } else if (array.length == 3) {
	 * stoppedMilliseconds = Integer.parseInt(array[0]) * 60 60 * 1000 +
	 * Integer.parseInt(array[1]) * 60 1000 + (Integer.parseInt(array[2]) + 1)
	 * 1000; }
	 * 
	 * time.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
	 * 
	 * time.start(); //
	 * =======================================================================
	 * 
	 * Intent intent = new Intent(activity, FinalScoreScavengerActivity.class);
	 * activity.startActivity(intent);
	 * activity.overridePendingTransition(R.anim.slide_left, 0);
	 * 
	 * apps.setChronoText(time.getText().toString());
	 * 
	 * } else { //
	 * ===================================================================== //
	 * counting timer String array[] = chronoText.split(":"); if (array.length
	 * == 2) { stoppedMilliseconds = Integer.parseInt(array[0]) * 60 1000 +
	 * (Integer.parseInt(array[1]) + 1) 1000; } else if (array.length == 3) {
	 * stoppedMilliseconds = Integer.parseInt(array[0]) * 60 60 * 1000 +
	 * Integer.parseInt(array[1]) * 60 1000 + (Integer.parseInt(array[2]) + 1)
	 * 1000; }
	 * 
	 * time.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
	 * 
	 * time.start(); //
	 * =======================================================================
	 * Intent intentDescriptions = new Intent(activity,
	 * GameDescriptionActivity.class);
	 * activity.startActivity(intentDescriptions);
	 * activity.overridePendingTransition(R.anim.slide_left, 0);
	 * apps.setChronoText(time.getText().toString()); }
	 * 
	 * } }); return alertDialog; }
	 */
	/**
	 * Show dialog final clue.
	 * 
	 * @param activity
	 *            the activity
	 * @param title
	 *            the title
	 * @param message
	 *            the message
	 * @return the alert dialog
	 */
	/*
	 * public AlertDialog showDialogFinalClue(final Activity activity, String
	 * title, String message) { AlertDialog alertDialog = new
	 * AlertDialog.Builder(activity).create();
	 * activity.getWindow().setBackgroundDrawableResource(R.color.orange);
	 * alertDialog.setIcon(R.drawable.dialog_icon); alertDialog.setTitle(title);
	 * alertDialog.setMessage(message); alertDialog.setButton("OK", new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) { //
	 * Intent it=new Intent(activity,FinalScoreActivity.class); //
	 * it.putExtra("gameId", gameId); // activity.startActivity(it);
	 * dialog.dismiss();
	 * 
	 * } }); return alertDialog; }
	 */
	/**
	 * Show dialog search.
	 * 
	 * @param activity
	 *            the activity
	 * @return the dialog
	 */
	/*
	 * public Dialog showDialogShare(Activity activity) { // set up dialog final
	 * Dialog dialog = new Dialog(activity);
	 * dialog.setContentView(R.layout.dialog_share_option);
	 * dialog.setCancelable(true);
	 * 
	 * final WheelView share = (WheelView) dialog.findViewById(R.id.shares);
	 * String arrShare[] = new String[] { "Share Facebook", "Share Email" };
	 * share.setViewAdapter(new DateArrayAdapter(activity, arrShare, 0));
	 * 
	 * // set up image view // Button button = (Button)
	 * dialog.findViewById(R.id.button1); Button btn_done = (Button)
	 * dialog.findViewById(R.id.btn_done); btn_done.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View v) {
	 * 
	 * dialog.cancel(); } }); // now that the dialog is set up, it's time to
	 * show it return dialog; }
	 */

	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	/*
	 * private class DateArrayAdapter extends ArrayWheelAdapter<String>
	 * implements WheelViewAdapter { // Index of current item
	 *//** The current item. */
	/*
	 * int currentItem; // Index of item to be highlighted
	 *//** The current value. */
	/*
	 * int currentValue;
	 *//**
	 * Constructor.
	 * 
	 * @param context
	 *            the context
	 * @param items
	 *            the items
	 * @param current
	 *            the current
	 */
	/*
	 * public DateArrayAdapter(Context context, String[] items, int current) {
	 * super(context, items); this.currentValue = current; setTextSize(16); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.museum.wheel.widget.adapters.AbstractWheelTextAdapter#
	 * configureTextView(android.widget.TextView)
	 */
	/*
	 * protected void configureTextView(TextView view) {
	 * super.configureTextView(view); // if (currentItem == currentValue) { //
	 * view.setTextColor(0xFF0000F0); // }
	 * view.setTypeface(Typeface.SANS_SERIF); view.setLineSpacing(6, 1);
	 * view.setTextSize(25); }
	 */

	public String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	// Function to get the time out
	/**
	 * Give me time.
	 * 
	 * @param crono
	 *            the crono
	 * @return the long
	 */
	public long giveMeTime(Chronometer crono) {
		long allMilli = SystemClock.elapsedRealtime() - crono.getBase();
		return allMilli;
	}

	public String getCurrentDate() {
		String mdatestr = null;
		Calendar c = Calendar.getInstance();

		SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
		mdatestr = date.format(c.getTime());
		return mdatestr;

		// "yyyy-MM-dd HH:mm:ss"
	}

	public String getCurrentDateYYYYMMdd() {
		String mdatestr = null;
		Calendar c = Calendar.getInstance();

		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mdatestr = date.format(c.getTime());
		return mdatestr;

	}

	public String getCurrentDateOnly() {
		String mdatestr = null;
		Calendar c = Calendar.getInstance();

		SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
		mdatestr = date.format(c.getTime());
		return mdatestr;
	}

	public String getAppVersion(Activity activity) {
		PackageManager manager = activity.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(activity.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		String version = info.versionName;
		return version;
	}

	public String getNextYearDate() {
		String nextYeardate = null;

		Calendar date = Calendar.getInstance();
		date.setTime(new Date());
		Format f = new SimpleDateFormat("dd-MMMM-yyyy");
		System.out.println(f.format(date.getTime()));
		date.add(Calendar.YEAR, 1);
		System.out.println(f.format(date.getTime()));
		nextYeardate = f.format(date.getTime());
		return nextYeardate;
	}

	/*
	 * public Dialog showConfirmDialog(Activity activity, String title, String
	 * message) { Dialog dialog = null; CustomDialog.Builder customBuilder = new
	 * CustomDialog.Builder(activity);
	 * customBuilder.setTitle(title).setMessage(message)
	 * .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) {
	 * 
	 * dialog.dismiss(); } }); dialog = customBuilder.create(); return dialog; }
	 */

	public static boolean isInternetAvalible(Context context) {
		final ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null
				&& activeNetwork.getState() == NetworkInfo.State.CONNECTED) {
			System.out.println("Connected...");
			return true;
		}
		return false;
	}

	public static void cleanUpActivity() {
		for (Activity activity : cleanupList) {
			activity.finish();
		}
		cleanupList.clear();
	}

	public Bitmap decodeImage(File f) {
		Bitmap b = null;
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;

			FileInputStream fis = new FileInputStream(f);
			BitmapFactory.decodeStream(fis, null, o);
			fis.close();

			float sc = 0.0f;
			int scale = 1;
			// if image height is greater than width
			if (o.outHeight > o.outWidth) {
				sc = o.outHeight / 400;
				scale = Math.round(sc);
			}
			// if image width is greater than height
			else {
				sc = o.outWidth / 400;
				scale = Math.round(sc);
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			fis = new FileInputStream(f);
			b = BitmapFactory.decodeStream(fis, null, o2);
			fis.close();
		} catch (IOException e) {
		}
		return b;
	}

	public String getDate(String datestr, int dateid) {
		/* yyyy-mm-dd */
		switch (dateid) {
		case 0:
			inFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				date = inFormat.parse(datestr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			outFormat = new SimpleDateFormat("dd/MM/yy");
			datestr = outFormat.format(date);
			// tvDisplayDate.setText(datestr);
			break;
		case 1:
			inFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				date = inFormat.parse(datestr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			outFormat = new SimpleDateFormat("MM/dd/yy");
			datestr = outFormat.format(date);
			// tvDisplayDate.setText(datestr);

			break;
		case 2:
			inFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				date = inFormat.parse(datestr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			outFormat = new SimpleDateFormat("DD/MM/YYYY");
			datestr = outFormat.format(date);
			// tvDisplayDate.setText(datestr);
			// detailvaluetextview.setTextC
			break;
		case 3:
			inFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				date = inFormat.parse(datestr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			outFormat = new SimpleDateFormat("MM/DD/YYYY");
			datestr = outFormat.format(date);
			// tvDisplayDate.setText(datestr);

			break;
		case 4:
			inFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				date = inFormat.parse(datestr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			outFormat = new SimpleDateFormat("dd MMM, yyyy ");
			datestr = outFormat.format(date);
			// tvDisplayDate.setText(datestr);

			break;
		case 5:
			inFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				date = inFormat.parse(datestr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			outFormat = new SimpleDateFormat("E, MMMM dd, yyyy");
			datestr = outFormat.format(date);
			// tvDisplayDate.setText(datestr);
			break;
		case 6:
			inFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				date = inFormat.parse(datestr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			outFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
			datestr = outFormat.format(date);
			// tvDisplayDate.setText(datestr);

			break;
		case 7:

			/* 06/26/1987 */

			inFormat = new SimpleDateFormat("MM/dd/yy");

			try {
				date = inFormat.parse(datestr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			datestr = outFormat.format(date);

			/* 1987-06-26 00:00:00.000 */

			break;
		}
		return datestr;
	}

	// public int displayHeight(Activity activity)
	// {
	// DisplayMetrics metrics = new DisplayMetrics();
	// activity.getWindowManager().getDefaultDisplay()
	// .getMetrics(metrics);
	// screenHeight = metrics.heightPixels;
	// screenWidth = metrics.widthPixels;
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// topbarheight=50+32;
	// System.out.println("mdpi   top margin is "+topbarheight);
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	// {
	// topbarheight=38+24;
	// //topmargingfortext=20;
	// System.out.println(" ldpi  top margin is "+topbarheight);
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	// // topbarheight=75+48;
	// topbarheight=113;
	// System.out.println("hdpi  top margin is "+topbarheight);
	// }
	// else
	// {
	// // topbarheight=75+48;
	// topbarheight=113;
	// System.out.println("xhdpi  top margin is "+topbarheight);
	// }
	// return topbarheight;
	// }

	// public int getMarginForPuchaseList(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=0;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=0;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=0;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=30;
	//
	// }
	// else
	// {
	// topMatgin=30;
	// }
	// return topMatgin;
	// }

	// public int getTopMarginForPuchaseList(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=12;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=10;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=15;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=19;
	//
	// }
	// else
	// {
	// topMatgin=19;
	// }
	// return topMatgin;
	// }

	// public int getLeftTopMarginForAddCategoryActivity(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=20;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=30;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=40;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=60;
	//
	// }
	// else
	// {
	// topMatgin=50;
	// }
	// return topMatgin;
	// }

	// public int[] getHeightAndWidthForToggleButton(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int imagehiegth;
	// int imagewidth;
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// imagehiegth=22;
	// imagewidth=22;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// imagehiegth=17;
	// imagewidth=17;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// imagehiegth=32;
	// imagewidth=32;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	//
	// Log.d(TAG, "getImageHeightAndWidth xdpi");
	// imagehiegth=48;
	// imagewidth=48;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// else
	// {
	// imagehiegth=48;
	// imagewidth=48;
	// imageHeightAndWidth[0]=imagehiegth;
	// imageHeightAndWidth[1]=imagewidth;
	// }
	// return imageHeightAndWidth;
	// }

	// public int getLeftMarginFOrHeading(Activity activity)
	// {
	// //Log.i(TAG, "getImageHeightAndWidth");
	//
	// int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// //Log.i(TAG, "getImageHeightAndWidth  screenHeight "+screenHeight);
	// //Log.i(TAG, "getImageHeightAndWidth  screenWidth  "+screenWidth);
	// int topMatgin=0;
	//
	// if ((screenHeight <= 500 && screenHeight >= 480)&& (screenWidth <= 340 &&
	// screenWidth >= 300))
	// {
	// //Log.i(TAG, "getImageHeightAndWidth mdpi");
	// topMatgin=20;
	//
	// }
	//
	// else if ((screenHeight <= 400 && screenHeight >= 300)&& (screenWidth <=
	// 240 && screenWidth >= 220))
	//
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth ldpi");
	// topMatgin=25;
	//
	// }
	//
	// else if ((screenHeight <= 840 && screenHeight >= 780)&& (screenWidth <=
	// 500 && screenWidth >= 440))
	// {
	//
	// //Log.i(TAG, "getImageHeightAndWidth hdpi");
	// topMatgin=30;
	//
	// }
	// else if ((screenHeight <= 1280 && screenHeight >= 840)&& (screenWidth <=
	// 720 && screenWidth >= 500))
	// {
	// topMatgin=40;
	//
	// }
	// else
	// {
	// topMatgin=40;
	// }
	// return topMatgin;
	// }

	// public int[] getLeftRightMarginForSeekBar(Activity activity)
	// {
	// ////Log.i(TAG, "getImageHeightAndWidth");
	//
	// int imageHeightAndWidth[]= new int[2];
	// int screenHeight = getHeight(activity);
	// int screenWidth=getWidth(activity);
	// Log.i(TAG,
	// "getHeightAndWidthOfSignaturePanel  screenHeight "+screenHeight);
	// Log.i(TAG,
	// "getHeightAndWidthOfSignaturePanel  screenWidth  "+screenWidth);
	// int leftMargin;
	// int rightMargin;
	// if ((screenWidth <= 500 && screenWidth >= 480)&& (screenHeight <= 340 &&
	// screenHeight >= 300))
	// {
	// ////Log.i(TAG, "getImageHeightAndWidth mdpi");
	// leftMargin= 10;
	// rightMargin=171;
	// imageHeightAndWidth[0]=leftMargin;
	// imageHeightAndWidth[1]=rightMargin;
	//
	// }
	//
	// else if ((screenWidth <= 400 && screenWidth >= 300)&& (screenHeight <=
	// 240 && screenHeight >= 220))
	//
	// {
	//
	// Log.i(TAG, "getHeightAndWidthOfSignaturePanel ldpi");
	// leftMargin= 10;
	// rightMargin=138;
	// imageHeightAndWidth[0]=leftMargin;
	// imageHeightAndWidth[1]=rightMargin;
	// }
	//
	// else if ((screenWidth <= 840 && screenWidth >= 780)&& (screenHeight <=
	// 500 && screenHeight >= 440))
	// {
	//
	// ////Log.i(TAG, "getImageHeightAndWidth hdpi");
	// leftMargin=10;
	// rightMargin=256;
	// imageHeightAndWidth[0]=leftMargin;
	// imageHeightAndWidth[1]=rightMargin;
	// }
	// else if ((screenWidth <= 1280 && screenWidth >= 840)&& (screenHeight <=
	// 720 && screenHeight >= 500))
	// {
	//
	// ////Log.i(TAG, "getImageHeightAndWidth xdpi");
	// leftMargin=10;
	// rightMargin=385;
	// imageHeightAndWidth[0]=leftMargin;
	// imageHeightAndWidth[1]=rightMargin;
	// }
	// else
	// {
	// leftMargin=10;
	// rightMargin=385;
	// imageHeightAndWidth[0]=leftMargin;
	// imageHeightAndWidth[1]=rightMargin;
	// }
	//
	// return imageHeightAndWidth;
	// }

	public RelativeLayout.LayoutParams getRelativelayoutParams(int width,
			int height) {
		RelativeLayout.LayoutParams lp = null;

		lp = new RelativeLayout.LayoutParams(/*
											 * RelativeLayout.LayoutParams.
											 * WRAP_CONTENT
											 */width, /*
													 * RelativeLayout.LayoutParams
													 * .WRAP_CONTENT
													 */height);

		return lp;
	}

}
