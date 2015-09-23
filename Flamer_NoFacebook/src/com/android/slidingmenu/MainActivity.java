package com.android.slidingmenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.appdupe.androidpushnotifications.ChatActivity;
import com.appdupe.flamer.QuestionsActivity;
import com.appdupe.flamer.pojo.LikeMatcheddataForListview;
import com.appdupe.flamer.pojo.LikedMatcheData;
import com.appdupe.flamer.pojo.Likes;
import com.appdupe.flamer.utility.AlertDialogManager;
import com.appdupe.flamer.utility.AppLog;
import com.appdupe.flamer.utility.ConnectionDetector;
import com.appdupe.flamer.utility.Constant;
import com.appdupe.flamer.utility.ScalingUtilities;
import com.appdupe.flamer.utility.ScalingUtilities.ScalingLogic;
import com.appdupe.flamer.utility.SessionManager;
import com.appdupe.flamer.utility.Ultilities;
import com.appdupe.flamer.utility.Utility;
import com.appdupe.flamerchat.db.DatabaseHandler;
import com.appdupe.flamernofb.R;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;

public class MainActivity extends SherlockFragmentActivity implements
		OnClickListener, OnOpenListener {
	// MainLayout mLayout;
	private static final String TAG = "MainActivity";
	private ListView matcheslistview;
	Button btMenu;
	private Button buttonRightMenu;
	TextView tvTitle;
	private Typeface topbartextviewFont;
	private Editor editor;
	private SharedPreferences preferences;
	private EditText etSerchFriend;
	double mLatitude = 0;
	double mLongitude = 0;
	double dLatitude = 0;
	double dLongitude = 0;
	// private Session.StatusCallback statusCallback = new
	// SessionStatusCallback();
	private Dialog mdialog;
	// private boolean usersignup = false;
	private boolean isProfileclicked = false;
	private ArrayList<LikeMatcheddataForListview> arryList;
	private MatchedDataAdapter adapter;
	private ImageView profileimage;
	private LinearLayout profilelayout, homelayout, messages, settinglayout,
			invitelayout, questionLayout;
	public SlidingMenu menu;
	private boolean flagforHome, flagForProfile, flagForsetting;
	// private AQuery aQuery;
	private ImageOptions options;
	private ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// mLayout = (MainLayout)
		// this.getLayoutInflater().inflate(R.layout.slidmenuxamplemainactivity,
		// null);
		// setContentView(mLayout);
		cd = new ConnectionDetector(this);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();
		// aQuery = new AQuery(this);
		options = new ImageOptions();
		options.fileCache = true;
		options.memCache = true;
		setContentView(R.layout.slidmenuxamplemainactivity);
		if (preferences.getBoolean(Constant.PREF_ISFIRST, true)) {
			editor.putBoolean(Constant.PREF_ISFIRST, false);
			editor.commit();
			startActivity(new Intent(this, QuestionsActivity.class));
		}
		tvTitle = (TextView) findViewById(R.id.activity_main_content_title);
		topbartextviewFont = Typeface.createFromAsset(getAssets(),
				"fonts/HelveticaLTStd-Light.otf");
		tvTitle.setTypeface(topbartextviewFont);
		tvTitle.setTextColor(Color.rgb(255, 255, 255));
		tvTitle.setTextSize(20);
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		Log.d(TAG, "onCreate  before add menu ");
		menu.setMenu(R.layout.leftmenu);
		menu.setSecondaryMenu(R.layout.rightmenu);
		Log.d(TAG, "onCreate  add menu ");
		menu.setSlidingEnabled(true);
		Log.d(TAG, "onCreate  finish");

		// search
		etSerchFriend = (EditText) menu
				.findViewById(R.id.et_serch_right_side_menu);

		// btnSerch = (Button) menu.findViewById(R.id.btn_serch_right_side);

		View leftmenuview = menu.getMenu();
		View rightmenuview = menu.getSecondaryMenu();

		initLayoutComponent(leftmenuview, rightmenuview);

		menu.setSecondaryOnOpenListner(this);

		// lvMenuItems = getResources().getStringArray(R.array.menu_items);

		// lvMenu.setAdapter(new
		// ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
		// lvMenuItems));

		matcheslistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// logDebug("setOnItemClickListener  onItemClick arg2 "+arg2);

				LikeMatcheddataForListview matcheddataForListview = (LikeMatcheddataForListview) arg0
						.getItemAtPosition(arg2);
				String faceboolid = matcheddataForListview.getFacebookid();
				// logDebug(" background setOnItemClickListener  onItemClick friend facebook id faceboolid "+faceboolid);
				// logDebug(" background setOnItemClickListener  onItemClick user facebook id  faceboolid"+new
				// SessionManager(MainActivity.this).getFacebookId());
				Bundle mBundle = new Bundle();
				mBundle.putString(Constant.FRIENDFACEBOOKID, faceboolid);
				mBundle.putString(Constant.CHECK_FOR_PUSH_OR_NOT, "1");

				Intent mIntent = new Intent(MainActivity.this,
						ChatActivity.class);
				mIntent.putExtras(mBundle);
				startActivity(mIntent);
				menu.toggle();

			}
		});

		buttonRightMenu = (Button) findViewById(R.id.button_right_menu);

		btMenu = (Button) findViewById(R.id.button_menu);
		btMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Show/hide the menu
				toggleMenu(v);
			}
		});

		try {

			profilelayout.setOnClickListener(this);
			homelayout.setOnClickListener(this);
			messages.setOnClickListener(this);
			settinglayout.setOnClickListener(this);
			invitelayout.setOnClickListener(this);
			questionLayout.setOnClickListener(this);

		} catch (Exception e) {
			AppLog.handleException("oncreate   Exception  ", e);
		}

		// Bundle extras = getIntent().getExtras();
		System.out.println("Get Intent done");
		try {
			FragmentManager fm = MainActivity.this.getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			FindMatches fragment = new FindMatches();
			ft.add(R.id.activity_main_content_fragment, fragment);
			tvTitle.setText(getResources().getString(R.string.app_name));

			ft.commit();
			setProfilePick(profileimage);

		} catch (Exception e) {
			AppLog.handleException("onCreate Exception ", e);

		}

		Ultilities mUltilities = new Ultilities();
		int imageHeightAndWidht[] = mUltilities
				.getImageHeightAndWidthForAlubumListview(this);
		arryList = new ArrayList<LikeMatcheddataForListview>();
		adapter = new MatchedDataAdapter(this, arryList, imageHeightAndWidht);
		matcheslistview.setAdapter(adapter);

		// final SessionManager sessionManager = new SessionManager(this);

		buttonRightMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isProfileclicked) {
					Intent mIntent = new Intent(MainActivity.this,
							EditProfileNew.class);
					startActivity(mIntent);
				} else {
					toggleRightMenu(v);

					// //menu_right.setVisibility(View.VISIBLE);;
					// //menu_left.setVisibility(View.GONE);
					// if (!flagformatchedListDisplay)
					// {
					// flagformatchedListDisplay=true;
					// fineLikedMatched();
					// }
					// else
					// {
					// flagformatchedListDisplay=false;
					// arryList.clear();
					// adapter.notifyDataSetChanged();
					// }

				}

			}
		});

		initSerchData();

	}

	/**
	 * method responsible for intialise search function at right side friend
	 * list adapter of this activity
	 */
	private void initSerchData() {
		etSerchFriend.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				adapter.getFilter().filter(s.toString().trim());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	public void setMenuTouchFullScreenEnable(boolean isEnable) {
		if (isEnable) {
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	@Override
	public void onOpen() {
		AppLog.Log(TAG, "onOpen");
		findLikedMatched();
	}

	@Override
	protected void onResume() {
		super.onResume();
		AppLog.Log(TAG, " MainActivity   onResume  called");
		// SessionManager sessionManager = new SessionManager(this);
		// if (sessionManager.isIsProfileImageChanged()) {
		// sessionManager.setIsProfileImageChanged(false);
		setProfilePick(profileimage);
		// } else {
		//
		// }
	}

	private void setProfilePick(final ImageView userProfilImage) {
		final Ultilities mUltilities = new Ultilities();

		// File appDirectory;
		// File _picDir;
		// File myimgFile;

		// try {

		// appDirectory =
		// mUltilities.createAppDirectoy(getResources().getString(R.string.appdirectory));
		// _picDir = new File(appDirectory,
		// getResources().getString(R.string.imagedirectory));
		// myimgFile= new File(_picDir,
		// getResources().getString(R.string.imagefilename)+"0.jpg");

		// DatabaseHandler mdaDatabaseHandler = new DatabaseHandler(this);
		// String imageOrderArray[] = { "1" };
		// ArrayList<ImageDetail> imagelist = mdaDatabaseHandler
		// .getImageDetailByImageOrder(imageOrderArray);
		// if (imagelist != null && imagelist.size() > 0) {
		// Bitmap bitmapimage = mUltilities.showImage/*
		// * setImageToImageViewBitmapFactory
		// * .decodeFiledecodeFile
		// */(imagelist.get(0)
		// .getSdcardpath());
		new Thread(new Runnable() {

			@Override
			public void run() {
				final Bitmap bitmapimage = Utility.getBitmapFromURL(preferences
						.getString(Constant.PREF_PROFILE_IMAGE_ONE, ""));
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						AppLog.Log(
								TAG,
								"Profile Image Url:"
										+ preferences
												.getString(
														Constant.PREF_PROFILE_IMAGE_ONE,
														""));
						Bitmap cropedBitmap = null;
						ScalingUtilities mScalingUtilities = new ScalingUtilities();
						Bitmap mBitmap = null;
						if (bitmapimage != null) {
							cropedBitmap = mScalingUtilities
									.createScaledBitmap(bitmapimage, 80, 80,
											ScalingLogic.CROP);
							bitmapimage.recycle();
							mBitmap = mUltilities.getCircleBitmap(cropedBitmap,
									1);
							cropedBitmap.recycle();
							userProfilImage.setImageBitmap(mBitmap);
							// aQuery.id(userProfilImage).image(mBitmap);
						} else {
						}

					}
				});

			}
		}).start();

		// } else {
		//
		// }

		// } catch (Exception e) {
		// e.printStackTrace();
		// // ImageView[] params = { userProfilImage };
		// // new
		// // BackGroundTaskForDownloadProfileImageIfUseDeletedFormDirectory()
		// // .execute(params);
		// }

	}

	private void initLayoutComponent(View leftmenu, View rightmenu) {
		// menu_right=(LinearLayout)rightmenu.findViewById(R.id.menu_right);
		// menu_left=(LinearLayout)leftmenu.findViewById(R.id.menu_left);
		matcheslistview = (ListView) rightmenu
				.findViewById(R.id.menu_right_ListView);
		// lvMenu = (ListView) findViewById(R.id.menu_listview);

		profileimage = (ImageView) leftmenu.findViewById(R.id.profileimage);
		// aQuery.id(profileimage).image(
		// preferences.getString(Constant.PREF_PROFILE_IMAGE_ONE, ""),
		// options);
		// homeimageview=(ImageView)findViewById(R.id.homeimageview);
		// messageimage=(ImageView)findViewById(R.id.messageimage);
		// settingimage=(ImageView)findViewById(R.id.settingimage);
		// inviteimage=(ImageView)findViewById(R.id.inviteimage);
		// profileimage.setBackgroundResource(R.drawable.profile_boader_on);

		profilelayout = (LinearLayout) leftmenu
				.findViewById(R.id.profilelayout);
		homelayout = (LinearLayout) leftmenu.findViewById(R.id.homelayout);
		messages = (LinearLayout) leftmenu.findViewById(R.id.messages);
		settinglayout = (LinearLayout) leftmenu
				.findViewById(R.id.settinglayout);
		invitelayout = (LinearLayout) leftmenu.findViewById(R.id.invitelayout);
		questionLayout = (LinearLayout) leftmenu
				.findViewById(R.id.questionLayout);

	}

	private void findLikedMatched() {

		AppLog.Log(TAG, "findLikedMatched");

		// try {
		// String deviceid = Ultilities.getDeviceId(this);
		// AppLog.Log(TAG, "fineLikedMatched   deviceid" + deviceid);
		// SessionManager mSessionManager = new SessionManager(this);

		// String sessionToken = mSessionManager.getUserToken();
		// AppLog.Log(TAG, "fineLikedMatched   sessionToken" + sessionToken);
		// Ultilities mUltilitie = new Ultilities();
		// String currentdeviceTime = mSessionManager.getLastUpdatedTime();
		// String curenttime = mUltilitie.getCurrentGmtTime();
		// mSessionManager.setLastUpdate(curenttime);

		String params[] = { preferences.getString(Constant.FACEBOOK_ID, "") };
		new BackgroundTaskForFindLikeMatched().execute(params);

	}

	private class BackgroundTaskForFindLikeMatched extends
			AsyncTask<String, Void, Void> {
		private Ultilities mUltilities = new Ultilities();
		private List<NameValuePair> getuserparameter;
		private String likedmatchedata;
		private LikedMatcheData matcheData;
		private ArrayList<Likes> likesList;
		private LikeMatcheddataForListview matcheddataForListview;
		DatabaseHandler mDatabaseHandler = new DatabaseHandler(
				MainActivity.this);
		private boolean isResponseSuccess = true;

		@Override
		protected Void doInBackground(String... params) {
			try {

				File appDirectory = mUltilities
						.createAppDirectoy(getResources().getString(
								R.string.appdirectory));
				AppLog.Log(TAG,
						"BackgroundTaskForFindLikeMatched   doInBackground appDirectory "
								+ appDirectory);
				File _picDir = new File(appDirectory, getResources().getString(
						R.string.imagedirematchuserdirectory));

				AppLog.Log(TAG,
						"BackgroundTaskForFindLikeMatched doInBackground ");

				getuserparameter = mUltilities.getUserLikedParameter(params);

				AppLog.Log(TAG,
						"BackgroundTaskForFindLikeMatched doInBackground   getuserparameter "
								+ getuserparameter);

				likedmatchedata = mUltilities.makeHttpRequest(
						Constant.getliked_url, Constant.methodeName,
						getuserparameter);

				AppLog.Log(TAG,
						"BackgroundTaskForFindLikeMatched doInBackground   likedmatchedata "
								+ likedmatchedata);

				Gson gson = new Gson();
				matcheData = gson.fromJson(likedmatchedata,
						LikedMatcheData.class);

				AppLog.Log(TAG,
						"BackgroundTaskForFindLikeMatched doInBackground   matcheData "
								+ matcheData);

				// "errNum": "51",
				// "errFlag": "0",
				// "errMsg": "Matches found!",

				if (matcheData.getErrFlag() == 0) {
					likesList = matcheData.getLikes();
					AppLog.Log(TAG,
							"BackgroundTaskForFindLikeMatched doInBackground   likesList "
									+ likesList);
					if (arryList != null) {
						arryList.clear();
					}

					AppLog.Log(TAG,
							"BackgroundTaskForFindLikeMatched doInBackground   likesList sized "
									+ likesList.size());
					for (int i = 0; i < likesList.size(); i++) {
						matcheddataForListview = new LikeMatcheddataForListview();
						String userName = likesList.get(i).getfName();
						String facebookid = likesList.get(i).getFbId();
						// Log.i(TAG, "Background facebookid......"+facebookid);
						String picturl = likesList.get(i).getpPic();
						int falg = likesList.get(i).getFlag();
						String latd = likesList.get(i).getLadt();
						matcheddataForListview.setFacebookid(facebookid);
						matcheddataForListview.setUserName(userName);
						matcheddataForListview.setImageUrl(picturl);
						matcheddataForListview.setFlag("" + falg);
						matcheddataForListview.setladt(latd);
						// matcheddataForListview.setFilePath(filePath);
						File imageFile = mUltilities.createFileInSideDirectory(
								_picDir, userName + facebookid + ".jpg");
						// logDebug("BackGroundTaskForUserProfile doInBackground imageFile is profile "+imageFile.isFile());
						Utility.addBitmapToSdCardFromURL(likesList.get(i)
								.getpPic().replaceAll(" ", "%20"), imageFile);
						matcheddataForListview.setFilePath(imageFile
								.getAbsolutePath());
						if (!preferences.getString(Constant.FACEBOOK_ID, "")
								.equals(facebookid)) {
							arryList.add(matcheddataForListview);
						}

					}
					DatabaseHandler mDatabaseHandler = new DatabaseHandler(
							MainActivity.this);
					// SessionManager mSessionManager = new SessionManager(
					// MainActivity.this);
					String userFacebookid = preferences.getString(
							Constant.FACEBOOK_ID, "");

					//
					boolean isdataiserted = mDatabaseHandler.insertMatchList(
							arryList, userFacebookid);

					// what it is??
					// ////////////////////////////////////////////////////////////////////////////////

					ArrayList<LikeMatcheddataForListview> arryListtem = mDatabaseHandler
							.getUserFindMatch();
					AppLog.Log(TAG, "arryListtem  " + arryListtem);
					if (arryListtem != null && arryListtem.size() > 0) {
						AppLog.Log(TAG, "arryList  size " + arryListtem.size());
						arryList.clear();

						arryList.addAll(arryListtem);
						// adapter.notifyDataSetChanged();

						// for (int i = 0; i < arryList.size(); i++)
						// {
						// Bitmap bitmapimage =
						// mUltilities.showImage/*setImageToImageViewBitmapFactory.decodeFiledecodeFile*/(arryList.get(i).getFilePath());
						// logDebug("setProfilePick bitmapimage"+bitmapimage);
						// ScalingUtilities mScalingUtilities =new
						// ScalingUtilities();
						// Bitmap cropedBitmap=
						// mScalingUtilities.createScaledBitmap(bitmapimage,
						// 100, 100, ScalingLogic.CROP);
						// bitmapimage.recycle();
						// Bitmap mBitmap=
						// mUltilities.getCircleBitmap(cropedBitmap, 1);
						// arryList.get(i).setmBitmap(mBitmap);
						// cropedBitmap.recycle();
						// // logDebug("setProfilePick  mBitmap"+mBitmap);
						// // userProfilImage.setImageBitmap(mBitmap);
						//
						// }
					}

				}
				// "errNum": "50",
				// "errFlag": "1",
				// "errMsg": "Sorry, no matches found!"
				else if (matcheData.getErrFlag() == 1) {
					ArrayList<LikeMatcheddataForListview> arryListtem = mDatabaseHandler
							.getUserFindMatch();
					AppLog.Log(TAG, "arryListtem  " + arryListtem);
					if (arryListtem != null && arryListtem.size() > 0) {
						AppLog.Log(TAG, "arryList  size " + arryListtem.size());
						arryList.clear();

						arryList.addAll(arryListtem);
					}

				} else {
					// do nothing
				}

			} catch (Exception e) {
				AppLog.handleException(
						"BackgroundTaskForFindLikeMatched doInBackground Exception ",
						e);
				// some thing wrong happend
				isResponseSuccess = false;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			AppLog.Log(TAG, "BackgroundTaskForFindLikeMatched onPostExecute  ");
			try {
				mdialog.dismiss();
			} catch (Exception e) {
				AppLog.Log(TAG,
						"BackgroundTaskForFindLikeMatched   onPostExecute  Exception "
								+ e);
			}
			if (!isResponseSuccess) {
				AlertDialogManager.errorMessage(MainActivity.this, "Alert",
						"Request timeout");
			}
			adapter.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			AppLog.Log(TAG, "BackgroundTaskForFindLikeMatched onPreExecute  ");
			try {
				mdialog = mUltilities.GetProcessDialog(MainActivity.this);
				mdialog.setCancelable(false);
				mdialog.show();
			} catch (Exception e) {
				AppLog.handleException(
						"BackgroundTaskForFindLikeMatched   onPreExecute  Exception ",
						e);
			}

		}

	}

	private class MatchedDataAdapter extends
			ArrayAdapter<LikeMatcheddataForListview> {
		private AQuery aQuery;
		private Activity mActivity;
		private LayoutInflater mInflater;
		private SessionManager sessionManager;

		public MatchedDataAdapter(Activity context,
				List<LikeMatcheddataForListview> objects,
				int imageHeigthAndWidth[]) {
			super(context, R.layout.matchedlistviewitem, objects);
			mActivity = context;
			mInflater = (LayoutInflater) mActivity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// this.imageHeigthAndWidth=imageHeigthAndWidth;
			sessionManager = new SessionManager(context);
			aQuery = new AQuery(context);
		}

		@Override
		public int getCount() {
			return super.getCount();
		}

		@Override
		public LikeMatcheddataForListview getItem(int position) {
			return super.getItem(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.matchedlistviewitem,
						null);
				holder.imageview = (ImageView) convertView
						.findViewById(R.id.userimage);
				holder.textview = (TextView) convertView
						.findViewById(R.id.userName);
				holder.lastMasage = (TextView) convertView
						.findViewById(R.id.lastmessage);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.textview.setId(position);
			holder.imageview.setId(position);
			holder.lastMasage.setId(position);
			holder.textview.setText(getItem(position).getUserName());
			aQuery.id(holder.imageview).image(getItem(position).getImageUrl());
			try {
				holder.lastMasage.setText(sessionManager
						.getLastMessage(getItem(position).getFacebookid()));
			} catch (Exception e) {
				AppLog.handleException(TAG + " getView  Exception ", e);
			}

			return convertView;
		}

		class ViewHolder {
			ImageView imageview;
			TextView textview;
			TextView lastMasage;

		}
	}

	public void toggleMenu(View v) {
		menu.toggle();
	}

	public void toggleRightMenu(View v) {
		menu.showSecondaryMenu();
	}

	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.toggle();
		} else if (menu.isSecondaryMenuShowing()) {
			menu.showSecondaryMenu();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mdialog != null) {
			mdialog.dismiss();
			mdialog = null;
		}
	}

	@Override
	protected void onDestroy() {
		if (mdialog != null && mdialog.isShowing()) {
			mdialog.dismiss();
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {

		FragmentManager fm = MainActivity.this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = null;

		if (v.getId() == R.id.homelayout) {
			if (!cd.isConnectingToInternet()) {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}
			if (flagforHome) {
				menu.toggle();
				return;
			} else {
				fragment = new FindMatches();
				buttonRightMenu
						.setBackgroundResource(R.drawable.selector_for_message_button);
				tvTitle.setText(getResources().getString(R.string.app_name));
				flagforHome = true;
				flagForProfile = false;
				flagForsetting = false;
				// flagForMessage=false;
				// flagForInvite=false;
				isProfileclicked = false;
				if (fragment != null) {
					ft.replace(R.id.activity_main_content_fragment, fragment);
					ft.commit();
					// tvTitle.setText(selectedItem);
				}
				menu.toggle();
			}
		} else if (v.getId() == R.id.profilelayout) {
			if (!cd.isConnectingToInternet()) {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}
			if (flagForProfile) {
				menu.toggle();
				return;
			} else {
				buttonRightMenu.setBackgroundResource(R.drawable.edit_btn);
				isProfileclicked = true;

				fragment = new UserProfile();
				tvTitle.setText(getResources().getString(R.string.myprofile));
				flagforHome = false;
				flagForProfile = true;
				flagForsetting = false;
				// flagForMessage=false;
				// flagForInvite=false;
				if (fragment != null) {
					ft.replace(R.id.activity_main_content_fragment, fragment);
					ft.commit();
					// tvTitle.setText(selectedItem);
				}
				menu.toggle();
			}
		} else if (v.getId() == R.id.settinglayout) {
			if (!cd.isConnectingToInternet()) {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}
			if (flagForsetting) {
				menu.toggle();
				return;
			} else {
				buttonRightMenu
						.setBackgroundResource(R.drawable.selector_for_message_button);
				tvTitle.setText(getResources().getString(R.string.settings));
				fragment = new SettingActivity();
				flagforHome = false;
				flagForProfile = false;
				flagForsetting = true;
				// flagForMessage=false;
				// flagForInvite=false;
				isProfileclicked = false;
				if (fragment != null) {
					ft.replace(R.id.activity_main_content_fragment, fragment);
					ft.commit();
					// tvTitle.setText(selectedItem);
				}
				menu.toggle();
			}
		} else if (v.getId() == R.id.messages) {
			if (!cd.isConnectingToInternet()) {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}
			toggleRightMenu(v);

		} else if (v.getId() == R.id.questionLayout) {
			if (!cd.isConnectingToInternet()) {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}
			menu.toggle();
			Intent questionIntent = new Intent(this, QuestionsActivity.class);
			startActivity(questionIntent);

		} else if (v.getId() == R.id.invitelayout) {
			if (!cd.isConnectingToInternet()) {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}
			// Change by Dilavar
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent
					.putExtra(
							Intent.EXTRA_TEXT,
							"I am using Flamer App ! Why don't you try it out...\nInstall Flamer now !\nhttps://play.google.com/store/apps/details?id=com.appdupe.flamernofb");
			sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					" Flamer App !");
			sendIntent.setType("message/rfc822"); //

			sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { "" });
			startActivity(Intent
					.createChooser(sendIntent, "Send mail using..."));

			// sendIntent.setType("text/plain");
			// startActivity(sendIntent);

		}
	}

}
