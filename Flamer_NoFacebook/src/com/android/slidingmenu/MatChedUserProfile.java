package com.android.slidingmenu;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdupe.flamer.pojo.GellaryData;
import com.appdupe.flamer.pojo.InviteActionData;
import com.appdupe.flamer.pojo.userProFileData;
import com.appdupe.flamer.utility.AlertDialogManager;
import com.appdupe.flamer.utility.AppLog;
import com.appdupe.flamer.utility.ConnectionDetector;
import com.appdupe.flamer.utility.Constant;
import com.appdupe.flamer.utility.ExtendedGallery;
import com.appdupe.flamer.utility.SessionManager;
import com.appdupe.flamer.utility.Ultilities;
import com.appdupe.flamer.utility.UltilitiesDate;
import com.appdupe.flamernofb.R;
import com.facebook.LoggingBehaviors;
import com.facebook.Session;
import com.facebook.Settings;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class MatChedUserProfile extends Activity implements OnClickListener {
	private static final String TAG = "MatChedUserProfile";
	private static boolean mDebugLog = true;
	private static String mDebugTag = "MatChedUserProfile";
	private ExtendedGallery imageExtendedGallery;
	private LinearLayout image_count;
	private TextView usernametextivew, ueragetextviw, distancetextview,
			activetimetextview, abouttextview, abouttextviewvalues,
			viewMatchedProfiletextview, statusTextView;
	private RelativeLayout Aboutuseragelayout, likedislikebuttonlayout;
	private Button likeButton, dislikebutton;
	private ArrayList<GellaryData> imageList;
	private ImageAdapterForGellary mAdapterForGellary;
	private ProgressDialog mDialog;
	private int[] imageHeightandWIdth;
	private ConnectionDetector cd;
	private RelativeLayout.LayoutParams layoutParams;
	private int count;
	private TextView[] page_text;
	private SharedPreferences preferences;

	// void logDebug(String msg) {
	// if (mDebugLog) {
	// Log.d(mDebugTag, msg);
	// }
	// }
	//
	// void logError(String msg) {
	// if (mDebugLog) {
	// Log.e(mDebugTag, msg);
	// }
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.matcheduserprofile);
		cd = new ConnectionDetector(this);
		if (!cd.isConnectingToInternet()) {
			Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
			return;
		}
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		initLayoutResource();

		// userFriendPhotogallery = (RelativeLayout)
		// findViewById(R.id.userFriendPhotogallery);
		// userInterestedGallery = (RelativeLayout)
		// findViewById(R.id.userInterestedGallery);
		// gallery_paging = (RelativeLayout) findViewById(R.id.gallery_paging);

		imageList = new ArrayList<GellaryData>();
		mAdapterForGellary = new ImageAdapterForGellary(this, imageList);
		imageExtendedGallery.setAdapter(mAdapterForGellary);
		Ultilities ultilities = new Ultilities();

		imageHeightandWIdth = ultilities
				.getImageHeightAndWidthForProfileGellary(this);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle.getBoolean(Constant.isFromChatScreen)) {
				likedislikebuttonlayout.setVisibility(View.GONE);
			} else {

			}
		}

		// userintrestdata = new ArrayList<GellaryData>();
		// mAdapterForGellaryInterested = new ImageAdapterForGellaryInterested(
		// this, userintrestdata);
		// userIntestedgallery.setAdapter(mAdapterForGellaryInterested);
		//
		// userFriendlist = new ArrayList<GellaryData>();
		// mAdapterForGellaryfriends = new ImageAdapterForGellaryfriends(this,
		// userFriendlist);
		// userfriendgallery.setAdapter(mAdapterForGellaryfriends);

		Settings.addLoggingBehavior(LoggingBehaviors.INCLUDE_ACCESS_TOKENS);

		// Session session = Session.getActiveSession();
		//
		// if (session == null) {
		// if (savedInstanceState != null) {
		// // session = Session.restoreSession(this, null, statusCallback,
		// // savedInstanceState);
		//
		// }
		// if (session == null) {
		// session = new Session(this);
		// }
		// Session.setActiveSession(session);
		//
		// if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
		// // session.openForRead(new
		// //
		// Session.OpenRequest(this).setPermissions(Arrays.asList("user_birthday",
		// //
		// "email","user_relationships","user_photos")).setCallback(statusCallback));
		// }
		// }
		try {
			likeButton.setOnClickListener(this);
			dislikebutton.setOnClickListener(this);
		} catch (Exception e) {
		}

		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
				imageHeightandWIdth[1], imageHeightandWIdth[0]);
		rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		// rlp.setMargins(0, imageLayoutHeightandWidth[2], 0, 0);
		// gallery_paging.setLayoutParams(rlp);

		layoutParams = ultilities.getRelativelayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		// layoutParams.addRule(RelativeLayout.BELOW,
		// R.id.userinterestedlayout);
		layoutParams.setMargins(0, 5, 0, 0);
		// userInterestedGallery.setLayoutParams(layoutParams);

		layoutParams = ultilities.getRelativelayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		// layoutParams.addRule(RelativeLayout.BELOW, R.id.myfriendsnamelayout);
		layoutParams.setMargins(0, 5, 0, 0);

		// userFriendPhotogallery.setLayoutParams(layoutParams);

		cd = new ConnectionDetector(getApplicationContext());
		if (cd.isConnectingToInternet()) {
			getUserProfile();
			// getUserShareeInterest();
		} else {
			AlertDialogManager
					.internetConnetionErrorAlertDialog(MatChedUserProfile.this);

		}

		imageExtendedGallery
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {

						for (int i = 0; i < count; i++) {
							page_text[i]
									.setTextColor(android.graphics.Color.GRAY);
						}
						page_text[pos]
								.setTextColor(android.graphics.Color.LTGRAY);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
	}

	private void initLayoutResource() {

		viewMatchedProfiletextview = (TextView) findViewById(R.id.viewMatchedProfiletextview);
		likeButton = (Button) findViewById(R.id.likeButton);
		dislikebutton = (Button) findViewById(R.id.dislikebutton);
		imageExtendedGallery = (ExtendedGallery) findViewById(R.id.imageExtendedGallery);
		image_count = (LinearLayout) findViewById(R.id.image_count);
		usernametextivew = (TextView) findViewById(R.id.usernametextivew);
		ueragetextviw = (TextView) findViewById(R.id.ueragetextviw);
		distancetextview = (TextView) findViewById(R.id.distancetextview);
		activetimetextview = (TextView) findViewById(R.id.activetimetextview);
		statusTextView = (TextView) findViewById(R.id.txtMatchedUserStatus);
		// userfriendgallery = (HorizontalListView)
		// findViewById(R.id.userfriendgallery);
		// userIntestedgallery = (HorizontalListView)
		// findViewById(R.id.userIntestedgallery);
		abouttextview = (TextView) findViewById(R.id.abouttextview);
		Aboutuseragelayout = (RelativeLayout) findViewById(R.id.Aboutuseragelayout);
		Aboutuseragelayout.setVisibility(View.GONE);
		likedislikebuttonlayout = (RelativeLayout) findViewById(R.id.likedislikebuttonlayout);
		abouttextviewvalues = (TextView) findViewById(R.id.abouttextviewvalues);
		// userFriendPhotogallery = (RelativeLayout)
		// findViewById(R.id.userFriendPhotogallery);
		// userInterestedGallery = (RelativeLayout)
		// findViewById(R.id.userInterestedGallery);
		// usersharedfriend = (TextView) findViewById(R.id.usersharedfriend);
		// usersahredInterested = (TextView)
		// findViewById(R.id.usersahredInterested);
		// myfriendssharecont = (TextView)
		// findViewById(R.id.myfriendssharecont);
		// userInterestedcount = (TextView)
		// findViewById(R.id.userInterestedcount);

		Typeface HelveticaInseratLTStd_Roman = Typeface.createFromAsset(
				getAssets(), "fonts/HelveticaInseratLTStd-Roman.otf");
		Typeface HelveticaLTStd_Light = Typeface.createFromAsset(getAssets(),
				"fonts/HelveticaLTStd-Light.otf");
		viewMatchedProfiletextview.setTypeface(HelveticaLTStd_Light);
		viewMatchedProfiletextview.setTextColor(Color.rgb(255, 255, 255));
		viewMatchedProfiletextview.setTextSize(20);

		usernametextivew.setTypeface(HelveticaInseratLTStd_Roman);
		usernametextivew.setTextColor(Color.rgb(124, 124, 124));
		((TextView) findViewById(R.id.txtst))
				.setTypeface(HelveticaInseratLTStd_Roman);
		((TextView) findViewById(R.id.txtst)).setTextColor(Color.rgb(124, 124,
				124));
		statusTextView.setTypeface(HelveticaInseratLTStd_Roman);
		statusTextView.setTextColor(Color.rgb(124, 124, 124));
		// usernametextivew.setTextSize(20);

		ueragetextviw.setTypeface(HelveticaLTStd_Light);
		ueragetextviw.setTextColor(Color.rgb(124, 124, 124));
		// ueragetextviw.setTextSize(20);

		// usersharedfriend.setTypeface(HelveticaLTStd_Light);
		// usersharedfriend.setTextColor(Color.rgb(124, 124, 124));
		// usersharedfriend.setTextSize(15);
		//
		// usersahredInterested.setTypeface(HelveticaLTStd_Light);
		// usersahredInterested.setTextColor(Color.rgb(124, 124, 124));
		// usersahredInterested.setTextSize(15);
		//
		// myfriendssharecont.setTypeface(HelveticaLTStd_Light);
		// myfriendssharecont.setTextColor(Color.rgb(124, 124, 124));
		// myfriendssharecont.setTextSize(15);
		// userInterestedcount.setTypeface(HelveticaLTStd_Light);
		// userInterestedcount.setTextColor(Color.rgb(124, 124, 124));
		// userInterestedcount.setTextSize(15);

		abouttextview.setTypeface(HelveticaInseratLTStd_Roman);
		abouttextview.setTextColor(Color.rgb(92, 92, 92));
		abouttextview.setTextSize(15);

		abouttextviewvalues.setTypeface(HelveticaInseratLTStd_Roman);
		abouttextviewvalues.setTextColor(Color.rgb(131, 131, 131));
		abouttextviewvalues.setTextSize(15);

		distancetextview.setTypeface(HelveticaInseratLTStd_Roman);
		distancetextview.setTextColor(Color.rgb(92, 92, 92));
		distancetextview.setTextSize(15);

		activetimetextview.setTypeface(HelveticaInseratLTStd_Roman);
		activetimetextview.setTextColor(Color.rgb(131, 131, 131));
		activetimetextview.setTextSize(15);
	}

	private void getUserProfile() {
		SessionManager mSessionManager = new SessionManager(this);

		String macheduserFacebookid = mSessionManager
				.getMatchedUserFacebookId();
		AppLog.Log(TAG, "Matched UserFacebook ID:" + macheduserFacebookid);
		// String userSessionToken = mSessionManager.getUserToken();
		// String userDeviceId = Ultilities.getDeviceId(this);
		if (macheduserFacebookid != null && macheduserFacebookid.length() > 0) {
			// String[] params = { userSessionToken, userDeviceId,
			// macheduserFacebookid };
			String[] params = { macheduserFacebookid };
			new BackGroundTaskForUserProfile().execute(params);
		} else {
			ErrorMessageMandetoryFiledMissing(
					getResources().getString(R.string.alert), getResources()
							.getString(R.string.retriedmessage));
		}
	}

	private class BackGroundTaskForUserProfile extends
			AsyncTask<String, Void, Void> {
		Ultilities mUltilities = new Ultilities();
		private String getProfileResponse;
		private List<NameValuePair> userProfileNameValuePairList;
		private userProFileData mUserProFileData;
		private GellaryData mGellaryData;

		// private SessionManager sessionManager = new SessionManager(
		// MatChedUserProfile.this);

		@Override
		protected Void doInBackground(String... params) {
			try {

				userProfileNameValuePairList = mUltilities
						.getUserProfileParameter(params);
				getProfileResponse = mUltilities.makeHttpRequest(
						Constant.getProfile_url, Constant.methodeName,
						userProfileNameValuePairList);
				AppLog.Log(TAG, "Matched User Profile response-------->"
						+ getProfileResponse);
				// logDebug("BackGroundTaskForUserProfile  getProfileResponse "+getProfileResponse);
				Gson gson = new Gson();
				mUserProFileData = gson.fromJson(getProfileResponse,
						userProFileData.class);

				String[] images = mUserProFileData.getImages();

				for (int i = 0; i < images.length; i++) {
					mGellaryData = new GellaryData();

					mGellaryData.setImageUrl(images[i]);
					imageList.add(mGellaryData);

				}

				runOnUiThread(new Runnable() {
					public void run() {

						if (mDialog != null) {
							mDialog.dismiss();
						}

						page_text = new TextView[imageList.size()];
						count = imageList.size();
						image_count.removeAllViews();
						for (int i = 0; i < imageList.size(); i++) {
							page_text[i] = new TextView(MatChedUserProfile.this);
							page_text[i].setText(".");
							page_text[i].setTextSize(45);
							page_text[i].setTypeface(null, Typeface.BOLD);
							page_text[i]
									.setTextColor(android.graphics.Color.GRAY);
							image_count.addView(page_text[i]);

						}

						mAdapterForGellary.notifyDataSetChanged();
						if (mUserProFileData.getStatus() != null
								&& !mUserProFileData.getStatus().equals("")) {
							statusTextView
									.setText(mUserProFileData.getStatus());
						} else {
							statusTextView.setText("N/A");
						}
						ueragetextviw.setText("" + mUserProFileData.getAge());
						usernametextivew.setText(""
								+ mUserProFileData.getFirstName());
						viewMatchedProfiletextview.setText(""
								+ mUserProFileData.getFirstName());
						SessionManager sessionManager = new SessionManager(
								MatChedUserProfile.this);
						String DistanceUinit = null;
						if (sessionManager.getDistaceUnit().equals("Km")) {
							DistanceUinit = "Km.";
						} else {
							DistanceUinit = "Mi.";
						}

						distancetextview.setText("Less then "
								+ mUserProFileData.getDistance() + " "
								+ DistanceUinit + " away");
						String gmtTime = mUserProFileData.getLastActive();
						// gmtTime=gmtTime.replaceAll("-", " ");

						String localTime = UltilitiesDate.getLocalTime(gmtTime);
						Ultilities ultilities = new Ultilities();
						// String
						// curentTime=ultilities.getCurrentDateYYYYMMdd();
						String dataString = UltilitiesDate
								.datesString(localTime);
						UltilitiesDate ultilitiesDate = new UltilitiesDate();
						int days = ultilitiesDate.getDays();
						int hours = ultilitiesDate.getHours();

						activetimetextview.setText("active " + days + " -d  "
								+ hours + "- Hour ago");
						if (mUserProFileData.getPersDesc() != null
								&& mUserProFileData.getPersDesc().length() > 0) {
							Aboutuseragelayout.setVisibility(View.VISIBLE);
							abouttextview.setText("About  " + ""
									+ mUserProFileData.getFirstName());
							abouttextviewvalues.setText(""
									+ mUserProFileData.getPersDesc());

						} else {
							Aboutuseragelayout.setVisibility(View.GONE);
						}
					}
				});

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

				if (mDialog != null) {
					mDialog.dismiss();
				}

			} catch (Exception e) {
				AppLog.Log(TAG,
						"BackGroundTaskForUserProfile   onPostExecute Exception  "
								+ e);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = mUltilities.GetProcessDialog(MatChedUserProfile.this);
			mDialog.setMessage("Please Wait..");
			mDialog.setCancelable(false);
			mDialog.show();
		}

	}

	private class ImageAdapterForGellary extends ArrayAdapter<GellaryData> {
		Activity mActivity;
		private LayoutInflater mInflater;
		private Ultilities mUltilities = new Ultilities();
		private int[] imageheightandWidth = mUltilities
				.getImageHeightAndWidthForGellary(MatChedUserProfile.this);

		public ImageAdapterForGellary(Activity context,
				List<GellaryData> objects) {
			super(context, R.layout.galleritem, objects);
			mActivity = context;
			mInflater = (LayoutInflater) mActivity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return super.getCount();
		}

		@Override
		public GellaryData getItem(int position) {
			return super.getItem(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.galleritem, null);
				holder.imageview = (ImageView) convertView
						.findViewById(R.id.thumbImage);
				// holder.mProgressBar = (ProgressBar) convertView
				// .findViewById(R.id.pbGalleryItemImage);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// holder.mProgressBar.setId(position);
			holder.imageview.setId(position);

			Picasso.with(MatChedUserProfile.this) //
					.load(getItem(position).getImageUrl()) //
					.error(R.drawable.error) //
					.resize(imageHeightandWIdth[1], imageHeightandWIdth[0]) //
					.into(holder.imageview);

			return convertView;
		}

		class ViewHolder {
			ImageView imageview;
			// ProgressBar mProgressBar;

		}
	}

	public void onStart() {
		super.onStart();

		// Session.getActiveSession().addCallback(statusCallback);
		// FlurryAgent.onStartSession(this, Constant.flurryKey);

	}

	@Override
	public void onStop() {
		super.onStop();
		// Session.getActiveSession().removeCallback(statusCallback);
		// FlurryAgent.onEndSession(this);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.likeButton) {
			likeMatchedUser("1");
		}
		if (v.getId() == R.id.dislikebutton) {
			likeMatchedUser("2");
		}

	}

	private void likeMatchedUser(String action) {
		SessionManager mSessionManager = new SessionManager(
				MatChedUserProfile.this);
		String sessionToke = mSessionManager.getUserToken();
		String devieceId = Ultilities.getDeviceId(MatChedUserProfile.this);
		String MatchedUserFacebookId = mSessionManager
				.getMatchedUserFacebookId();
		String userAction = action;
		// String[] params = { sessionToke, devieceId, MatchedUserFacebookId,
		// userAction };
		String[] params = { preferences.getString(Constant.FACEBOOK_ID, ""),
				MatchedUserFacebookId, userAction };

		new BackGroundTaskForInviteAction().execute(params);

	}

	private class BackGroundTaskForInviteAction extends
			AsyncTask<String, Void, Void> {

		private String inviteActionResponse;
		private List<NameValuePair> inviteactionparamlist;
		private InviteActionData mActionData;
		private Ultilities mUltilities = new Ultilities();

		@Override
		protected Void doInBackground(String... params) {
			try {
				inviteactionparamlist = mUltilities
						.getInviteActionParameter(params);

				inviteActionResponse = mUltilities.makeHttpRequest(
						Constant.inviteaction_url, Constant.methodeName,
						inviteactionparamlist);

				Gson gson = new Gson();
				mActionData = gson.fromJson(inviteActionResponse,
						InviteActionData.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = mUltilities.GetProcessDialog(MatChedUserProfile.this);
			mDialog.setMessage("Please wait..");
			mDialog.setCancelable(true);
			mDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			try {
				mDialog.dismiss();

				if (mActionData.getErrNum() == 29
						&& mActionData.getErrFlag() == 0) {
					SessionManager mSessionManager = new SessionManager(
							MatChedUserProfile.this);
					mSessionManager.isInviteActionSucess(true);

					finish();
				} else if (mActionData.getErrNum() == 37
						&& mActionData.getErrFlag() == 1) {
					ErrorMessage("alrte", mActionData.getErrMsg());
				} else {
					ErrorMessage("alrte",
							"sorry Server Error! Please try again after sometime!");
				}
				finish();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void ErrorMessage(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				MatChedUserProfile.this);
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

	private void ErrorMessageMandetoryFiledMissing(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				MatChedUserProfile.this);
		builder.setTitle(title);
		builder.setMessage(message);

		builder.setPositiveButton(
				getResources().getString(R.string.okbuttontext),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				});

		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();
	}

}
