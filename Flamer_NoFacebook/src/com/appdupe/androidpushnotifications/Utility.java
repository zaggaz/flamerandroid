package com.appdupe.androidpushnotifications;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class Utility {
	private static String TAG = "Utility";
	public static int CONSTANT_FOR_ANDROID = 2;

	public static String convertURL(String str) {

		String url = null;
		try {
			url = new String(str.trim().replace(" ", "%20").replace("&", "%26")
					.replace(",", "%2c").replace("(", "%28")
					.replace(")", "%29").replace("!", "%21")
					.replace("=", "%3D").replace("<", "%3C")
					.replace(">", "%3E").replace("#", "%23")
					.replace("$", "%24").replace("'", "%27")
					.replace("*", "%2A").replace("-", "%2D")
					.replace(".", "%2E").replace("/", "%2F")
					.replace(":", "%3A").replace(";", "%3B")
					.replace("?", "%3F").replace("@", "%40")
					.replace("[", "%5B").replace("\\", "%5C")
					.replace("]", "%5D").replace("_", "%5F")
					.replace("`", "%60").replace("{", "%7B")
					.replace("|", "%7C").replace("}", "%7D"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	public static String callhttpRequest(String url) {
		System.out.println("utility url..." + url);
		url = url.replaceAll(" ", "%20");
		String resp = null;
		HttpGet httpRequest;
		try {
			httpRequest = new HttpGet(url);
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

	public String makeHttpRequest(String url, String method,
			List<NameValuePair> params) {

		InputStream is = null;
		// Making HTTP request
		try {

			// check for request method
			if (method == "POST") {
				// request method is POST
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				System.out.println("--------Orignal URL-------" + params);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

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
			Log.i(TAG, "makeHttpRequest  resposns  " + sb.toString());
			// logDebug("makeHttpRequest  resposns  "+sb.toString());
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

	public List<NameValuePair> getSendMessageReq(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		// namevaluepairs.add(new BasicNameValuePair("ent_sess_token",
		// params[0]));
		// namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
		namevaluepairs.add(new BasicNameValuePair("ent_user_fbid", params[0]));
		namevaluepairs.add(new BasicNameValuePair("ent_user_recever_fbid",
				params[1]));
		namevaluepairs.add(new BasicNameValuePair("ent_message", params[2]));

		/*
		 * logDebug("getUserProfileParameter  ent_sess_token "+params[0]);
		 * logDebug("getUserProfileParameter  ent_dev_id "+params[1]);
		 * logDebug("getUserProfileParameter  ent_user_fbid "+params[2]);
		 */

		// ent_image_flag

		return namevaluepairs;
	}

	public List<NameValuePair> getPullMessageReq(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		// namevaluepairs.add(new BasicNameValuePair("ent_sess_token",
		// params[0]));
		// namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
		// namevaluepairs.add(new BasicNameValuePair("ent_msg_id", params[2]));

		namevaluepairs.add(new BasicNameValuePair("ent_user_fbid", params[0]));
		namevaluepairs.add(new BasicNameValuePair("ent_user_recever_fbid",
				params[1]));

		// namevaluepairs.add(new BasicNameValuePair("ent_message",params[3]));

		/*
		 * logDebug("getUserProfileParameter  ent_sess_token "+params[0]);
		 * logDebug("getUserProfileParameter  ent_dev_id "+params[1]);
		 * logDebug("getUserProfileParameter  ent_user_fbid "+params[2]);
		 */

		// ent_image_flag

		return namevaluepairs;
	}

	public List<NameValuePair> getPullHistoryReq(String[] params) {
		List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
		namevaluepairs.add(new BasicNameValuePair("ent_sess_token", params[0]));
		namevaluepairs.add(new BasicNameValuePair("ent_dev_id", params[1]));
		namevaluepairs.add(new BasicNameValuePair("ent_user_fbid", params[2]));
		namevaluepairs.add(new BasicNameValuePair("ent_chat_page", params[3]));

		/*
		 * logDebug("getUserProfileParameter  ent_sess_token "+params[0]);
		 * logDebug("getUserProfileParameter  ent_dev_id "+params[1]);
		 * logDebug("getUserProfileParameter  ent_user_fbid "+params[2]);
		 */

		// ent_image_flag

		return namevaluepairs;
	}

	/*
	 * public static String postData(String url,String []params,String
	 * number,String email) { // Create a new HttpClient and Post Header
	 * HttpClient httpclient = new DefaultHttpClient();
	 * System.out.println("postData url............"+url);
	 * System.out.println("postData params 0...."+params[0]);
	 * System.out.println("postData params 1...."+params[1]);
	 * System.out.println("postData params 2...."+params[2]);
	 * System.out.println("postData params 3...."+params[3]);
	 * System.out.println("postData params 4...."+params[4]);
	 * System.out.println("postData params 5...."+params[5]);
	 * System.out.println("postData params 6...."+params[6]);
	 * System.out.println("postData params 7...."+params[7]);
	 * 
	 * 
	 * HttpPost httppost = new HttpPost(url); JSONObject response=null; try { //
	 * Add your data List<NameValuePair> nameValuePairs = new
	 * ArrayList<NameValuePair>(); nameValuePairs.add(new
	 * BasicNameValuePair(params[0], params[1])); nameValuePairs.add(new
	 * BasicNameValuePair(params[2], params[3])); nameValuePairs.add(new
	 * BasicNameValuePair(params[4], number)); nameValuePairs.add(new
	 * BasicNameValuePair(params[6], email)); httppost.setEntity(new
	 * UrlEncodedFormEntity(nameValuePairs));
	 * 
	 * // Execute HTTP Post Request ResponseHandler<String> responseHandler=new
	 * BasicResponseHandler(); String responseBody =
	 * httpclient.execute(httppost, responseHandler); try { response=new
	 * JSONObject(responseBody); } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * } catch (ClientProtocolException e) { // TODO Auto-generated catch block
	 * } catch (IOException e) { // TODO Auto-generated catch block }
	 * Log.i("TAG", "post response........."+response); return
	 * response.toString(); }
	 */
	/*
	 * public static String postData(String url,String []params,String
	 * number,String email) { // Create a new HttpClient and Post Header
	 * HttpClient httpclient = new DefaultHttpClient(); HttpPost httppost = new
	 * HttpPost(url); String temp=null; try { // Add your data
	 * List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	 * nameValuePairs.add(new BasicNameValuePair(params[0], params[1]));
	 * nameValuePairs.add(new BasicNameValuePair(params[2], params[3]));
	 * nameValuePairs.add(new BasicNameValuePair(params[4], number));
	 * nameValuePairs.add(new BasicNameValuePair(params[6], email));
	 * httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 * 
	 * // Execute HTTP Post Request HttpResponse response =
	 * httpclient.execute(httppost); temp =
	 * EntityUtils.toString(response.getEntity());
	 * System.out.println("postData temp.........."+temp); } catch
	 * (ClientProtocolException e) { // TODO Auto-generated catch block } catch
	 * (IOException e) { // TODO Auto-generated catch block } return temp; }
	 */
	/**
	 * Do post.
	 * 
	 * @param url
	 *            the url
	 * @param kvPairs
	 *            the kv pairs
	 * @return the http response
	 * @throws ClientProtocolException
	 *             the client protocol exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static HttpResponse doPost(String url, Map<String, String> kvPairs)
			throws ClientProtocolException, IOException {
		// HttpClient httpclient = new DefaultHttpClient();

		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpClient httpclient = defaultHttpClient;

		HttpPost httppost = new HttpPost(url);

		if (kvPairs != null || kvPairs.isEmpty() == false) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
					kvPairs.size());
			String k, v;
			Iterator<String> itKeys = kvPairs.keySet().iterator();

			while (itKeys.hasNext()) {
				k = itKeys.next();
				v = kvPairs.get(k);
				nameValuePairs.add(new BasicNameValuePair(k, v));
			}

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		}

		HttpResponse response;
		response = httpclient.execute(httppost);
		Log.i("TAG", "doPost response........." + response);
		return response;
	}

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
				if (flag) {
					activity.finish();
				} else {
					return;
				}

			}
		});
		return alertDialog;
	}

	public AlertDialog.Builder showDialogForLogin(final Activity activity,
			String title, String message, final Intent intent,
			final boolean flag) {
		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				activity);
		// activity.getWindow().setBackgroundDrawableResource(R.color.orange);
		// alertDialog.setIcon(R.drawable.dialog_icon);
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		/*
		 * alertDialog.setButton("NO", new DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { if
		 * (flag) { activity.finish(); } else { return; } alertDialog.dismiss();
		 * } }); alertDialog.setButton("YES", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) {
		 * activity.startActivity(intent); alertDialog.dismiss(); } });
		 */
		alertDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						activity.startActivity(intent);
						dialog.dismiss();

					}
				});
		alertDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (flag) {
							/* activity.finish(); */
						} else {
							return;
						}
						dialog.dismiss();
					}

				});
		return alertDialog;
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

	public static boolean isEmailValidate(String emailid) {
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

	public static ProgressDialog GetProcessDialog(Activity activity) {
		// prepare the dialog box
		ProgressDialog dialog = new ProgressDialog(activity);
		// make the progress bar cancelable
		dialog.setCancelable(true);
		// set a message text
		dialog.setMessage("Loading...");

		// show it
		return dialog;
	}

	public static String getCurrentDateTimeString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String currentDateTimeString = dateFormat.format(date);
		return currentDateTimeString;
	}

	public static String getCurrentDateTimeStringGMT() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String currentDateTimeString = dateFormat.format(date);
		String currentDateTimeWithformat = Utility.changeDateTimeFormate(
				currentDateTimeString, "yyyy-MM-dd HH:mm:ss",
				"yyyy-MM-dd HH:mm:ss");
		Log.i("", "onActivityResult flight currentDateTimeWithformat..."
				+ currentDateTimeWithformat);
		Date currentDateTimeDate = Utility.convertStringIntoDate(
				currentDateTimeWithformat, "yyyy-MM-dd hh:mm:ss");
		Log.i("", "onActivityResult flight currentDateTimeDate..."
				+ currentDateTimeDate);
		String gmtDateTime = Utility.getLocalTimeToGMT(currentDateTimeDate);
		Log.i("", "onActivityResult gmtDateTime..............." + gmtDateTime);
		String currentDateTimeGMT = Utility.changeDateFormate(gmtDateTime,
				"MM/dd/yyyy HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
		Log.i("", "onActivityResult currentDateTimeGMT..............."
				+ currentDateTimeGMT);
		return currentDateTimeGMT;
	}

	/*
	 * public static String getCurrentDateTimeStringGMTForImage() { DateFormat
	 * dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date date = new
	 * Date(); String currentDateTimeString=dateFormat.format(date); String
	 * currentDateTimeWithformat
	 * =Utility.changeDateTimeFormate(currentDateTimeString
	 * ,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"); Log.i("",
	 * "onActivityResult flight currentDateTimeWithformat..." +
	 * currentDateTimeWithformat); Date
	 * currentDateTimeDate=Utility.convertStringIntoDate
	 * (currentDateTimeWithformat, "yyyy-MM-dd hh:mm:ss"); Log.i("",
	 * "onActivityResult flight currentDateTimeDate..." + currentDateTimeDate);
	 * String gmtDateTime=Utility.getLocalTimeToGMT(currentDateTimeDate);
	 * Log.i("", "onActivityResult gmtDateTime..............."+gmtDateTime);
	 * String currentDateTimeGMT=Utility.changeDateFormate(gmtDateTime,
	 * "MM/dd/yyyy HH:mm:ss","yyyy-MM-dd HH:mm:ss"); Log.i("",
	 * "onActivityResult currentDateTimeGMT..............."+currentDateTimeGMT);
	 * return currentDateTimeGMT; }
	 */
	public static String getCurrentDateYearMonthString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String currentDateTimeString = dateFormat.format(date);
		return currentDateTimeString;
	}

	public static Date convertStringIntoDate(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// String dateInString = "7-Jun-2013";
		System.out.println("dateString......." + dateString);
		Date date = null;

		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(date);
		System.out.println(formatter.format(date));

		return date;
	}

	public static Date convertStringIntoDate(String dateString,
			String inputFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(inputFormat);
		// String dateInString = "7-Jun-2013";
		System.out.println("dateString......." + dateString);
		Date date = null;

		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(date);
		System.out.println(formatter.format(date));

		return date;
	}

	public static String getLocalTimeToGMT(Date localTime, String outputFormat) {
		// Date will return local time in Java
		// Date localTime = new Date();

		// creating DateFormat for converting time from local timezone to GMT
		DateFormat converter = new SimpleDateFormat(outputFormat);

		// getting GMT timezone, you can get any timezone e.g. UTC
		converter.setTimeZone(TimeZone.getTimeZone("GMT"));

		System.out.println("local time : " + localTime);
		;
		System.out.println("time in GMT : " + converter.format(localTime));
		return converter.format(localTime);
		// Read more:
		// http://javarevisited.blogspot.com/2012/04/how-to-convert-local-time-to-gmt-in.html#ixzz2i5QriBRI
	}

	public static String convertDateIntoString(Date date) {
		// Create an instance of SimpleDateFormat used for formatting
		// the string representation of date (month/day/year)
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		// Get the date today using Calendar object.
		// Date today = Calendar.getInstance().getTime();
		// Using DateFormat format method we can create a string
		// representation of a date with the defined format.
		String reportDate = df.format(date);
		return reportDate;
	}

	public static String changeDateFormate(String inputDate,
			String inputFormate, String outputFormate) {
		// String dateStr = "Jul 27, 2011 8:35:29 AM";
		DateFormat readFormat = new SimpleDateFormat(inputFormate);
		DateFormat writeFormat = new SimpleDateFormat(outputFormate);
		Date date = null;
		try {
			date = readFormat.parse(inputDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			String formattedDate = writeFormat.format(date);
		}
		return writeFormat.format(date);

	}

	public static String changeDateTimeFormate(String inputDate,
			String inputFormat, String outFormate) {

		String time24 = null;
		try {
			// String now = new SimpleDateFormat("hh:mm aa").format(new
			// java.util.Date().getTime());
			System.out.println("onActivityResult time in 12 hour format : "
					+ inputDate);
			SimpleDateFormat inFormat = new SimpleDateFormat(inputFormat);
			SimpleDateFormat outFormat = new SimpleDateFormat(outFormate);
			time24 = outFormat.format(inFormat.parse(inputDate));
			System.out.println("onActivityResult time in 24 hour format : "
					+ time24);
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		return time24;

	}

	public int getWithOFAppRatreDialoge(Context activity) {
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
			topMatgin = 240;

		}

		else if ((screenHeight <= 400 && screenHeight >= 300)
				&& (screenWidth <= 240 && screenWidth >= 220))

		{

			// Log.i(TAG, "getImageHeightAndWidth ldpi");
			topMatgin = 200;

		}

		else if ((screenHeight <= 840 && screenHeight >= 780)
				&& (screenWidth <= 500 && screenWidth >= 440)) {

			// Log.i(TAG, "getImageHeightAndWidth hdpi");
			topMatgin = 400;

		} else if ((screenHeight <= 1280 && screenHeight >= 840)
				&& (screenWidth <= 720 && screenWidth >= 500)) {
			topMatgin = 600;

		} else if ((screenHeight <= 1920 && screenHeight >= 1280)
				&& (screenWidth <= 1080 && screenWidth >= 720)) {
			topMatgin = 700;
		} else {
			topMatgin = 700;
		}
		return topMatgin;
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

	public static String getLocalTimeToGMT(Date localTime) {
		// Date will return local time in Java
		// Date localTime = new Date();

		// creating DateFormat for converting time from local timezone to GMT
		DateFormat converter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		// getting GMT timezone, you can get any timezone e.g. UTC
		converter.setTimeZone(TimeZone.getTimeZone("GMT"));

		System.out.println("local time : " + localTime);
		;
		System.out.println("time in GMT : " + converter.format(localTime));
		return converter.format(localTime);
		// Read more:
		// http://javarevisited.blogspot.com/2012/04/how-to-convert-local-time-to-gmt-in.html#ixzz2i5QriBRI
	}

	public static Bitmap decodeUri(Uri selectedImage, Context con)
			throws FileNotFoundException {

		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(
				con.getContentResolver().openInputStream(selectedImage), null,
				o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 140;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
				break;
			}
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(con.getContentResolver()
				.openInputStream(selectedImage), null, o2);

	}

	public Bitmap getBitmapFromString(String image_URL) {
		Log.i(TAG, "getBitmapFromString ");
		Log.i(TAG, "getBitmapFromString  " + image_URL);
		image_URL = image_URL.replaceAll(" ", "%20");
		Bitmap srcBitmap;
		if (image_URL == null)
			return null;
		BitmapFactory.Options bmOptions;
		bmOptions = new BitmapFactory.Options();
		Log.i(TAG, "getBitmapFromString bmOptions " + bmOptions);
		bmOptions.inSampleSize = 1;
		return srcBitmap = LoadImage(image_URL, bmOptions);
	}

	private Bitmap LoadImage(String URL, BitmapFactory.Options options) {
		Log.i(TAG, "LoadImage");
		Log.i(TAG, "LoadImage  URL " + URL);
		Log.i(TAG, "LoadImage  options " + options);
		Bitmap bitmap = null;
		InputStream in = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			in = OpenHttpConnection(URL);
			Log.i(TAG, "LoadImage  in " + in);
			bufferedInputStream = new BufferedInputStream(in);
			bitmap = BitmapFactory.decodeStream(bufferedInputStream, null,
					options);
			Log.i(TAG, "LoadImage  bitmap " + bitmap);
			if (in != null) {
				in.close();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
			Log.i(TAG, "LoadImage  IOException " + e1);
		}

		return bitmap;
	}

	/**
	 * Gets the bitmap from string.
	 * 
	 * @param image_URL
	 *            the image_ url
	 * @return the bitmap from string
	 */
	/*
	 * public Bitmap getBitmapFromString(String image_URL) { Bitmap srcBitmap;
	 * if (image_URL == null) return null; BitmapFactory.Options bmOptions;
	 * bmOptions = new BitmapFactory.Options(); bmOptions.inSampleSize = 1;
	 * return srcBitmap = LoadImage(image_URL, bmOptions); }
	 */
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
	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

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

	public static float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}
	/*
	 * private boolean isEmailIdEnter(String name,EditText mEditText) { boolean
	 * nameisvalid = false; if (name != null && name.length() > 0) { nameisvalid
	 * = true; } else { nameisvalid = false; Animation shake =
	 * AnimationUtils.loadAnimation(this, R.anim.shake);
	 * mEditText.startAnimation(shake); Toast.makeText(this,
	 * getResources().getString(R.string.pleaseenteremailid),
	 * Toast.LENGTH_SHORT) .show(); } return nameisvalid; }
	 * 
	 * private boolean isPasswordEnter(String id,EditText mEditText) { boolean
	 * idisvalid = false; if (id != null && id.length() > 0) { idisvalid = true;
	 * } else { Animation shake = AnimationUtils.loadAnimation(this,
	 * R.anim.shake); mEditText.startAnimation(shake); idisvalid = false;
	 * Toast.makeText(this,
	 * getResources().getString(R.string.pleaseenterepassword),
	 * Toast.LENGTH_SHORT) .show(); } return idisvalid; }
	 * 
	 * public boolean isEmailValid(String email,EditText mEditText) { boolean
	 * isValid = false;
	 * 
	 * String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	 * CharSequence inputStr = email;
	 * 
	 * Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	 * Matcher matcher = pattern.matcher(inputStr); if (matcher.matches()) {
	 * isValid = true; } else { Animation shake =
	 * AnimationUtils.loadAnimation(this, R.anim.shake);
	 * mEditText.startAnimation(shake); Toast.makeText(this,
	 * getResources().getString
	 * (R.string.pleaseentervalidemailid),Toast.LENGTH_SHORT) .show(); } return
	 * isValid; }
	 */
	/*
	 * public static Dialog showProgressDialog(Activity mContext) { Dialog
	 * mDialog = new Dialog(mContext,
	 * android.R.style.Theme_Translucent_NoTitleBar);
	 * mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * 
	 * LayoutInflater mInflater = LayoutInflater.from(mContext); View layout =
	 * mInflater.inflate(R.layout.popup_example, null);
	 * mDialog.setContentView(layout);
	 * 
	 * mDialog.setCancelable(false); // aiImage.post(new
	 * Starter(activityIndicator)); return mDialog; }
	 */
}
