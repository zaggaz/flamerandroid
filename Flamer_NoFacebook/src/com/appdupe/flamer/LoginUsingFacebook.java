/**
 * this class is responsible to login with facebook and fetch all the data from facebook
 */
package com.appdupe.flamer;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.slidingmenu.MainActivity;
import com.appdupe.flamer.pojo.AgeRange;
import com.appdupe.flamer.pojo.Hometown;
import com.appdupe.flamer.pojo.ImageDetail;
import com.appdupe.flamer.pojo.Location;
import com.appdupe.flamer.pojo.LoginData;
import com.appdupe.flamer.pojo.PickUrl;
import com.appdupe.flamer.pojo.UploadImage;
import com.appdupe.flamer.pojo.UserFaceBookInfo;
import com.appdupe.flamer.pojo.UserFaceBookLikeData;
import com.appdupe.flamer.pojo.UserFacebookLikeId;
import com.appdupe.flamer.pojo.UserProfilePick;
import com.appdupe.flamer.pojo.userProFileData;
import com.appdupe.flamer.utility.AlertDialogManager;
import com.appdupe.flamer.utility.AppLog;
import com.appdupe.flamer.utility.ConnectionDetector;
import com.appdupe.flamer.utility.Constant;
import com.appdupe.flamer.utility.GellaryData;
import com.appdupe.flamer.utility.LocationFinder;
import com.appdupe.flamer.utility.LocationFinder.LocationResult;
import com.appdupe.flamer.utility.SessionManager;
import com.appdupe.flamer.utility.Ultilities;
import com.appdupe.flamer.utility.Utility;
import com.appdupe.flamerchat.db.DatabaseHandler;
import com.appdupe.flamernofb.R;
import com.facebook.FacebookActivity;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehaviors;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class LoginUsingFacebook extends FacebookActivity implements
		OnClickListener {
	private static final String TAG = "LoginUsingActivityActivity";

	private Button buttonLoginLogout, loginwithfacebook;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private ExtendedGallery galleryforsuprvisore;
	private Resources res;
	private TypedArray imgs;
	private ArrayList<GellaryData> imagelist;
	private HomeAdapter mAdapterForGalery;
	private RelativeLayout privacypolicy;

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	Context context;
	private String regid;
	private ConnectionDetector cd;
	// private String SENDER_ID = "340606936019";
	private String SENDER_ID = "775207507530";

	private RelativeLayout tendethomeloginactivitytoplayout;
	private LinearLayout count_layout, tendethomeloginactivitybottomlayout,
			privacylayout;
	private int count;
	private TextView[] page_text;
	private static boolean mDebugLog = true;

	private Ultilities mUltilities = new Ultilities();
	private android.app.ProgressDialog mdialog;
	private LocationFinder newLocationFinder;
	double mLatitude;
	double mLongitude;
	private UserFaceBookInfo mUserFaceBookInfo;
	private Animation animeBottomTOUp;
	private Animation animeBottomUpToBotton;
	private Animation gellarybottom, gellaryup;
	private RelativeLayout galleryToplayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity);

		// this should be prints only when application is on debugging mode
		if (AppLog.isDebug) {
			printKeyHash();
		}

		res = getResources();
		initdata();
		context = getApplicationContext();
		printKeyHash();
		imagelist = new ArrayList<GellaryData>();
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

		Settings.addLoggingBehavior(LoggingBehaviors.INCLUDE_ACCESS_TOKENS);

		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback,
						savedInstanceState);
			}
			// still null then create new session
			if (session == null) {
				session = new Session(this);
			}

			Session.setActiveSession(session);
			AppLog.Log(TAG,
					"onCreate savedInstanceState state " + session.getState());

			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				AppLog.Log(TAG, "ACCEESS TOKEN Loaded");
			}
		}

		animeBottomTOUp = AnimationUtils.loadAnimation(this,
				R.anim.helpscreen_in_to_out);
		gellarybottom = AnimationUtils.loadAnimation(this,
				R.anim.helpscreen_in_to_out);
		gellarybottom.setFillAfter(true);

		gellarybottom.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});

		updateView();

		cd = new ConnectionDetector(getApplicationContext());
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
			AlertDialogManager
					.internetConnetionErrorAlertDialog(LoginUsingFacebook.this);
		}
	}

	/**
	 * This code is required only once, to get the KeyHash, which will be used
	 * in facebook while signing the app. The KeyHash will be displayed in
	 * LogCat. Once the Keyhash has been generated, use it for signing app, and
	 * then comment the code. Replace the package name with your package name.
	 */
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

	LocationResult mLocationResult = new LocationResult() {
		public void gotLocation(final double latitude, final double longitude) {
			// logDebug("gotLocation  latitude "+latitude);
			// logDebug("gotLocation  longitude "+longitude);
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
					gcm = GoogleCloudMessaging.getInstance(context);
				}
				regid = gcm.register(SENDER_ID);
				String regidfoundseccessfully = "getGoogleRegistrationId";
				msg = "GCMRegistration doInBackground Device registered, registration ID="
						+ regid;
				storeRegistrationId(context, regid);
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

	@Override
	protected void onResume() {
		super.onResume();
		checkPlayServices();
		LocationManager locationManagerresume = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (locationManagerresume
				.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			newLocationFinder = new LocationFinder();
			newLocationFinder.getLocation(LoginUsingFacebook.this,
					mLocationResult);
		} else {
			showGPSDisabledAlertToUser();

		}
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	public boolean checkPlayServices() {
		// logDebug("checkPlayServices  ");
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		// logDebug("checkPlayServices  resultCode "+resultCode);
		// logDebug("checkPlayServices   ConnectionResult.SUCCESS "+
		// ConnectionResult.SUCCESS);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {

				// logDebug("This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
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

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(LoginUsingFacebook.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	@SuppressLint("NewApi")
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			// logDebug("getRegistrationId   Registration not found.");

			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			// Log.i(TAG, "App version changed.");
			// logDebug("getRegistrationId   App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		// Log.i(TAG, "Saving regId on app version " + appVersion);
		// logDebug("Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private void initdata() {

		galleryToplayout = (RelativeLayout) findViewById(R.id.galleryToplayout);

		galleryforsuprvisore = (ExtendedGallery) findViewById(R.id.gallery);
		buttonLoginLogout = (Button) findViewById(R.id.buttonLoginLogout);
		loginwithfacebook = (Button) findViewById(R.id.loginwithfacebook);
		count_layout = (LinearLayout) findViewById(R.id.image_count_homescreen);
		// textInstructionsOrLink =
		// (TextView)findViewById(R.id.instructionsOrLink);
		privacypolicy = (RelativeLayout) findViewById(R.id.privacypolicy);
		privacypolicy.setOnClickListener(this);

		privacylayout = (LinearLayout) findViewById(R.id.privacylayout);
		privacylayout.setVisibility(View.GONE);
		tendethomeloginactivitytoplayout = (RelativeLayout) findViewById(R.id.tendethomeloginactivitytoplayout);
		tendethomeloginactivitybottomlayout = (LinearLayout) findViewById(R.id.tendethomeloginactivitybottomlayout);

		Typeface segoeuiregular = Typeface.createFromAsset(getAssets(),
				"fonts/segoeui.ttf");
		Typeface segoeuilLight = Typeface.createFromAsset(getAssets(),
				"fonts/segoeuil.ttf");

		TextView wetakeyourprivacy = (TextView) findViewById(R.id.wetakeyourprivacy);
		// TextView textviewsecond = (TextView)
		// findViewById(R.id.textviewsecond);
		TextView textviewthird = (TextView) findViewById(R.id.textviewthird);

		wetakeyourprivacy.setTypeface(segoeuilLight);
		// textviewsecond.setTypeface(segoeuilLight);
		wetakeyourprivacy.setTextColor(android.graphics.Color.rgb(76, 76, 76));
		// textviewsecond.setTextColor(android.graphics.Color.rgb(76, 76, 76));

		textviewthird.setTypeface(segoeuiregular);
		textviewthird.setTextColor(android.graphics.Color.rgb(76, 76, 76));

		TextView textviewfourth = (TextView) findViewById(R.id.textviewfourth);
		textviewfourth.setTypeface(segoeuiregular);
		textviewfourth.setTextColor(android.graphics.Color.rgb(76, 76, 76));

		TextView textviewfifth = (TextView) findViewById(R.id.textviewfifth);
		textviewfifth.setTypeface(segoeuiregular);
		textviewfifth.setTextColor(android.graphics.Color.rgb(76, 76, 76));

		TextView textviewfirst = (TextView) findViewById(R.id.textviewfirst);
		textviewfirst.setTypeface(segoeuiregular);
		textviewfirst.setTextColor(android.graphics.Color.rgb(76, 76, 76));

		loginwithfacebook.setTypeface(segoeuiregular);
	}

	private void getTemplateFromResource() {

		// logDebug("getTemplateFromResource   ");
		imgs = res.obtainTypedArray(R.array.galleryimage);

		for (int i = 0; i < imgs.length(); i++) {
			GellaryData gellaryData = new GellaryData();
			gellaryData.setResourceId(imgs.getResourceId(i, -1));
			imagelist.add(gellaryData);
		}
		page_text = new TextView[imgs.length()];
		count = imagelist.size();
		// logDebug("getTemplateFromResource   count");
		for (int i = 0; i < imagelist.size(); i++) {
			page_text[i] = new TextView(this);

			page_text[i].setTypeface(null, Typeface.BOLD);
			page_text[i].setText(".");
			page_text[i].setTextSize(25);
			page_text[i].setTextColor(android.graphics.Color.GRAY);
			count_layout.addView(page_text[i]);
		}

		// icon =
		// BitmapFactory.decodeResource(this.getResources(),imgs.getResourceId(shre.getInt("itemId",
		// -1)-1,-1));
		// Log.d(TAG, "item index is "+shre.getInt("itemId", -1));
		mAdapterForGalery.notifyDataSetChanged();

	}

	private class HomeAdapter extends ArrayAdapter<GellaryData> {

		Activity activity;

		private ArrayList<GellaryData> data = new ArrayList<GellaryData>();

		/* FeaturedActivity fac; */
		public Drawable image_in_detailActivity;

		public static final int DIALOG_PROGRESS = 1;

		public HomeAdapter(Activity context, ArrayList<GellaryData> list) {
			super(context, R.layout.gellaryitem, list);
			this.data = list;
			this.activity = context;
		}

		@Override
		public GellaryData getItem(int position) {
			return super.getItem(position);
		}

		@Override
		public int getCount() {
			return super.getCount();
		}

		// public HomeAdapter(Activity c, ArrayList<GellaryData> itemListAds)
		// {
		// /*private final static String TAG="HomeAdapter";*/
		// // this.context = context;
		// /*this.user = username;
		// this.pass = password;*/
		// this.activity = c;
		// this.mContext = c;
		// this.sContext = c;
		// // TypedArray attr = mContext;
		// this.data = itemListAds;
		// /*TypedArray attr = mContext
		// .obtainStyledAttributes(R.styleable.HelloGallery);
		// mGalleryItemBackground = attr.getResourceId(
		// R.styleable.HelloGallery_android_galleryItemBackground, 0);
		// TypedArray attrs = sContext
		// .obtainStyledAttributes(R.styleable.HelloGallery_smallImage);
		// small_imageBackground = attrs.getResourceId(
		// R.styleable.HelloGallery_android_galleryItemBackground, 1);
		// attr.recycle();*/
		// }

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			System.out.println("HomeAdapter getview");
			boolean flag = true;
			/* System.out.println("getView       HomeAdapter"); */

			if (convertView == null) {
				LayoutInflater vi = activity.getLayoutInflater();
				convertView = vi.inflate(R.layout.gellaryitem, null);
			}
			ImageView galleimageview = (ImageView) convertView
					.findViewById(R.id.gelleryimageview);
			galleimageview.setImageResource(getItem(position).getResourceId());

			return convertView;

		}
	}

	@Override
	public void onStart() {
		super.onStart();

		Session.getActiveSession().addCallback(statusCallback);
		FlurryAgent.onStartSession(this, Constant.flurryKey);

	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
		FlurryAgent.onEndSession(this);
		if (mdialog != null) {
			mdialog.dismiss();
			mdialog = null;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	private void updateView() {

		Session session = Session.getActiveSession();
		if (session.isOpened()) {
			Log.e(TAG, "Session open : " + session);
			new BackGroundTaskForFetchingDataFromFaceBook().execute();

		} else {
			buttonLoginLogout.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {

					Log.e(TAG, "this clicked.");
					onClickLogin();

				}
			});
			loginwithfacebook.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					onClickLogin();
				}
			});
		}
	}

	private class BackGroundTaskForFetchingDataFromFaceBook extends
			AsyncTask<String, Void, Void> {

		private File AppDirectoryPath;
		private boolean flagForResponse = true;
		private SessionManager mSessionManager = new SessionManager(
				LoginUsingFacebook.this);

		private Ultilities mUltilities = new Ultilities();
		private DatabaseHandler databaseHandler = new DatabaseHandler(
				LoginUsingFacebook.this);

		@Override
		protected Void doInBackground(String... params) {
			try {

				Session mCurrentSession = Session.getActiveSession();
				String URL = "https://graph.facebook.com/me?fields=bio,id,birthday,email,gender,first_name,age_range,hometown,last_name,name,relationship_status,quotes,about,location,interested_in&access_token="
						+ mCurrentSession.getAccessToken();

				HttpClient hc = new DefaultHttpClient();
				HttpGet get = new HttpGet(URL);
				HttpResponse rp = hc.execute(get);
				if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String queryAlbums = EntityUtils.toString(rp.getEntity());
					AppLog.Log(
							TAG,
							"BackGroundTaskForFetchingDataFromFaceBook   doInBackground fetch all user data Step 1 : "
									+ queryAlbums);
					Gson gson = new Gson();
					mUserFaceBookInfo = gson.fromJson(queryAlbums,
							UserFaceBookInfo.class);

				}

				AppDirectoryPath = mUltilities.createAppDirectoy(getResources()
						.getString(R.string.appdirectory));
				mUltilities.deleteNon_EmptyDir(AppDirectoryPath);
				databaseHandler.deleteUserData();
				databaseHandler.deleteMatchedlist();
				mSessionManager.logoutUser();

			} catch (Exception e) {
				Log.i(TAG, "FLAG RESPONSE CATCH:" + flagForResponse);
				e.printStackTrace();
				flagForResponse = false;
				// logDebug("BackGroundTaskForFetchingDataFromFaceBook   doInBackground exception "+
				// e);
				return null;
			} catch (Throwable e) {
				Log.i(TAG, "FLAG RESPONSE CATCH:" + flagForResponse);
				e.printStackTrace();
				flagForResponse = false;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (flagForResponse) {
				Log.i(TAG, "FLAG RESPONSE post execute:" + flagForResponse);
				Location mLocation = mUserFaceBookInfo.getLocation();
				AgeRange mAgeRange = mUserFaceBookInfo.getAgeRange();
				Hometown mHometown = mUserFaceBookInfo.getHomeTown();
				String userLikse = "";

				String[] userInteres = mUserFaceBookInfo.getInterestedIn();
				String interested = "";
				if (userInteres != null && userInteres.length > 0) {
					if (userInteres.length > 1) {
						interested = "3";
					} else {
						if (userInteres[0].equals("male")) {
							interested = "1";
						} else {
							interested = "2";
						}
					}
				}

				String firstName = mUserFaceBookInfo.getFirstName();// 0
				String lastName = mUserFaceBookInfo.getLastName();// 1
				String facebookId = "" + mUserFaceBookInfo.getFaceBookId(); // 2
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(LoginUsingFacebook.this);
				Editor editor = preferences.edit();
				editor.putString(Constant.FACEBOOK_ID, facebookId);
				editor.commit();
				Log.i(TAG, "preferece stored:" + facebookId);
				String userEmail = mUserFaceBookInfo.getEmail();// 3
				String uerGender = mUserFaceBookInfo.getGender();// 4
				String authenticationType = Constant.facebooAuthenticationType;// 5
				String deviceType = Constant.deviceType;// 6
				String deviceRegid = "";// 7
				String qbid = "34344";// 8
				String city = "";

				if (mHometown != null) {
					city = mHometown.getHomeTownName();// 9
				}
				String country = "";
				if (mLocation != null) {
					country = mLocation.getName();// 10
				}

				if (mLatitude == 0.0 || mLongitude == 0.0) {
					// mLatitude = 13.0287751;
					// mLongitude = 77.5896221;
					newLocationFinder = new LocationFinder();
					newLocationFinder.getLocation(LoginUsingFacebook.this,
							mLocationResult);
				}
				String currentLantitude = "" + mLatitude/* "13.0287751" */;// 11
				String currentLogitude = "" + mLongitude/* "77.5896221" */;// 12
				String usertagline = mUserFaceBookInfo.getBio();// 13
				String userPersional_dec = mUserFaceBookInfo.getBio();// 14
				String tempdateandtime = mUserFaceBookInfo.getBirthday();
				String userBirthday = null;
				if (tempdateandtime != null) {
					userBirthday = mUltilities.getDate(tempdateandtime, 7);
				}
				int minage = mAgeRange.getMin();
				int maxage = mAgeRange.getMax();

				if (minage == 0) {
					minage = 18;
				}
				if (maxage == 0) {
					maxage = 55;
				}
				// logDebug("BackGroundTaskForFetchingDataFromFaceBook   onPostExecute minage "+minage);
				// logDebug("BackGroundTaskForFetchingDataFromFaceBook   onPostExecute maxage "+maxage);
				mSessionManager.setUserHeigherAge("" + maxage);
				mSessionManager.setUserLowerAge("" + minage);
				String userInterested = interested;// 16
				String loweraageprefrence = "" + minage;// 17
				String maxageprefrence = "" + maxage;// 18
				String userPreferRadiouse = "100";// 19
				mSessionManager.setDistaceUnit("Km");
				mSessionManager.setDistance(100);
				String diveciId = Ultilities
						.getDeviceId(LoginUsingFacebook.this);// 20
				SessionManager mSessionManager = new SessionManager(
						LoginUsingFacebook.this);
				mSessionManager.setFacebookId(""
						+ mUserFaceBookInfo.getFaceBookId());
				// if (interested != null && interested.length() > 0) {
				// mSessionManager.setUserPrefSex("3");
				// } else {
				// if (uerGender.equals("male")) {
				// mSessionManager.setUserPrefSex("2");
				//
				// } else {
				// mSessionManager.setUserPrefSex("1");
				//
				// }
				mSessionManager.setUserPrefSex("3");
				// }
				if (uerGender.equals("male")) {
					uerGender = "1";
					mSessionManager.setUserSex(uerGender);
				} else {
					uerGender = "2";
					mSessionManager.setUserSex(uerGender);
				}

				if (userEmail != null && userEmail.length() > 0) {

				} else {
					Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level
																	// 8+
					Account[] accounts = AccountManager.get(
							LoginUsingFacebook.this).getAccounts();
					for (Account account : accounts) {
						if (emailPattern.matcher(account.name).matches()) {
							userEmail = account.name;
							mUserFaceBookInfo.setEmail(userEmail);
							// logDebug(" BackGroundTaskForFetchingDataFromFaceBook onPostExecute from sync email possibleEmail   "+userEmail);

						}
					}
				}

				if (userEmail != null && userEmail.length() > 0) {

				} else {
					userEmail = "sunil@3embed.com";
				}

				deviceRegid = regid/* "APA91bFDFSSYqjJMs2B125_UlHVAEou7ztZWJhrQ0gi8nr2_SLR0HQ_QyM6FYBV72TES_uAXO20VexAyf09y4QYlw823MjiOnyddiuBIP1qQZ69zpcQuv5U2p6m3GRhXmP7vLe7AO5eME7bu00QS093-oywJfW-7tg" */;
				// logDebug("BackGroundTaskForFetchingDataFromFaceBook   from device onPostExecute regid "+
				// regid);

				if (deviceRegid != null && !deviceRegid.isEmpty()) {
					// deviceRegid="googleREGIdNotFound";
					String regidisempty = "googleREGIdNotFound";
					FlurryAgent.logEvent(regidisempty);
				} else {
					deviceRegid = "googleREGIdNotFound";
				}

				String[] params = { firstName, // 0
						lastName, // 1
						facebookId, // 2
						userEmail, // 3
						uerGender, // 4
						city, // 5
						country, // 6
						currentLantitude, // 7
						currentLogitude, // 8
						usertagline, // 9
						userPersional_dec, // 10
						userBirthday, // 11
						userInterested, // 12
						loweraageprefrence, // 13
						maxageprefrence, // 14
						userPreferRadiouse, // 15
						userLikse, // 16
						diveciId, // 17
						deviceRegid, // 18
						qbid, // 19
						deviceType, // 20
						authenticationType // 21
				};

				for (int i = 0; i < params.length; i++) {
					Log.e(TAG, "(" + i + ")" + params[i]);
				}

				getUserLikes(params);

			} else {
				Log.i(TAG, "FLAG RESPONSE post execute else :"
						+ flagForResponse);
				mdialog.dismiss();
				mdialog.cancel();
				ErrorMessage("Alert",
						"Sorry. Not able fetch your profile information from facebook!! ");
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			try {
				mdialog = mUltilities.GetProcessDialog(LoginUsingFacebook.this);
				mdialog.setTitle("Fetching data          ");
				mdialog.setMessage("Please Wait...");
				mdialog.setCancelable(false);
				mdialog.show();
			} catch (Exception e) {
				// logDebug("BackGroundTaskForFetchingDataFromFaceBook   onPreExecute exception "+
				// e);
			}
		}
	}

	private void getUserLikes(final String[] params) {

		Session mSession = Session.getActiveSession();
		String fqlQuery = "SELECT page_id FROM page_fan WHERE uid=" + "'"
				+ params[2] + "'";

		// SELECT page_id FROM page_fan WHERE uid=me()

		// String fqlQuery =
		// "select uid1, uid2 FROM friend WHERE uid1 = '100003056725155'";
		// String fqlQuery =
		// "select src from photo  where album_object_id IN (SELECT  object_id   FROM album WHERE owner='params[0]' and name='Profile Pictures'";
		// SELECT pic_small,name from page where page_id IN (SELECT page_id FROM
		// page_fan WHERE uid='100003056725155' LIMIT 35)
		// logDebug("getUserLikes "+fqlQuery);
		Bundle param = new Bundle();
		param.putString("q", fqlQuery);
		// Session session = Session.getActiveSession();
		Request request = new Request(mSession, "/fql", param, HttpMethod.GET,
				new Request.Callback() {
					public void onCompleted(Response response) {
						try {
							final String Response = response.toString();
							// og.i(TAG, "Result: " + response.toString());
							// logDebug("getUserLikes response "+response);
							final String finalResponse = Response.substring(
									Response.indexOf("state=") + 6,
									Response.indexOf("}, error:"));
							AppLog.Log(TAG, "getUserLikes finalResponse "
									+ finalResponse);
							Gson gson = new Gson();
							UserFaceBookLikeData mUserFaceBookLikeData = gson
									.fromJson(finalResponse,
											UserFaceBookLikeData.class);
							ArrayList<UserFacebookLikeId> likeid = mUserFaceBookLikeData
									.getLikeid();
							String userlikes = "";
							for (int i = 0; i < likeid.size(); i++) {
								userlikes = userlikes
										+ likeid.get(i).getObject_id() + ",";
								AppLog.Log(TAG, "getUserLikes userlikes "
										+ userlikes);

							}
							params[16] = userlikes;

							Log.e(TAG,
									"Now next step(BackGroundTaskForLogind) after fetching user likes");
							new BackGroundTaskForLogind().execute(params);
						} catch (Exception e) {
							Log.e(TAG,
									"Going to next step(BackGroundTaskForLogind) but user likes are not fetched.");
							AppLog.Log(TAG, "getUserLikes Exception  " + e);
							new BackGroundTaskForLogind().execute(params);
						}
					}
				});

		Request.executeBatchAsync(request);
	}

	private class BackGroundTaskForLogind extends AsyncTask<String, Void, Void> {
		private String lognResponse;
		private boolean flagForResponse = true;

		private LoginData mLoginData;

		private List<NameValuePair> loginNameValuePairList;

		protected Void doInBackground(String... params) {
			try {
				loginNameValuePairList = mUltilities.getLoginParameter(params);

				Log.i(TAG, "*********************");
				Log.e("LOGIN PARAMS", loginNameValuePairList.toString()); // CHECKING
																			// PARAMS
				Log.i(TAG, "*********************");
				lognResponse = mUltilities.makeHttpRequest(Constant.login_url,
						Constant.methodeName, loginNameValuePairList);
				Log.i(TAG, "BackGroundTaskForLogind lognResponse JSON RESP :  "
						+ lognResponse);
				Gson gson = new Gson();
				mLoginData = gson.fromJson(lognResponse, LoginData.class);
				Log.i(TAG, " Gson RESP :  " + mLoginData);
				if (mLoginData.getErrFlag() == 0 && mLoginData.getErrNum() == 2) {
					File appDirectory = mUltilities
							.createAppDirectoy(getResources().getString(
									R.string.appdirectory));
					File _picDir = new File(appDirectory, getResources()
							.getString(R.string.imagedirectory));
					File imageFile = mUltilities.createFileInSideDirectory(
							_picDir,
							getResources().getString(R.string.imagefilename)
									+ "0.jpg");
					Utility.addBitmapToSdCardFromURL(mLoginData.getProfilePic()
							.replaceAll(" ", "%20"), imageFile);
				} else if (mLoginData.getErrFlag() == 0
						&& mLoginData.getErrNum() == 3) {
					// signup
				} else {
					// do nothing
					flagForResponse = false; // originally not commented
					AppLog.Log(TAG,
							"BackGroundTaskForLogind doInBackground mLoginData"
									+ mLoginData);
				}
			} catch (Exception e) {
				flagForResponse = false; // originally not commented
				AppLog.Log(TAG,
						"BackGroundTaskForLogind doInBackground Exception" + e);
			}
			return null;
		}

		protected void onPostExecute(Void feed) {
			super.onPostExecute(feed);

			if (flagForResponse) {
				try {
					AppLog.Log(TAG,
							"BackGroundTaskForLogind doInBackground message"
									+ mLoginData.getLoginMasseage());
					if (mLoginData.getErrFlag() == 0
							&& mLoginData.getErrNum() == 2) {
						SessionManager mSessionManager = new SessionManager(
								LoginUsingFacebook.this);
						mSessionManager.setUserToken(mLoginData.getUserToken());
						mSessionManager.setLastUpdate(mLoginData.getJoined());
						getUserProfile();
					} else if (mLoginData.getErrFlag() == 0
							&& mLoginData.getErrNum() == 3) {

						SessionManager mSessionManager = new SessionManager(
								LoginUsingFacebook.this);
						mSessionManager.setUserToken(mLoginData.getUserToken());
						mSessionManager.setLastUpdate(mLoginData.getJoined());
						getProfildPic();

					} else {
						mUltilities.showDialogConfirm(
								LoginUsingFacebook.this,
								getResources().getString(
										R.string.loginfaildalerttilel),
								mLoginData.getLoginMasseage(), true).show();
						mdialog.dismiss();
					}

				} catch (Exception e) {
					AppLog.Log(TAG,
							"BackGroundTaskForLogind   onPostExecute Exception "
									+ e);
					mdialog.dismiss();

				}
			} else {
				AppLog.Log(TAG, "BackGroundTaskForLogind onPostExecute");
				ErrorMessage("Alert ",
						"Sorry! Server not able to processs your request");
				mdialog.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			if (mdialog != null) {
				mdialog.setTitle("Creating profile");
				mdialog.setMessage("Please Wait...");
			}
			super.onPreExecute();
		}
	}

	private void getProfildPic() {
		// logDebug("getProfildPic ");
		SessionManager mSessionManager = new SessionManager(this);

		String[] params = { mSessionManager.getFacebookId() };
		// logDebug("onCreate params "+params);

		String fqlQuery = "select src_big from photo  where album_object_id IN (SELECT  object_id   FROM album WHERE owner="
				+ "'"
				+ params[0]
				+ "'"
				+ " and name='Profile Pictures') LIMIT 5";
		// String fqlQuery =
		// "select uid1, uid2 FROM friend WHERE uid1 = '100003056725155'";
		// String fqlQuery =
		// "select src from photo  where album_object_id IN (SELECT  object_id   FROM album WHERE owner='params[0]' and name='Profile Pictures'";
		// Log.i(TAG, "Result: " + fqlQuery);
		// logDebug("getProfildPic  Result: " + fqlQuery);
		Bundle param = new Bundle();
		param.putString("q", fqlQuery);
		Session session = Session.getActiveSession();
		Request request = new Request(session, "/fql", param, HttpMethod.GET,
				new Request.Callback() {
					public void onCompleted(Response response) {
						// Log.i(TAG, "Result: " + response.toString());
						AppLog.Log(TAG, "getProfildPic    response  "
								+ response.toString());
						String[] params = { response.toString() };
						new BackGroundTaskForDownload().execute(params);

					}
				});

		Request.executeBatchAsync(request);
	}

	private class BackGroundTaskForDownload extends
			AsyncTask<String, Void, Void> {
		Ultilities mUltilities = new Ultilities();
		UserProfilePick mUserProfilePick;

		private String deviceid;
		// private Ultilities mUltilities=new Ultilities();
		private String sessionToken;
		private SessionManager mSessionManager = new SessionManager(
				LoginUsingFacebook.this);
		private List<NameValuePair> uploadNameValuePairList;
		private String uploadResponse;
		private UploadImage mUploadImage;
		private boolean getImageFromFaceBookResponse = true;

		@Override
		protected Void doInBackground(String... params) {
			try {
				String picurlresponse = params[0];
				Log.e(TAG, "pic url : " + picurlresponse);
				picurlresponse = picurlresponse.substring(
						picurlresponse.indexOf("state=") + 1,
						picurlresponse.indexOf("}, error"));
				picurlresponse = picurlresponse.substring(5,
						picurlresponse.length());
				Gson gson = new Gson();
				mUserProfilePick = gson.fromJson(picurlresponse,
						UserProfilePick.class);
				ArrayList<PickUrl> poickList = mUserProfilePick.getData();
				if (poickList != null && poickList.size() > 0) {
					String profilePickUrl = "";
					String otherPicurl = "";
					if (poickList.size() == 1) {
						profilePickUrl = poickList.get(0).getSrc();
					} else {
						for (int i = 0; i < poickList.size(); i++) {
							if (i == 0) {
								profilePickUrl = poickList.get(i).getSrc();

							} else {
								if (i == poickList.size() - 1) {
									otherPicurl = otherPicurl
											+ poickList.get(i).getSrc();
								} else {
									otherPicurl = otherPicurl
											+ poickList.get(i).getSrc() + ",";
								}
							}
						}
					}

					Log.e(TAG, "profilePickUrl : " + profilePickUrl
							+ " and otherPicurl : " + otherPicurl);

					deviceid = /* "defoutlfortestin" */Ultilities
							.getDeviceId(LoginUsingFacebook.this);
					sessionToken = mSessionManager.getUserToken();

					String[] uploadParameter = { sessionToken, deviceid,
							profilePickUrl, otherPicurl };

					uploadNameValuePairList = mUltilities
							.getUploadParameter(uploadParameter);
					// logDebug("BackGroundTaskForDownload doInBackground uploadNameValuePairList "+uploadNameValuePairList);

					uploadResponse = mUltilities.makeHttpRequest(
							Constant.uploadImage_url, Constant.methodeName,
							uploadNameValuePairList);
					AppLog.Log(TAG,
							"BackGroundTaskForDownload doInBackground uploadResponse "
									+ uploadResponse);

					mUploadImage = gson.fromJson(uploadResponse,
							UploadImage.class);

					if (mUploadImage.getErrNum() == 18) {
						getImageFromFaceBookResponse = true;
						mSessionManager.setProFilePicture(mUploadImage
								.getPicURL());
						File appDirectory = mUltilities
								.createAppDirectoy(getResources().getString(
										R.string.appdirectory));
						File _picDir = new File(appDirectory, getResources()
								.getString(R.string.imagedirectory));
						File imageFile = mUltilities.createFileInSideDirectory(
								_picDir,
								getResources()
										.getString(R.string.imagefilename)
										+ "0.jpg");
						com.appdupe.flamer.utility.Utility
								.addBitmapToSdCardFromURL(
										mSessionManager.getUserPrifilePck()
												.replaceAll(" ", "%20"),
										imageFile);
					} else if (mUploadImage.getErrNum() == 1
							&& mUploadImage.getErrFlag() == 1) {
						getImageFromFaceBookResponse = false;
					}
				} else {
					getImageFromFaceBookResponse = false;
				}

			} catch (JsonSyntaxException ex) {
				// Inform then user that the the Json data contains invalid
				// syntax
				AppLog.Log(TAG,
						"BackGroundTaskForDownload   doInBackground JsonSyntaxException "
								+ ex);
			} catch (Exception e) {
				AppLog.Log(TAG,
						"BackGroundTaskForDownload   doInBackground Exception "
								+ e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			try {
				getUserProfile();

			} catch (Exception e2) {
				AppLog.Log(TAG,
						"BackGroundTaskForDownload   onPostExecute  Exception"
								+ e2);
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}
	}

	private void getUserProfile() {
		SessionManager mSessionManager = new SessionManager(this);

		String userFaceBookid = mSessionManager.getFacebookId();
		String userSessionToken = mSessionManager.getUserToken();
		String userDeviceId = Ultilities.getDeviceId(this);

		String[] params = { userSessionToken, userDeviceId, userFaceBookid };
		new BackGroundTaskForUserProfile().execute(params);
	}

	private class BackGroundTaskForUserProfile extends
			AsyncTask<String, Void, Void> {
		Ultilities mUltilities = new Ultilities();
		private String getProfileResponse;
		private List<NameValuePair> userProfileNameValuePairList;
		private userProFileData mUserProFileData;
		private ImageDetail imageDetail;
		private File imageFile;
		private SessionManager sessionManager = new SessionManager(
				LoginUsingFacebook.this);
		private ArrayList<ImageDetail> imageList = new ArrayList<ImageDetail>();

		@Override
		protected Void doInBackground(String... params) {
			try {
				File appDirectory = mUltilities
						.createAppDirectoy(getResources().getString(
								R.string.appdirectory));
				File _picDir = new File(appDirectory, getResources().getString(
						R.string.imagedire));
				mUltilities.deleteNon_EmptyDir(_picDir);
				userProfileNameValuePairList = mUltilities
						.getUserProfileParameter(params);

				Log.e("userProfileNameValuePairList",
						userProfileNameValuePairList.toString());

				getProfileResponse = mUltilities.makeHttpRequest(
						Constant.getProfile_url, Constant.methodeName,
						userProfileNameValuePairList);
				Log.e(TAG, "getProfileResponse : " + getProfileResponse);

				Gson gson = new Gson();
				mUserProFileData = gson.fromJson(getProfileResponse,
						userProFileData.class);
				String[] images = mUserProFileData.getImages();
				for (int i = 0; i < images.length; i++) {
					imageDetail = new ImageDetail();
					imageDetail.setImageUrl(images[i]);
					imageDetail.setUserFacebookid(sessionManager
							.getFacebookId());
					imageList.add(imageDetail);
				}
				Log.e(TAG, "imageList size : " + imageList.size());
				for (int i = 0; i < imageList.size(); i++) {
					switch (i) {
					case 0:
						sessionManager.setProFilePicture(imageList.get(i)
								.getImageUrl());
						imageFile = mUltilities.createFileInSideDirectory(
								_picDir,
								getResources()
										.getString(R.string.imagefilename)
										+ i
										+ ".jpg");
						com.appdupe.flamer.utility.Utility
								.addBitmapToSdCardFromURL(imageList.get(i)
										.getImageUrl().replaceAll(" ", "%20"),
										imageFile);
						imageList.get(i).setSdcardpath(
								imageFile.getAbsolutePath());
						imageList.get(i).setImageOrder(i + 1);
						break;
					case 1:
						sessionManager.setProFilePicture1(imageList.get(i)
								.getImageUrl());
						imageFile = mUltilities.createFileInSideDirectory(
								_picDir,
								getResources()
										.getString(R.string.imagefilename)
										+ i
										+ ".jpg");
						com.appdupe.flamer.utility.Utility
								.addBitmapToSdCardFromURL(imageList.get(i)
										.getImageUrl().replaceAll(" ", "%20"),
										imageFile);
						imageList.get(i).setSdcardpath(
								imageFile.getAbsolutePath());
						imageList.get(i).setImageOrder(i + 1);
						break;
					case 2:
						sessionManager.setProFilePicture2(imageList.get(i)
								.getImageUrl());
						imageFile = mUltilities.createFileInSideDirectory(
								_picDir,
								getResources()
										.getString(R.string.imagefilename)
										+ i
										+ ".jpg");
						com.appdupe.flamer.utility.Utility
								.addBitmapToSdCardFromURL(imageList.get(i)
										.getImageUrl().replaceAll(" ", "%20"),
										imageFile);
						imageList.get(i).setSdcardpath(
								imageFile.getAbsolutePath());
						imageList.get(i).setImageOrder(i + 1);
						break;
					case 3:
						sessionManager.setProFilePicture3(imageList.get(i)
								.getImageUrl());
						imageFile = mUltilities.createFileInSideDirectory(
								_picDir,
								getResources()
										.getString(R.string.imagefilename)
										+ i
										+ ".jpg");
						com.appdupe.flamer.utility.Utility
								.addBitmapToSdCardFromURL(imageList.get(i)
										.getImageUrl().replaceAll(" ", "%20"),
										imageFile);
						imageList.get(i).setSdcardpath(
								imageFile.getAbsolutePath());
						imageList.get(i).setImageOrder(i + 1);
						break;
					case 4:
						sessionManager.setProFilePicture4(imageList.get(i)
								.getImageUrl());
						imageFile = mUltilities.createFileInSideDirectory(
								_picDir,
								getResources()
										.getString(R.string.imagefilename)
										+ i
										+ ".jpg");
						com.appdupe.flamer.utility.Utility
								.addBitmapToSdCardFromURL(imageList.get(i)
										.getImageUrl().replaceAll(" ", "%20"),
										imageFile);
						imageList.get(i).setSdcardpath(
								imageFile.getAbsolutePath());
						imageList.get(i).setImageOrder(i + 1);
						break;
					case 5:
						sessionManager.setProFilePicture5(imageList.get(i)
								.getImageUrl());
						imageFile = mUltilities.createFileInSideDirectory(
								_picDir,
								getResources()
										.getString(R.string.imagefilename)
										+ i
										+ ".jpg");
						com.appdupe.flamer.utility.Utility
								.addBitmapToSdCardFromURL(imageList.get(i)
										.getImageUrl().replaceAll(" ", "%20"),
										imageFile);
						imageList.get(i).setSdcardpath(
								imageFile.getAbsolutePath());
						imageList.get(i).setImageOrder(i + 1);
						break;

					}
				}
				DatabaseHandler mDatabaseHandler = new DatabaseHandler(
						LoginUsingFacebook.this);
				mDatabaseHandler.addImagedetal(imageList);

			} catch (Exception e) {
				AppLog.Log(TAG,
						"BackGroundTaskForUserProfile   doInBackground Exception"
								+ e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			try {
				mdialog.dismiss();

				SessionManager mSessionManager = new SessionManager(
						LoginUsingFacebook.this);
				mSessionManager.createLoginSession();
				mSessionManager.setUserProfileName(mUserProFileData
						.getFirstName());
				mSessionManager.setUserAge("" + mUserProFileData.getAge());
				AppLog.Log(TAG,
						"BackGroundTaskForUserProfile  onPostExecute  user persional description "
								+ mUserProFileData.getPersDesc());
				if (mUserProFileData.getPersDesc() != null
						&& mUserProFileData.getPersDesc().length() > 0) {
					mSessionManager
							.setUserAbout(mUserProFileData.getPersDesc());
				} else {
					sessionManager.setUserAbout("");
				}
				Intent intent = new Intent(LoginUsingFacebook.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			} catch (Exception e) {
				AppLog.Log(TAG,
						"BackGroundTaskForUserProfile   onPostExecute Exception  "
								+ e);
				AppLog.handleException(TAG, e);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	/**
	 * Showing an alert dialog to usre in case of any error.
	 * 
	 * @param title
	 *            - title for the dialog.
	 * @param message
	 *            - error message.
	 */
	private void ErrorMessage(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				LoginUsingFacebook.this);
		builder.setTitle(title);
		builder.setMessage(message);

		builder.setPositiveButton(
				getResources().getString(R.string.okbuttontext),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();
	}

	private void ErrorMessageLocationNotFonr(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				LoginUsingFacebook.this);
		builder.setTitle(title);
		builder.setMessage(message);

		builder.setPositiveButton(
				getResources().getString(R.string.okbuttontext),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();
	}

	/**
	 * If the user presses the Facebook button, this function is being called.
	 */
	private void onClickLogin() {
		// birthday,email,cover,gender,interested_in,age_range,location,about,bio
		Session session = Session.getActiveSession();
		// logDebug(" onClickLogin    session"+session.getState());

		if (!session.isOpened() && !session.isClosed()) {
			AppLog.Log(TAG, " onClickLogin    session" + session.getState());
			Session.OpenRequest openRequest = new Session.OpenRequest(this);
			List<String> permissions = Arrays
					.asList("user_birthday", "email", "user_relationships",
							"user_photos,user_likes", "user_friends",
							"user_about_me", "user_relationship_details",
							"read_stream", "user_about_me");
			openRequest.setPermissions(permissions);
			openRequest.setCallback(statusCallback);

			if (session.getState() == SessionState.OPENING) {
				return;
			}

			session.openForRead(openRequest);
		} else {
			Session.openActiveSession(this, true, statusCallback);

		}
	}

	private void onClickLoginWithGaph() {

		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			AppLog.Log(TAG, " onClickLogin    session" + session.getState());

			Session.OpenRequest openRequest = new Session.OpenRequest(this);
			List<String> permissions = Arrays
					.asList("user_birthday", "email", "user_relationships",
							"user_photos,user_likes",
							"user_relationship_details", "read_stream",
							"user_about_me");
			openRequest.setPermissions(permissions);
			openRequest.setCallback(statusCallback);

			if (session.getState() == SessionState.OPENING) {
				return;
			}
			session.openForRead(openRequest);
		} else {
			Session.openActiveSession(this, true, statusCallback);

		}
	}

	private void onClickLogout() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}

	/**
	 * A callback which is called from facebook when facebook button is pressed.
	 */
	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			Log.e(TAG, "Status Call Back from fb, session : " + session
					+ " state : " + state);
			updateView();
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.privacypolicy) {

			privacylayout.setVisibility(View.VISIBLE);
			/* extendedgalrylayout.set */
			privacylayout.startAnimation(animeBottomTOUp);
			tendethomeloginactivitytoplayout.setVisibility(View.GONE);
			;
			tendethomeloginactivitybottomlayout.setVisibility(View.GONE);
			;

			// galleryToplayout.startAnimation(gellarybottom);

		}
	}

	/**
	 * When the back button is pressed, check if user has clicked on the
	 * "privacy policy" button. If clicked, then close the privacy policy layout
	 * on back press. Otherwise close the activity.
	 */
	@Override
	public void onBackPressed() {

		if (privacylayout.isShown()) {
			privacylayout.setVisibility(View.GONE);
			tendethomeloginactivitytoplayout.setVisibility(View.VISIBLE);
			tendethomeloginactivitybottomlayout.setVisibility(View.VISIBLE);
		} else {
			super.onBackPressed();
		}
	}
}