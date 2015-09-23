package com.appdupe.androidpushnotifications;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.appdupe.flamer.pojo.LikeMatcheddataForListview;
import com.appdupe.flamer.pojo.LikedMatcheData;
import com.appdupe.flamer.pojo.Likes;
import com.appdupe.flamer.utility.AppLog;
import com.appdupe.flamer.utility.Constant;
import com.appdupe.flamer.utility.SessionManager;
import com.appdupe.flamer.utility.Ultilities;
import com.appdupe.flamerchat.db.DatabaseHandler;
import com.appdupe.flamernofb.R;
import com.google.gson.Gson;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
	private static final String TAG = "GcmBroadcastReceiver";
	Context con;
	private String userFacebookid;
	private String strMessageID;
	private String strMessageType;
	private static boolean mDebugLog = true;
	private static String mDebugTag = "GcmBroadcastReceiver";
	private SharedPreferences preferences;

	// void logDebug(String msg) {
	//
	// if (mDebugLog) {
	// Log.d(mDebugTag, msg);
	// }
	// }
	//
	// void logError(String msg) {
	//
	// if (mDebugLog) {
	// Log.e(mDebugTag, msg);
	// }
	// }

	@Override
	public void onReceive(Context context, Intent intent) {
		con = context;
		preferences = PreferenceManager.getDefaultSharedPreferences(con);
		Log.i("GcmBroadcastReceiver...............",
				"push log onReceive push demo project...............  "
						+ intent);
		SessionManager session = new SessionManager(context);
		boolean bFlagForCurrent = session.isFirstScreen();
		// userFacebookid = session.getFacebookId();
		userFacebookid = preferences.getString(Constant.FACEBOOK_ID, "");
		Log.i("", "push log sendNotification bFlagForCurrent........."
				+ bFlagForCurrent);
		Bundle extras = intent.getExtras();
		AppLog.Log("TAG", "Extras:" + extras);
		String message = extras.getString("payload");
		String action = extras.getString("action");
		strMessageType = extras.getString("mt");
		strMessageID = extras.getString("mid");
		String strSenderName = extras.getString("sname");
		String strDateTime = extras.getString("dt");
		String strFacebookId = extras.getString("sfid");

		session.setLastMessage(strFacebookId,
				message.substring(message.indexOf(":") + 1, message.length()));
		fineLikedMatched(extras);
		Log.i("", "GcmBroadcastReceiver  action...... " + action);
		if (action.equals("3")) {
			ComponentName comp = new ComponentName(context.getPackageName(),
					GcmIntentService.class.getName());
			// Start the service, keeping the device awake while it is
			// launching.
			startWakefulService(context, (intent.setComponent(comp)));
			setResultCode(Activity.RESULT_OK);

		} else {

			if (bFlagForCurrent) {

				Log.i("", "push log onReceive  extras......." + extras);

				Log.i("GcmBroadcastReceiver...............",
						"push log onReceive push demo strMessageType...............  "
								+ strMessageType);
				Log.i("GcmBroadcastReceiver...............",
						"push log onReceive push demo strMessageID...............  "
								+ strMessageID);
				Log.i("", "push log GcmBroadcastReceiver message......."
						+ message);
				Intent homeIntent = new Intent(
						"com.embed.anddroidpushntificationdemo11.push");

				homeIntent.putExtra("MESSAGE_FOR_PUSH", message);
				homeIntent.putExtra("MESSAGE_FOR_PUSH_ACTION", action);
				homeIntent.putExtra("MESSAGE_FOR_PUSH_MESSAGETYPE",
						strMessageType);
				homeIntent.putExtra("MESSAGE_FOR_PUSH_MESSAGEID", strMessageID);
				homeIntent.putExtra("MESSAGE_FOR_PUSH_SENDERNAME",
						strSenderName);
				homeIntent.putExtra("MESSAGE_FOR_PUSH_DATETIME", strDateTime);
				homeIntent.putExtra("MESSAGE_FOR_PUSH_FACEBOOKID",
						strFacebookId);
				context.sendBroadcast(homeIntent);
			} else {
				Log.i("", "push log onReceive  extras......." + extras);

				ChatMessageList params = new ChatMessageList();
				params.setStrMessage(message);
				params.setStrSenderFacebookId(strFacebookId);
				params.setStrSenderId(strFacebookId);
				params.setStrReceiverId(userFacebookid);
				params.setStrDateTime(strDateTime);
				params.setStrSendername(strSenderName);
				params.setStrFlagForMessageSuccess("1");

				new BackGroundForSaveChat().execute(params);
				ComponentName comp = new ComponentName(
						context.getPackageName(),
						GcmIntentService.class.getName());
				// Start the service, keeping the device awake while it is
				// launching.
				startWakefulService(context, (intent.setComponent(comp)));
				setResultCode(Activity.RESULT_OK);
			}

			// Explicitly specify that GcmIntentService will handle the intent.
		}

	}

	private void fineLikedMatched(Bundle extras) {

		AppLog.Log(TAG, "GcmBroadcastReceiver fineLikedMatched");

		try {
			String deviceid = Ultilities.getDeviceId(con);
			AppLog.Log(TAG, "GcmBroadcastReceiver fineLikedMatched   deviceid"
					+ deviceid);
			SessionManager mSessionManager = new SessionManager(con);
			String sessionToken = mSessionManager.getUserToken();
			AppLog.Log(TAG,
					"GcmBroadcastReceiver fineLikedMatched   sessionToken"
							+ sessionToken);
			Ultilities mUltilitie = new Ultilities();
			String currentdeviceTime = mSessionManager.getLastUpdatedTime();
			String curenttime = mUltilitie.getCurrentGmtTime();
			mSessionManager.setLastUpdate(curenttime);
			/*
			 * if (currentdeviceTime!=null&&currentdeviceTime.length()>0) {
			 * 
			 * } else { currentdeviceTime=mUltilitie.getCurrentGmtTime();
			 * mSessionManager.setLastUpdate(currentdeviceTime); }
			 */

			Bundle params[] = { extras };
			new BackgroundTaskForFindLikeMatched().execute(params);
		} catch (Exception e) {
			// TODO: handle exception
			AppLog.Log(TAG, "GcmBroadcastReceiver fineLikedMatched Exception "
					+ e);
		}

		/* String currentdeviceTime; */

	}

	private class BackgroundTaskForFindLikeMatched extends
			AsyncTask<Bundle, Void, Void> {

		private Ultilities mUltilities = new Ultilities();
		private List<NameValuePair> getuserparameter;
		private String likedmatchedata;
		private LikedMatcheData matcheData;
		private ArrayList<Likes> likesList;
		private LikeMatcheddataForListview matcheddataForListview;
		private Bundle bundle;

		@Override
		protected Void doInBackground(Bundle... params) {
			// TODO Auto-generated method stub
			try {
				bundle = params[0];

				File appDirectory = mUltilities.createAppDirectoy(con
						.getResources().getString(R.string.appdirectory));
				AppLog.Log(TAG,
						"GcmBroadcastReceiver   doInBackground appDirectory "
								+ appDirectory);
				File _picDir = new File(appDirectory, con.getResources()
						.getString(R.string.imagedirematchuserdirectory));
				AppLog.Log(TAG, "GcmBroadcastReceiver doInBackground ");
				// getuserparameter=mUltilities.getUserLikedParameter(params);
				// logDebug("GcmBroadcastReceiver doInBackground   getuserparameter "+getuserparameter);
				// // likedmatchedata=
				// mUltilities.makeHttpRequest(CommanConstant.getliked_url,CommanConstant.methodeName,getuserparameter);
				AppLog.Log(TAG,
						"GcmBroadcastReceiver doInBackground   likedmatchedata "
								+ likedmatchedata);
				// Gson gson = new Gson();
				// matcheData= gson.fromJson(likedmatchedata,
				// LikedMatcheData.class);
				// logDebug("GcmBroadcastReceiver doInBackground   matcheData "+matcheData);

				// "errNum": "51",
				// "errFlag": "0",
				// "errMsg": "Matches found!",

				// if (matcheData.getErrFlag()==0)
				// {
				likesList = new ArrayList<Likes>();

				Likes likes = new Likes();
				String imgaeName = bundle.getString("mid");
				String imageUrl = Constant.ImageHostUrl + imgaeName;
				String userFName = bundle.getString("sname");
				String senderFacebookId = bundle.getString("sfid");
				int activeFlag = 3;
				String userFaceBookidFromPush = bundle.getString("from");
				String curenttime = mUltilities.getCurrentGmtTime();

				AppLog.Log(TAG,
						"GcmBroadcastReceiver doInBackground   likesList "
								+ likesList);
				likes.setpPic(imageUrl);
				likes.setfName(userFName);
				likes.setFbId(senderFacebookId);

				likes.setFlag(activeFlag);
				likes.setLadt(curenttime);

				likesList.add(likes);
				AppLog.Log(TAG,
						"GcmBroadcastReceiver doInBackground   imgaeName "
								+ imgaeName);
				AppLog.Log(TAG,
						"GcmBroadcastReceiver doInBackground   imageUrl"
								+ imageUrl);
				AppLog.Log(TAG,
						"GcmBroadcastReceiver doInBackground   userFName "
								+ userFName);
				AppLog.Log(TAG,
						"GcmBroadcastReceiver doInBackground   senderFacebookId "
								+ senderFacebookId);
				AppLog.Log(TAG,
						"GcmBroadcastReceiver doInBackground   activeFlag "
								+ activeFlag);
				AppLog.Log(TAG,
						"GcmBroadcastReceiver doInBackground   userFaceBookidFromPush "
								+ userFaceBookidFromPush);
				AppLog.Log(TAG,
						"GcmBroadcastReceiver doInBackground   curenttime "
								+ curenttime);
				ArrayList<LikeMatcheddataForListview> arryList = new ArrayList<LikeMatcheddataForListview>();
				for (int i = 0; i < likesList.size(); i++) {

					/*
					 * ArrayList<LikeMatcheddataForListview> matchlist=new
					 * ArrayList<LikeMatcheddataForListview>();
					 * LikeMatcheddataForListview objMatchData=new
					 * LikeMatcheddataForListview();
					 * objMatchData.setFacebookid(mActionData.getuFbId());
					 * objMatchData.setFilePath(imageFile.getAbsolutePath());
					 * objMatchData.setUserName(mActionData.getuName());
					 * objMatchData.setFlag("3");
					 * objMatchData.setImageUrl(mActionData.getpPic());
					 * //objMatchData.setmBitmap(mActionData.getuFbId());
					 * Ultilities mUltilitie=new Ultilities();
					 * 
					 * String curenttime=mUltilitie.getCurrentGmtTime();
					 * objMatchData.setladt(curenttime);
					 */

					matcheddataForListview = new LikeMatcheddataForListview();
					String userName = likesList.get(i).getfName();
					String facebookid = likesList.get(i).getFbId();
					// Log.i(TAG, "Background facebookid......"+facebookid);
					String picturl = likesList.get(i).getpPic();
					int falg = likesList.get(i).getFlag();
					matcheddataForListview.setFacebookid(facebookid);
					matcheddataForListview.setUserName(userName);
					matcheddataForListview.setImageUrl(picturl);
					matcheddataForListview.setFlag("" + falg);
					matcheddataForListview.setladt(likesList.get(i).getLadt());
					// matcheddataForListview.setFilePath(filePath);
					File imageFile = mUltilities.createFileInSideDirectory(
							_picDir, userName + facebookid + ".jpg");
					AppLog.Log(TAG,
							"GcmBroadcastReceiver doInBackground imageFile is profile "
									+ imageFile.isFile());
					com.appdupe.flamer.utility.Utility
							.addBitmapToSdCardFromURL(likesList.get(i)
									.getpPic().replaceAll(" ", "%20"),
									imageFile);
					matcheddataForListview.setFilePath(imageFile
							.getAbsolutePath());
					arryList.add(matcheddataForListview);

				}
				AppLog.Log(TAG, "GcmBroadcastReceiver  listsize is arryList "
						+ arryList.size());
				DatabaseHandler mDatabaseHandler = new DatabaseHandler(con);
				AppLog.Log(TAG, "GcmBroadcastReceiver  mDatabaseHandler "
						+ mDatabaseHandler);
				SessionManager mSessionManager = new SessionManager(con);
				AppLog.Log(TAG, "GcmBroadcastReceiver  mSessionManager "
						+ mSessionManager);
				String userFacebookid = mSessionManager.getFacebookId();
				AppLog.Log(TAG, "GcmBroadcastReceiver  userFacebookid "
						+ userFacebookid);
				AppLog.Log(TAG, "GcmBroadcastReceiver  userFaceBookidfrompush "
						+ userFaceBookidFromPush);

				boolean isdataiserted = mDatabaseHandler.insertMatchList(
						arryList, userFacebookid);
				AppLog.Log(TAG, "GcmBroadcastReceiver  isdataiserted "
						+ isdataiserted);

				// }
			} catch (Exception e) {
				e.printStackTrace();
				AppLog.Log(TAG, "GcmBroadcastReceiver  Exception " + e);
			}

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	private class BackGroundForSaveChat extends
			AsyncTask<ChatMessageList, Void, String> {

		@Override
		protected String doInBackground(ChatMessageList... params) {
			// TODO Auto-generated method stub

			if (strMessageType.equals("2")) {
				String sendMessageUrl = "http://108.166.190.172:81/tinderClone/process.php/getChatMessage";
				Utility myUtility = new Utility();
				SessionManager session = new SessionManager(con);
				String sessionId = session.getUserToken();
				String deviceId = Ultilities.getDeviceId(con);
				String params1[] = { sessionId, deviceId, strMessageID };
				List<NameValuePair> sendMessageReqList = myUtility
						.getPullMessageReq(params1);
				String messageResponse = myUtility.makeHttpRequest(
						sendMessageUrl, "POST", sendMessageReqList);
				ChatMessageData objChatMessage = null;
				if (messageResponse != null) {
					Gson gson = new Gson();
					objChatMessage = gson.fromJson(messageResponse,
							ChatMessageData.class);
				}
				if (objChatMessage != null) {
					if (objChatMessage.getErrFlag() == 0) {
						params[0].setStrMessage(objChatMessage.getListChat()
								.get(0).getStrMessage());
					} else {

					}
				} else {

				}
			} else {

			}

			DatabaseHandler objDbHandler = new DatabaseHandler(con);
			boolean bFlag = objDbHandler.insertMessageData(params[0]);
			Log.i("", "BackGroundForSaveChat bFlag......" + bFlag);
			return null;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
}
