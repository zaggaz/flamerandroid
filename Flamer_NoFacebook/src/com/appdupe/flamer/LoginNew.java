package com.appdupe.flamer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.slidingmenu.MainActivity;
import com.appdupe.flamer.adapter.HomeAdapter;
import com.appdupe.flamer.model.FacebookData;
import com.appdupe.flamer.model.KeyValuePair;
import com.appdupe.flamer.utility.AlertDialogManager;
import com.appdupe.flamer.utility.AppLog;
import com.appdupe.flamer.utility.ConnectionDetector;
import com.appdupe.flamer.utility.Constant;
import com.appdupe.flamer.utility.GellaryData;
import com.appdupe.flamer.utility.HttpRequest;
import com.appdupe.flamer.utility.JsonParser;
import com.appdupe.flamer.utility.LocationFinder;
import com.appdupe.flamer.utility.LocationFinder.LocationResult;
import com.appdupe.flamer.utility.SessionManager;
import com.appdupe.flamer.utility.Utility;
import com.appdupe.flamernofb.R;
import com.facebook.FacebookActivity;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class LoginNew extends FacebookActivity implements OnClickListener {
	private static final String TAG = "LoginNew";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private ArrayList<GellaryData> imagelist;
	private LocationFinder newLocationFinder;
	private HomeAdapter mAdapterForGalery;
	private ExtendedGallery galleryforsuprvisore;
	private Resources res;
	private TypedArray imgs;
	private TextView[] page_text;
	private int count;
	private LinearLayout count_layout;
	private Animation animeBottomTOUp;
	private TextView txtPrivacy;
	private double mLatitude, mLongitude;
	private GoogleCloudMessaging gcm;
	private String regid;
	private ConnectionDetector cd;
	private String SENDER_ID = "775207507530";
	private SharedPreferences preferences;
	private Editor editor;
	private SessionManager mSessionManager;
	private Calendar mCalendar;
	FacebookData data;
	private Button btnLoginFacebook, btnLoginFacebook2;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initData();
		registerGCM();
		printKeyHash();
	}

	private void initData() {
		res = getResources();
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		mSessionManager = new SessionManager(this);
		editor = preferences.edit();
		galleryforsuprvisore = (ExtendedGallery) findViewById(R.id.gallery);
		txtPrivacy = (TextView) findViewById(R.id.txtLoginPrivacy);
		txtPrivacy.setOnClickListener(this);
		cd = new ConnectionDetector(this);
		btnLoginFacebook2 = (Button) findViewById(R.id.btnLoginFaceBook);
		btnLoginFacebook2.setOnClickListener(this);
		btnLoginFacebook = (Button) findViewById(R.id.loginwithfacebook);
		btnLoginFacebook.setOnClickListener(this);
		imagelist = new ArrayList<GellaryData>();
		count_layout = (LinearLayout) findViewById(R.id.image_count_homescreen);
		mAdapterForGalery = new HomeAdapter(this, imagelist);
		galleryforsuprvisore.setAdapter(mAdapterForGalery);
		getTemplateFromResource();

		galleryforsuprvisore
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						System.out.println("Item Selected Position=======>>>"
								+ pos);
						System.out
								.println("Item Selected Position=======>>>  count"
										+ count);
						for (int i = 0; i < count; i++) {
							page_text[i]
									.setTextColor(android.graphics.Color.GRAY);
						}
						page_text[pos]
								.setTextColor(android.graphics.Color.BLUE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
		animeBottomTOUp = AnimationUtils.loadAnimation(this,
				R.anim.helpscreen_in_to_out);
	}

	private void printKeyHash() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.i("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Exception(NameNotFoundException) : " + e);

		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "Exception(NoSuchAlgorithmException) : " + e);
		}

	}

	private void registerGCM() {
		if (cd.isConnectingToInternet()) {
			if (checkPlayServices()) {
				regid = getRegistrationId(this);
				if (regid.isEmpty()) {
					registerInBackground();
				} else {
					AppLog.Log(TAG, "reg id saved : " + regid);
				}
			} else {
				return;
			}
		} else {
			AlertDialogManager.internetConnetionErrorAlertDialog(LoginNew.this);
		}

	}

	private String getRegistrationId(Context context) {
		String registrationId = preferences.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			return "";
		}
		int registeredVersion = preferences.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			return "";
		}
		return registrationId;
	}

	private void getTemplateFromResource() {

		imgs = res.obtainTypedArray(R.array.galleryimage);

		for (int i = 0; i < imgs.length(); i++) {
			GellaryData gellaryData = new GellaryData();
			gellaryData.setResourceId(imgs.getResourceId(i, -1));
			imagelist.add(gellaryData);
		}
		page_text = new TextView[imgs.length()];
		count = imagelist.size();
		for (int i = 0; i < imagelist.size(); i++) {
			page_text[i] = new TextView(this);
			page_text[i].setTypeface(null, Typeface.BOLD);
			page_text[i].setText(".");
			page_text[i].setTextSize(25);
			page_text[i].setTextColor(android.graphics.Color.GRAY);
			count_layout.addView(page_text[i]);
		}
		mAdapterForGalery.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txtLoginPrivacy:
			findViewById(R.id.privacylayout).setVisibility(View.VISIBLE);
			/* extendedgalrylayout.set */
			findViewById(R.id.privacylayout).startAnimation(animeBottomTOUp);
			findViewById(R.id.llLoginTop).setVisibility(View.GONE);
			break;
		case R.id.btnLoginFaceBook:
		case R.id.loginwithfacebook:

			ConnectionDetector detector = new ConnectionDetector(this);
			if (detector.isConnectingToInternet()) {
				btnLoginFacebook.setEnabled(false);
				btnLoginFacebook2.setEnabled(false);
				btnLoginFacebook.setFocusable(false);
				btnLoginFacebook2.setFocusable(false);
				getUserDataFromFacebook();
			} else {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}
			break;
		default:
			break;
		}

	}

	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mCalendar.set(Calendar.YEAR, year);
			mCalendar.set(Calendar.MONTH, monthOfYear);
			mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			data.setBirthday(mCalendar.get(Calendar.YEAR) + "-"
					+ (mCalendar.get(Calendar.MONTH) + 1) + "-"
					+ mCalendar.get(Calendar.DAY_OF_MONTH));
			Calendar calendar = Calendar.getInstance();
			if (Integer.parseInt(calculateAge(mCalendar.get(Calendar.YEAR)
					+ "-" + (mCalendar.get(Calendar.MONTH) + 1) + "-"
					+ mCalendar.get(Calendar.DAY_OF_MONTH))) < 18) {
				Toast.makeText(LoginNew.this,
						"You should be a minimum of 18 yrs old to Sign Up",
						Toast.LENGTH_SHORT).show();
				return;

			}
			new Thread(new Runnable() {

				@Override
				public void run() {
					sendDataToServer(data);
				}
			}).start();

		}

	};

	public String calculateAge(String strDob) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(strDob));
			return String.valueOf(getAge(cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getAge(int _year, int _month, int _day) {

		GregorianCalendar cal = new GregorianCalendar();
		int y, m, d, a;
		y = cal.get(Calendar.YEAR);
		m = cal.get(Calendar.MONTH);
		d = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(_year, _month, _day);
		a = y - cal.get(Calendar.YEAR);
		if ((m < cal.get(Calendar.MONTH))
				|| ((m == cal.get(Calendar.MONTH)) && (d < cal
						.get(Calendar.DAY_OF_MONTH)))) {
			--a;
		}
		if (a < 0)
			throw new IllegalArgumentException("Age < 0");
		return a;
	}

	@Override
	protected void onSessionStateChange(SessionState state, Exception exception) {
		super.onSessionStateChange(state, exception);
		if (state.isOpened()) {
			sendReqToLoadDataFromFb();
		}
	}

	private void sendReqToLoadDataFromFb() {

		Callback callback = new Callback() {
			@Override
			public void onCompleted(Response response) {
				AppLog.Log(TAG, "Facebook Response ::" + response + "");
				loadFbImageInThred(response);
			}
		};

		String graphPath = "me";
		Bundle bundle = new Bundle();
		bundle.putString("fields",
				"id,picture.height(250),gender,first_name,last_name,age_range");

		Request request1 = new Request(Session.getActiveSession(), graphPath,
				bundle, HttpMethod.GET, callback);
		RequestAsyncTask task = Request.executeBatchAsync(request1);
		if (task == null) {
			AppLog.Log(TAG, "task is null");
		} else {
			AppLog.Log(TAG, task.getStatus() + "");
		}
	}

	private void getUserDataFromFacebook() {

		List<String> PERMISSIONS = new ArrayList<String>();
		PERMISSIONS.add("friends_about_me");
		PERMISSIONS.add("user_birthday");
		PERMISSIONS.add("user_activities");

		if (!isSessionOpen()) {
			try {
				openSession();
			} catch (Exception e) {
				e.printStackTrace();
				// Utils.removeSimpleProgressDialog();
			}
		} else {
			sendReqToLoadDataFromFb();
		}
	}

	private void loadFbImageInThred(final Response response) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					AppLog.Log(TAG, response.toString());
					GraphObject graphObject = response.getGraphObject();
					JSONObject jsonObject = new JSONObject(graphObject
							.getInnerJSONObject().toString());
					data = JsonParser.parseUserInfo(jsonObject);
					mCalendar = Calendar.getInstance();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							final DatePickerDialog birthdayDialog = new DatePickerDialog(
									LoginNew.this, date,
									mCalendar.get(Calendar.YEAR),
									mCalendar.get(Calendar.MONTH),
									mCalendar.get(Calendar.DAY_OF_MONTH));
							birthdayDialog.setTitle("Set Birthday");
							// birthdayDialog.setButton(
							// DialogInterface.BUTTON_NEGATIVE, "Cancel",
							// new DialogInterface.OnClickListener() {
							// @Override
							// public void onClick(
							// DialogInterface dialog,
							// int which) {
							//
							// birthdayDialog.cancel();
							// }
							// });
							birthdayDialog.setCancelable(false);
							birthdayDialog.setCanceledOnTouchOutside(false);
							birthdayDialog.show();

						}
					});

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	boolean isSend = false;

	protected void sendDataToServer(final FacebookData data) {
		if (isSend) {
			return;
		}
		isSend = true;
		AppLog.Log(TAG, "------------>>>>>Send Data<<<<<<<<<<-----------------");
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				btnLoginFacebook.setFocusable(true);
				btnLoginFacebook2.setFocusable(true);
				btnLoginFacebook.setEnabled(true);
				btnLoginFacebook2.setEnabled(true);
				Utility.showProcess(LoginNew.this, "Creating profile...");
			}
		});

		ArrayList<NameValuePair> keyValuePairs = new ArrayList<NameValuePair>();

		keyValuePairs.add(new KeyValuePair(Constant.KEY_FB_ID, data.getId()));
		keyValuePairs.add(new KeyValuePair(Constant.KEY_FIRST_NAME, data
				.getFirstName()));
		keyValuePairs.add(new KeyValuePair(Constant.KEY_LAST_NAME, data
				.getLastName()));
		if (data.getBirthday() != null) {
			keyValuePairs.add(new KeyValuePair(Constant.KEY_BIRTHDAY, data
					.getBirthday()));
		} else {
			keyValuePairs.add(new KeyValuePair(Constant.KEY_BIRTHDAY, ""));
		}
		keyValuePairs.add(new KeyValuePair(Constant.KEY_SEX, data.getSex()));
		keyValuePairs.add(new KeyValuePair(Constant.KEY_PUSH_TOKEN, regid));
		keyValuePairs.add(new KeyValuePair(Constant.KEY_CURRENT_LATITUDE,
				mLatitude + ""));
		keyValuePairs.add(new KeyValuePair(Constant.KEY_CURRENT_LONGITUDE,
				mLongitude + ""));
		keyValuePairs.add(new KeyValuePair(Constant.KEY_DEVICE_TYPE, "2"));
		AppLog.Log("TAG", "Sending data to server:" + Constant.KEY_FB_ID + " "
				+ data.getId() + " First " + data.getFirstName() + "  last "
				+ data.getLastName() + " birtday" + data.getBirthday()
				+ "profile pic:" + data.getProfilePicture());
		if (data.getProfilePicture() != null
				&& !data.getProfilePicture().equals("")) {
			keyValuePairs.add(new KeyValuePair(Constant.KEY_PROFILE, data
					.getProfilePicture()));
		} else {
			keyValuePairs.add(new KeyValuePair(Constant.KEY_PROFILE, ""));
		}

		HttpRequest httpRequest = new HttpRequest();
		try {
			String response = httpRequest.postData(Constant.login_url,
					keyValuePairs);
			FacebookData loginData = JsonParser.parseLoginResponse(response);
			AppLog.Log(TAG, "response From Registeration :: " + response);
			// Utils.removeSimpleProgressDialog();
			if (loginData != null) {
				// runOnUiThread(new Runnable() {
				//
				// @Override
				// public void run() {
				// Toast.makeText(
				// LoginNew.this,
				// "Name" + data.getFirstName() + " "
				// + data.getLastName() + " Birthday:"
				// + data.getBirthday(),
				// Toast.LENGTH_SHORT).show();
				//
				// }
				// });
				editor.putString(Constant.FACEBOOK_ID, data.getId());
				editor.putString(Constant.PREF_FIRST_NAME, data.getFirstName());
				editor.putString(Constant.PREF_LAST_NAME, data.getLastName());
				// editor.putString(Constant.PREF_PROFILE_IMAGE_ONE,
				// getStoredImageUrl("1", data.getProfilePicture()));
				editor.putString(Constant.PREF_PROFILE_IMAGE_ONE,
						loginData.getProfilePicture());
				editor.putInt(Constant.PREF_USER_AGE, loginData.getMinAge());
				editor.putString(Constant.PREF_USER_STATUS,
						loginData.getStatus());
				editor.commit();
				int minAge = 0, maxAge = 0;
				if (data.getMinAge() == 0) {
					minAge = 18;
				} else {
					minAge = data.getMinAge();
				}
				if (data.getMaxAge() == 0) {
					maxAge = 55;
				} else {
					maxAge = data.getMaxAge();
				}
				mSessionManager.setProFilePicture(data.getProfilePicture());
				mSessionManager.setFacebookId(data.getId());
				// mSessionManager.setUserHeigherAge("" + maxAge);
				// mSessionManager.setUserLowerAge("" + minAge);
				// mSessionManager.setDistaceUnit("Km");
				// mSessionManager.setDistance(100);
				// mSessionManager.setFacebookId("" + data.getId());
				// mSessionManager.setUserPrefSex("3");
				// mSessionManager.setUserSex(data.getSex());
				onRegisterationComplete();
			} else {
				cancelRegisteration();
			}
		} catch (Exception e) {
			e.printStackTrace();
			cancelRegisteration();
		}
	}

	private void cancelRegisteration() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Utility.closeprocess(LoginNew.this);
				Toast.makeText(LoginNew.this, "Some error Occured",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void onRegisterationComplete() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Utility.closeprocess(LoginNew.this);
				Intent intent = new Intent(LoginNew.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}

	@Override
	public void onBackPressed() {
		if (findViewById(R.id.privacylayout).getVisibility() == View.VISIBLE) {
			findViewById(R.id.privacylayout).setVisibility(View.GONE);
			findViewById(R.id.llLoginTop).setVisibility(View.VISIBLE);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (Session.getActiveSession().isOpened()) {
			AppLog.Log(TAG, "Sesstion is open");
			sendReqToLoadDataFromFb();
		} else {
			AppLog.Log(TAG, "Sesstion is close");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkPlayServices();
		LocationManager locationManagerresume = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (locationManagerresume
				.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			newLocationFinder = new LocationFinder();
			newLocationFinder.getLocation(LoginNew.this, mLocationResult);
		} else {
			showGPSDisabledAlertToUser();
		}
	}

	public boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {

				finish();
			}
			return false;
		}
		return true;
	}

	LocationResult mLocationResult = new LocationResult() {
		public void gotLocation(final double latitude, final double longitude) {
			System.out.println("Got Location...Lat:" + String.valueOf(latitude)
					+ "Long:" + String.valueOf(longitude));
			if (latitude == 0.0 || longitude == 0.0) {

				// ErrorMessageLocationNotFonr("Alert","Current Location not found please Switch on your GPS_PROVIDER or  NETWORK_PROVIDER");
				return;
			} else {
				runOnUiThread(new Runnable() {
					public void run() {
						mLatitude = latitude;
						mLongitude = longitude;
					}
				});
			}
		}
	};

	private void registerInBackground() {
		new GCMRegistration().execute();
	}

	private class GCMRegistration extends AsyncTask<String, Void, Void> {
		private boolean flagforresponse = true;
		private String[] params;

		@Override
		protected Void doInBackground(String... params) {
			String msg = "";
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(LoginNew.this);
					// logDebug("GCMRegistration  gcm "+gcm);
				}
				regid = gcm.register(SENDER_ID);
				String regidfoundseccessfully = "getGoogleRegistrationId";
				msg = "GCMRegistration doInBackground Device registered, registration ID="
						+ regid;
				storeRegistrationId(LoginNew.this, regid);
			} catch (IOException ex) {
				msg = "Error :" + ex.getMessage();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}
	}

	private void storeRegistrationId(Context context, String regId) {
		int appVersion = getAppVersion(context);
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	private void showGPSDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(
						"GPS is disabled in your device. Would you like to enable it?")
				.setCancelable(false)
				.setPositiveButton(R.string.button_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(callGPSSettingIntent);
							}
						});
		alertDialogBuilder.setNegativeButton(R.string.button_cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						finish();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	private void createIfNotDirectory() {
		File f = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "ShoppingList");
		if (f.exists()) {
			return;
		} else {
			f.mkdir();
		}

	}

	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected String getStoredImageUrl(String name, String image) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Bitmap bitmap = getBitmapFromURL(image);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		// you can create a new file name "test.jpg" in sdcard folder.
		File f = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "Meetr" + File.separator + name + ".jpg");

		createIfNotDirectory();

		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(stream.toByteArray());
			// remember close de FileOutput
			fo.close();
			return f.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;

	}

}
