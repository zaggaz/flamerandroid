package com.appdupe.androidpushnotifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.slidingmenu.MainActivity;
import com.appdupe.flamer.utility.Constant;
import com.appdupe.flamernofb.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
	private static final String TAG = "GcmIntentService";
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	private Intent notificationIntent;

	public GcmIntentService() {
		super("775207507530");
		// 809151122508
		// 27585530419
		// 323762218364
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(TAG, "push log onHandleIntent......" + intent);
		Bundle extras = intent.getExtras();

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString(), extras);
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification(
						"Deleted messages on server: " + extras.toString(),
						extras);
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				// This loop represents the service doing some work.
				/*
				 * for (int i=0; i<5; i++) { Log.i(TAG, "Working... " + (i+1) +
				 * "/5 @ " + SystemClock.elapsedRealtime()); try {
				 * Thread.sleep(5000); } catch (InterruptedException e) { } }
				 */
				Log.i(TAG,
						"push log Completed work @ "
								+ SystemClock.elapsedRealtime());
				// Post notification of received message
				String message = null;
				if (extras != null) {
					message = extras.getString("payload");
				}
				sendNotification(message/*
										 * "Received: " +
										 * extras.toString(),extras
										 */, extras);
				Log.i(TAG, "push log Received: " + extras.toString());
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		// GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg, Bundle extras) {
		Log.i(TAG, "push log sendNotification  msg " + msg);
		//
		/*
		 * SessionManager session=new SessionManager(this); boolean
		 * bFlagForCurrent=session.isFirstScreen(); Log.i(TAG,
		 * "sendNotification bFlagForCurrent........."+bFlagForCurrent);
		 */
		int icon = R.drawable.new_ic_icon;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, msg, when);

		String title = this.getString(R.string.app_name);
		String action = extras.getString("action");
		// PendingIntent intent = null;
		/*
		 * if (action!=null) { if (action.equals("3")) { Intent
		 * notificationIntent = new Intent(this, ChatActivity.class); // set
		 * intent so it does not start a new activity
		 * 
		 * notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		 * Intent.FLAG_ACTIVITY_SINGLE_TOP); extras.putString("sfid" ,
		 * extras.getString("sfid")); intent =PendingIntent.getActivity(this, 0,
		 * notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT); } else {
		 * Intent notificationIntent = new Intent(this, ChatActivity.class); //
		 * set intent so it does not start a new activity
		 * extras.putString(CommanConstant.CHECK_FOR_PUSH_OR_NOT , "2");
		 * notificationIntent.putExtra("PUSH_MESSAGE_BUNDLE", extras); //
		 * notificationIntent.putExtra(IntentConstant.PUSH_INTENT_CONSTANT,
		 * msg); notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		 * Intent.FLAG_ACTIVITY_SINGLE_TOP); intent
		 * =PendingIntent.getActivity(this, 0, notificationIntent,
		 * PendingIntent.FLAG_UPDATE_CURRENT); }
		 */
		if (action.equals("3")) {
			notificationIntent = new Intent(this, MainActivity.class);
		} else {
			notificationIntent = new Intent(this, ChatActivity.class);
		}
		// set intent so it does not start a new activity
		// notificationIntent.putExtra("PUSH_MESSAGE", msg);
		extras.putString(Constant.CHECK_FOR_PUSH_OR_NOT, "2");
		notificationIntent.putExtra("PUSH_MESSAGE_BUNDLE", extras);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(this, title, msg, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// Play default notification sound
		notification.defaults |= Notification.DEFAULT_SOUND;

		// notification.sound = Uri.parse("android.resource://" +
		// context.getPackageName() + "your_sound_file_name.mp3");

		// Vibrate if vibrate is enabled
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(0, notification);

	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	/*
	 * private void sendNotification(String msg) { mNotificationManager =
	 * (NotificationManager)
	 * this.getSystemService(Context.NOTIFICATION_SERVICE);
	 * 
	 * PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new
	 * Intent(this, DemoActivity.class), 0);
	 * 
	 * NotificationCompat.Builder mBuilder = new
	 * NotificationCompat.Builder(this) .setSmallIcon(R.drawable.ic_launcher)
	 * .setContentTitle("GCM Notification") .setStyle(new
	 * NotificationCompat.BigTextStyle() .bigText(msg)) .setContentText(msg);
	 * 
	 * mBuilder.setContentIntent(contentIntent);
	 * mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build()); }
	 */
}
