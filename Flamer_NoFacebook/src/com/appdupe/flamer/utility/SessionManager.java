package com.appdupe.flamer.utility;

import java.util.HashMap;

import android.content.Context;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
	public static boolean flag = true;
	private static final String TAG = "SessionManager";
	// Shared Preferences
	private SharedPreferences pref;
	// Editor for Shared preferences
	private Editor editor;
	// Context
	private Context _context;
	// Shared pref mode
	private int PRIVATE_MODE = 0;
	// Sharedpref file name
	private static final String PREF_NAME = "TinderApp";
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	// User name (make variable public to access from outside)
	public static final String USER_ID = "userid";
	public static final String USER_NAME = "USER_NAME";
	public static final String USER_EAILID = "USER_EAILID";
	public static final String USER_PHONE = "USER_PHONE";

	/*
	 * // Email address (make variable public to access from outside) public
	 * static final String KEY_EMAIL = "email";
	 */

	// Constructor
	public static final String USER_TOKEN = "userToken";
	public static final String FACEBOOK_ID = "facebookid";
	public static final String PROFILE_PIC_URL = "prifilePickurl";

	public static final String PROFILE_PIC_URL1 = "prifilePickurl1";
	public static final String PROFILE_PIC_URL2 = "prifilePickurl2";
	public static final String PROFILE_PIC_URL3 = "prifilePickurl3";
	public static final String PROFILE_PIC_URL4 = "prifilePickurl4";
	public static final String PROFILE_PIC_URL5 = "prifilePickurl5";
	public static final String PROFLR_IS_CALLED = "profileIsCallde";
	public static final String USER_NAME_PROFILE = "profileImageName";
	public static final String USER_NAME_AGE = "profileAGE";
	public static final String USER_NAME_ABOUT = "profileABOUT";

	public static final String IMAGEINDEX = "ImageIndex";
	public static final String ISIBAGESET = "isimageset";
	private static final String isFindCalledGetMatched = "isFindCalledGetMatched";
	private static final String MATCHEDUSERFACEBOOKID = "matcheduserfacebookid";
	private static final String INVITEACTIONSUCESS = "inviteactionsucess";
	private static final String IMAGEINDEXForLIKEDISLIKE = "ImagelikeDIslike";
	private static final String LASRUPDATEDATA = "lastupdatedate";
	private static final String LASTDOWNLOADIMAGE = "lastdwonloadImage";

	private static final String isMatchedUserCalled = "ismatchedUserCalled";

	public void SetIsMatchedUserCalled(boolean bFlag) {
		editor.putBoolean(isMatchedUserCalled, bFlag);
		editor.commit();
	}

	public boolean isMatchedUserCalled() {
		return pref.getBoolean(isMatchedUserCalled, false);
	}

	private static final String IS_FIRSTSCREEN = "is_firstscreen";
	// *************************************setting
	// secreen*************************************

	private static final String USERSEX = "usersex";
	private static final String USERPREFSEX = "userprefsex";
	private static final String USERLOWERAGEPREFRENCE = "userlowerageprefrence";
	private static final String USERHEIGHERAGEPREFRENCE = "userheigherageprefrence";
	private static final String USERRADIOUS = "userradious";
	private static final String DISTACEUNIT = "distanceunit";
	private static final String RADIOUS = "radious";
	private static final String IMAGECHANGE = "ImageChange";
	private static final String ISPROFILEIMAGEMODIFY = "ISPROFILEIMAGEMODIFY";

	public void setLastMessage(String key, String lastMessage) {
		editor.putString(key, lastMessage);
		// commit changes
		editor.commit();
	}

	public String getLastMessage(String key) {
		return pref.getString(key, null);
	}

	public void setIsImageChange(boolean isfindmatchedCalled) {
		editor.putBoolean(IMAGECHANGE, isfindmatchedCalled);
		editor.commit();
	}

	public boolean isImageChange() {
		return pref.getBoolean(IMAGECHANGE, false);
	}

	public void setIsProfileImageChanged(boolean isfindmatchedCalled) {
		editor.putBoolean(ISPROFILEIMAGEMODIFY, isfindmatchedCalled);
		editor.commit();
	}

	public boolean isIsProfileImageChanged() {
		return pref.getBoolean(ISPROFILEIMAGEMODIFY, false);
	}

	public void setDistance(int distance) {
		editor.putInt(RADIOUS, distance);
		// commit changes
		editor.commit();
	}

	public int getDistance() {
		return pref.getInt(RADIOUS, 0);
	}

	public void setUserSex(String usersex) {
		editor.putString(USERSEX, usersex);
		// commit changes
		editor.commit();
	}

	public String getUserSex() {
		return pref.getString(USERSEX, null);
	}

	public void setUserPrefSex(String usersexpref) {
		editor.putString(USERPREFSEX, usersexpref);
		// commit changes
		editor.commit();
	}

	public String getUserPrefSex() {
		return pref.getString(USERPREFSEX, "3");
	}

	public void setUserLowerAge(String lowerage) {
		editor.putString(USERLOWERAGEPREFRENCE, lowerage);
		// commit changes
		editor.commit();
	}

	public String getUserLowerAge() {
		return pref.getString(USERLOWERAGEPREFRENCE, null);
	}

	public void setUserHeigherAge(String heigher) {
		editor.putString(USERHEIGHERAGEPREFRENCE, heigher);
		// commit changes
		editor.commit();
	}

	public String getUserHeigherAge() {
		return pref.getString(USERHEIGHERAGEPREFRENCE, null);
	}

	public void setUserradious(String radious) {
		editor.putString(USERRADIOUS, radious);
		// commit changes
		editor.commit();
	}

	public String getUserradious() {
		return pref.getString(USERRADIOUS, null);
	}

	public void setDistaceUnit(String distanceUnit) {
		editor.putString(DISTACEUNIT, distanceUnit);
		// commit changes
		editor.commit();
	}

	public String getDistaceUnit() {
		return pref.getString(DISTACEUNIT, "Km");
	}

	// ********************* setting screen
	// end****************************************

	public void setLastUpdate(String lastupdatatime) {
		editor.putString(LASRUPDATEDATA, lastupdatatime);
		// commit changes
		editor.commit();
	}

	public String getLastUpdatedTime() {
		return pref.getString(LASRUPDATEDATA, null);
	}

	public void setImageIndexForLikeDislike(int imageIdex) {
		editor.putInt(IMAGEINDEXForLIKEDISLIKE, imageIdex);
		// commit changes
		editor.commit();
	}

	//

	public int getImageIndexForLikeDislike() {
		return pref.getInt(IMAGEINDEXForLIKEDISLIKE, 0);
	}

	public void setLastDownloadImageIndex(int imageIdex) {
		editor.putInt(LASTDOWNLOADIMAGE, imageIdex);
		// commit changes
		editor.commit();
	}

	public int getLastDownloadImageIndex() {
		return pref.getInt(LASTDOWNLOADIMAGE, 0);
	}

	public void isInviteActionSucess(boolean bFlag) {
		editor.putBoolean(INVITEACTIONSUCESS, bFlag);
		editor.commit();
	}

	public boolean getIsInviteActionSucess() {
		return pref.getBoolean(INVITEACTIONSUCESS, false);
	}

	public void setFirstScreen(boolean bFlag) {
		editor.putBoolean(IS_FIRSTSCREEN, bFlag);
		editor.commit();
	}

	public void setIsGetMatchedCall(boolean isfindmatchedCalled) {
		editor.putBoolean(isFindCalledGetMatched, isfindmatchedCalled);
		editor.commit();
	}

	public boolean isFindMatchedCalled() {
		return pref.getBoolean(isFindCalledGetMatched, false);
	}

	public boolean isFirstScreen() {

		return pref.getBoolean(IS_FIRSTSCREEN, false);

	}

	public void setImageIndex(int imageIdex) {
		editor.putInt(IMAGEINDEX, imageIdex);
		// commit changes
		editor.commit();
	}

	public void setIsImageSet(boolean isImageset) {
		editor.putBoolean(ISIBAGESET, isImageset);
		// commit changes
		editor.commit();
	}

	public int getImageIndex() {
		return pref.getInt(IMAGEINDEX, 0);
	}

	public boolean getIsImageSet() {
		return pref.getBoolean(ISIBAGESET, false);
	}

	public void removeImageSet() {
		editor.remove(ISIBAGESET);
		// commit changes
		editor.commit();
	}

	public void setUserProfileName(String profileImagename) {
		editor.putString(USER_NAME_PROFILE, profileImagename);
		// commit changes
		editor.commit();
	}

	public void setUserAge(String UserToken) {
		editor.putString(USER_NAME_AGE, UserToken);
		// commit changes
		editor.commit();
	}

	public void setUserAbout(String UserToken) {
		editor.putString(USER_NAME_ABOUT, UserToken);
		// commit changes
		editor.commit();
	}

	public String getUserProfileName() {
		return pref.getString(USER_NAME_PROFILE, "not found");
	}

	public String getUserAge() {
		return pref.getString(USER_NAME_AGE, "not found");
	}

	public String getUserAbout() {
		return pref.getString(USER_NAME_ABOUT, "not found");
	}

	public void setUserToken(String UserToken) {
		editor.putString(USER_TOKEN, UserToken);
		// commit changes
		editor.commit();
	}

	public void setProFileIsCallde(boolean iscalled) {
		editor.putBoolean(PROFLR_IS_CALLED, iscalled);
		// commit changes
		editor.commit();
	}

	public void setProFilePicture1(String picurl) {
		editor.putString(PROFILE_PIC_URL1, picurl);
		// commit changes
		editor.commit();
	}

	public void setProFilePicture2(String picurl) {
		editor.putString(PROFILE_PIC_URL2, picurl);
		// commit changes
		editor.commit();
	}

	public void setProFilePicture3(String picurl) {
		editor.putString(PROFILE_PIC_URL3, picurl);
		// commit changes
		editor.commit();
	}

	public void setProFilePicture4(String picurl) {
		editor.putString(PROFILE_PIC_URL4, picurl);
		// commit changes
		editor.commit();
	}

	public void setProFilePicture5(String picurl) {
		editor.putString(PROFILE_PIC_URL5, picurl);
		// commit changes
		editor.commit();
	}

	public void setProFilePicture(String picurl) {
		editor.putString(PROFILE_PIC_URL, picurl);
		// commit changes
		editor.commit();
	}

	public void setFacebookId(String facebookId) {
		editor.putString(FACEBOOK_ID, facebookId);
		// commit changes
		editor.commit();
	}

	public void setMatchedUserFacebookId(String facebookId) {
		editor.putString(MATCHEDUSERFACEBOOKID, facebookId);
		// commit changes
		editor.commit();
	}

	public String getFacebookId() {

		return pref.getString(FACEBOOK_ID, "");

	}

	public String getMatchedUserFacebookId() {

		return pref.getString(MATCHEDUSERFACEBOOKID, "");

	}

	public String getUserToken() {
		return pref.getString(USER_TOKEN, "");
	}

	public String getUserPrifilePck() {
		return pref.getString(PROFILE_PIC_URL, "not found");
	}

	public String getUserPrifilePck1() {
		return pref.getString(PROFILE_PIC_URL1, "not found");
	}

	public String getUserPrifilePck2() {
		return pref.getString(PROFILE_PIC_URL2, "not found");
	}

	public String getUserPrifilePck3() {
		return pref.getString(PROFILE_PIC_URL3, "not found");
	}

	public String getUserPrifilePck4() {
		return pref.getString(PROFILE_PIC_URL4, "not found");
	}

	public String getUserPrifilePck5() {
		return pref.getString(PROFILE_PIC_URL5, "not found");
	}

	// public void setProFilePicture5(String picurl)
	// {
	// editor.putString(PROFILE_PIC_URL5, picurl);
	// // commit changes
	// editor.commit();
	// }

	public boolean getProFileIsCallde() {
		return pref.getBoolean(PROFLR_IS_CALLED, false);
	}

	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public void setUserName(String useName) {
		Log.i(TAG, "setUserName.....");
		Log.i(TAG, "setUserName  useName " + useName);
		// Storing login value as TRUE
		// editor.putBoolean(IS_LOGIN, true);
		editor.putString(USER_NAME, useName);
		// commit changes
		editor.commit();
		Log.i(TAG, "" + pref.getString(USER_NAME, null));
	}

	public void setUserPhoneNo(String usePhone) {
		Log.i(TAG, "setUserName.....");
		Log.i(TAG, "setUserName  useName " + usePhone);
		// Storing login value as TRUE
		// editor.putBoolean(IS_LOGIN, true);
		editor.putString(USER_PHONE, usePhone);
		// commit changes
		editor.commit();
		Log.i(TAG, "" + pref.getString(USER_PHONE, null));

	}

	public void setUserEmailId(String useId) {
		Log.i(TAG, "setUserEmailId.....");
		Log.i(TAG, "setUserEmailId  useEmailId " + useId);
		// Storing login value as TRUE
		// editor.putBoolean(IS_LOGIN, true);
		editor.putString(USER_EAILID, useId);
		// commit changes
		editor.commit();
		Log.i(TAG, "" + pref.getString(USER_EAILID, null));

	}

	public void setUserId(String useEmailId) {
		Log.i(TAG, "setUserEmailId.....");
		Log.i(TAG, "setUserName  useEmailId " + useEmailId);
		// Storing login value as TRUE
		// editor.putBoolean(IS_LOGIN, true);
		editor.putString(USER_ID, useEmailId);
		// commit changes
		editor.commit();
		Log.i(TAG, "" + pref.getString(USER_ID, null));

	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(/* String userId */) {
		Log.i(TAG, "createLoginSession.....");
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		// editor.putString(USER_ID, userId);
		// commit changes
		editor.commit();
		Log.i(TAG, "" + pref.getString(USER_ID, null));
		Log.i(TAG, "session created....");
	}

	/**
	 * Check login method wil check user login status If false it will redirect
	 * user to login page Else won't do anything
	 * */
	public void checkLogin() {
		Log.i(TAG, "checkLogin......");
		// Check login status
		if (!this.isLoggedIn()) {
			Log.i(TAG, "user not logedin......");
			flag = false;
			// user is not logged in redirect him to Login Activity
			// Intent i = new Intent(_context, HomeActivity.class);
			// // Closing all the Activities
			// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//
			// // Add new Flag to start new Activity
			// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//
			// // Staring Login Activity
			// _context.startActivity(i);
		}

	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		Log.i(TAG, "getUserDetails.....");
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		Log.i(TAG, "user id= " + pref.getString(USER_ID, null));
		user.put(USER_ID, pref.getString(USER_ID, null));

		/*
		 * // user email id user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,
		 * null));
		 * 
		 * // return user
		 */
		return user;
	}

	public HashMap<String, String> getUserId() {
		Log.i(TAG, "getUserId.....");
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		Log.i(TAG, "getUserId= " + pref.getString(USER_ID, null));
		user.put(USER_ID, pref.getString(USER_ID, null));

		/*
		 * // user email id user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,
		 * null));
		 * 
		 * // return user
		 */
		return user;
	}

	public HashMap<String, String> getUserName() {
		Log.i(TAG, "getUserName.....");
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		Log.i(TAG, "getUserName= " + pref.getString(USER_NAME, null));
		user.put(USER_NAME, pref.getString(USER_NAME, null));

		/*
		 * // user email id user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,
		 * null));
		 * 
		 * // return user
		 */
		return user;
	}

	public HashMap<String, String> getUserEmailId() {
		Log.i(TAG, "getUserEmailId.....");
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		Log.i(TAG, "getUserEmailId= " + pref.getString(USER_EAILID, null));
		user.put(USER_EAILID, pref.getString(USER_EAILID, null));

		/*
		 * // user email id user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,
		 * null));
		 * 
		 * // return user
		 */
		return user;
	}

	public HashMap<String, String> getUserPhone() {
		Log.i(TAG, "getUserPhone.....");
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		Log.i(TAG, "getUserPhone= " + pref.getString(USER_PHONE, null));
		user.put(USER_PHONE, pref.getString(USER_PHONE, null));

		/*
		 * // user email id user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,
		 * null));
		 * 
		 * // return user
		 */
		return user;
	}

	/**
	 * Clear session details
	 * */
	public void logoutUser() {
		flag = true;
		Log.i(TAG, " logoutUser..........");
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
		/*
		 * Log.i(TAG, "Usersuccessfully logout.........."); // After logout
		 * redirect user to Loing Activity Intent i = new Intent(_context,
		 * LoginActivity.class); // Closing all the Activities
		 * i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Add new Flag to start
		 * new Activity i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Staring
		 * Login Activity _context.startActivity(i);
		 */
	}

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn() {
		return pref.getBoolean(IS_LOGIN, false);
	}
}
