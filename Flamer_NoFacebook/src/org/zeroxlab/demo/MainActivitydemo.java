package org.zeroxlab.demo;

import java.util.ArrayList;

import org.zeroxlab.widget.AnimationLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.appdupe.flamer.utility.LocationFinder;
import com.appdupe.flamer.utility.LocationFinder.LocationResult;
import com.appdupe.flamer.utility.Ultilities;
import com.appdupe.flamernofb.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivitydemo extends FragmentActivity implements
		AnimationLayout.Listener {
	public final static String TAG = "Demo";

	protected ListView mList;
	protected AnimationLayout mLayout;
	protected String[] mStrings = { "a", "b", "c", "d", "e", "f", "g", "h", "i" };

	GoogleMap mGoogleMap;
	ArrayList<LatLng> mMarkerPoints;
	double mLatitude = 0;
	double mLongitude = 0;
	double dLatitude = 0;
	double dLongitude = 0;
	private LocationFinder newLocationFinder;

	private static boolean mDebugLog = true;
	private static String mDebugTag = "MainActivity";

	void logDebug(String msg) {

		if (mDebugLog) {
			Log.d(mDebugTag, msg);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mLayout = (AnimationLayout) findViewById(R.id.animation_layout);
		mLayout.setListener(this);

		// mList = (ListView) findViewById(R.id.sidebar_list);
		// mList.setAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_multiple_choice,mStrings));
		// mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		if (Ultilities.isNetworkAvailable(this)) {
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				mMarkerPoints = new ArrayList<LatLng>();

				// Getting reference to SupportMapFragment of the activity_main
				SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
						.findFragmentById(R.id.map);
				logDebug("onCreate fm  " + fm);
				// Getting Map for the SupportMapFragment
				mGoogleMap = fm.getMap();
				logDebug("onCreate mGoogleMap  " + mGoogleMap);
				// Enable MyLocation Button in the Map
				mGoogleMap.setMyLocationEnabled(true);
				mGoogleMap.getUiSettings().setScrollGesturesEnabled(false);
				mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
				mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
				newLocationFinder = new LocationFinder();
				newLocationFinder.getLocation(MainActivitydemo.this,
						mLocationResult);

				// mGoogleMap.setOnMapClickListener(new OnMapClickListener() {
				//
				// @Override
				// public void onMapClick(LatLng arg0)
				// {
				// // TODO Auto-generated method stub
				// logDebug("setOnMapClickListener   onn mapclick listner call ");
				//
				// }
				// });

			} else {
				showGPSDisabledAlertToUser();

			}

		} else {
			Ultilities mUltilities = new Ultilities();

			mUltilities.displayMessageAndExit(
					this,
					getResources().getString(
							R.string.internetconnectionerrortitle),
					getResources().getString(
							R.string.internetconnectionerrormessage));
		}

	}

	LocationResult mLocationResult = new LocationResult() {
		public void gotLocation(final double latitude, final double longitude) {
			// TODO Auto-generated method stub
			System.out.println("Got Location...Lat:" + String.valueOf(latitude)
					+ "Long:" + String.valueOf(longitude));
			if (latitude == 0.0 || longitude == 0.0) {

				return;
			} else {

				runOnUiThread(new Runnable() {
					public void run() {
						mLatitude = latitude;
						mLongitude = longitude;
						LatLng point = new LatLng(mLatitude, mLongitude);
						mGoogleMap.moveCamera(CameraUpdateFactory
								.newLatLng(point));
						mGoogleMap
								.animateCamera(CameraUpdateFactory.zoomTo(17));
						drawMarker(point);
						// dLatitude=28.418733;
						// dLongitude= -81.581183;
						// LatLng dpoint = new LatLng(dLatitude, dLongitude);
						// drawMarker(dpoint);
						// LatLng origin = mMarkerPoints.get(0);
						// LatLng dest = mMarkerPoints.get(1);
						//
						// // Getting URL to the Google Directions API
						// String url = getDirectionsUrl(origin, dest);
						//
						// DownloadTask downloadTask = new DownloadTask();
						//
						// // Start downloading json data from Google Directions
						// API
						// downloadTask.execute(url);
					}
				});

			}
		}
	};

	private void drawMarker(LatLng point) {

		mMarkerPoints.add(point);

		// Creating MarkerOptions
		MarkerOptions options = new MarkerOptions();

		// Setting the position of the marker
		options.position(point);

		/**
		 * For the start location, the color of marker is GREEN and for the end
		 * location, the color of marker is RED.
		 */
		if (mMarkerPoints.size() == 1) {
			options.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		} else if (mMarkerPoints.size() == 2) {
			options.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		}

		// Add new marker to the Google Map Android API V2
		mGoogleMap.addMarker(options);
	}

	public void onClickContentButton(View v) {
		mLayout.toggleSidebar();
	}

	@Override
	public void onBackPressed() {
		if (mLayout.isOpening()) {
			mLayout.closeSidebar();
		} else {
			finish();
		}
	}

	/* Callback of AnimationLayout.Listener to monitor status of Sidebar */
	@Override
	public void onSidebarOpened() {
		Log.d(TAG, "opened");
	}

	/* Callback of AnimationLayout.Listener to monitor status of Sidebar */
	@Override
	public void onSidebarClosed() {
		Log.d(TAG, "opened");
	}

	/* Callback of AnimationLayout.Listener to monitor status of Sidebar */
	@Override
	public boolean onContentTouchedWhenOpening() {
		// the content area is touched when sidebar opening, close sidebar
		Log.d(TAG, "going to close sidebar");
		mLayout.closeSidebar();
		return true;
	}

	private void showGPSDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(getResources().getString(R.string.gpsenablemessage))
				.setCancelable(false)
				.setPositiveButton(
						getResources().getString(R.string.button_ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(callGPSSettingIntent);
							}
						});
		alertDialogBuilder.setNegativeButton(
				getResources().getString(R.string.button_cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}
}
