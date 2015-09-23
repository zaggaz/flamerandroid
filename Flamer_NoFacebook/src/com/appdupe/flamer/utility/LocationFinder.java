package com.appdupe.flamer.utility;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * GPS Check is a public class that allows to find out current location of user
 * via GPS and also provides a GUI for the same.The GUI has two buttons to stop
 * the location search & back button. Stopping will lead to manual PoS
 * screen.The UI is launched immediately after a new activity is started.
 */

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LocationFinder {
	private static String mDebugTag = "LocationFinder";
	private static boolean mDebugLog = true;

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

	Timer timer1;
	LocationManager lm;
	LocationResult locationResult;
	boolean gps_enabled = false;
	boolean network_enabled = false;

	public boolean getLocation(Context context, LocationResult result) {
		// I use LocationResult callback class to pass location value from
		// MyLocation to user code.
		locationResult = result;
		if (lm == null)
			lm = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);

		// exceptions will be thrown if provider is not permitted.
		try {
			gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
			logDebug("getLocation   gps_enabled " + gps_enabled);
		} catch (Exception ex) {
			logDebug("getLocation  gps_enabled Exception " + ex);
		}
		try {
			network_enabled = lm
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			logDebug("getLocation   network_enabled " + network_enabled);
		} catch (Exception ex) {
			logDebug("getLocation   network_enabled   Exception" + ex);
		}

		// don't start listeners if no provider is enabled
		if (!gps_enabled && !network_enabled) {
			return false;
		} else if (gps_enabled) {
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
					locationListenerGps);
			timer1 = new Timer();
			timer1.schedule(new GetLastLocation(), 0000);
			return true;
		}

		else if (network_enabled) {
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
					locationListenerNetwork);
			timer1 = new Timer();
			timer1.schedule(new GetLastLocation(), 0000);
			return true;
		} else {
			return false;
		}

	}

	LocationListener locationListenerGps = new LocationListener() {
		public void onLocationChanged(Location location) {
			timer1.cancel();
			locationResult.gotLocation(location.getLatitude(),
					location.getLongitude());
			lm.removeUpdates(this);
			lm.removeUpdates(locationListenerNetwork);
		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	};

	LocationListener locationListenerNetwork = new LocationListener() {
		public void onLocationChanged(Location location) {
			try {
				logDebug("getLocation   locationListenerNetwork   onLocationChanged");
				timer1.cancel();
				logDebug("getLocation   locationListenerNetwork  onLocationChanged getLatitude "
						+ location.getLatitude());
				logDebug("getLocation   locationListenerNetwork onLocationChanged getLongitude  "
						+ location.getLongitude());
				locationResult.gotLocation(location.getLatitude(),
						location.getLongitude());
				lm.removeUpdates(this);
				lm.removeUpdates(locationListenerGps);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		public void onProviderDisabled(String provider) {
			logDebug("getLocation   locationListenerNetwork   onProviderDisabled provider "
					+ provider);
		}

		public void onProviderEnabled(String provider) {
			logDebug("getLocation   locationListenerNetwork   onProviderEnabled provider "
					+ provider);
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			logDebug("getLocation   locationListenerNetwork   onStatusChanged provider "
					+ provider);
			logDebug("getLocation   locationListenerNetwork   onStatusChanged status "
					+ status);
			logDebug("getLocation   locationListenerNetwork   onStatusChanged extras "
					+ extras);
		}

	};

	// public String getDistance(GeoPoint mGeoPoint, GeoPoint passPoint1)
	// {
	// return CabforuGeocoder.getDistance(mGeoPoint, passPoint1);
	// }
	// public String getDistanceInmeter(GeoPoint geoPoint, GeoPoint geoPoint2)
	// {
	// return CabforuGeocoder.getDistanceInmeter( geoPoint, geoPoint2);
	// }
	//
	// public String gettiming(GeoPoint geoPoint, GeoPoint geoPoint2) {
	// // TODO Auto-generated method stub
	//
	// return CabforuGeocoder.getTimings( geoPoint, geoPoint2);
	// }
	// public String gettiming2(GeoPoint mGeoPoint, GeoPoint passPoint1) {
	// // TODO Auto-generated method stub
	// //System.out.println("LOCATION FINDER:>>>>>>>> " + mGeoPoint +
	// passPoint1);
	// return CabforuGeocoder.getTimings2(mGeoPoint, passPoint1);
	// }
	class GetLastLocation extends TimerTask {
		@Override
		public void run() {
			logDebug("getLocation  GetLastLocation  ");
			lm.removeUpdates(locationListenerGps);
			lm.removeUpdates(locationListenerNetwork);

			Location net_loc = null, gps_loc = null;
			logDebug("getLocation  GetLastLocation  gps_enabled " + gps_enabled);
			logDebug("getLocation  GetLastLocation  network_enabled "
					+ network_enabled);
			if (gps_enabled) {
				gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
			if (network_enabled) {
				net_loc = lm
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}

			// if there are both values use the latest one
			logDebug("getLocation  GetLastLocation  gps_loc " + gps_loc);
			logDebug("getLocation  GetLastLocation  net_loc " + net_loc);

			if (gps_loc != null && net_loc != null) {
				if (gps_loc.getTime() > net_loc.getTime()) {
					locationResult.gotLocation(gps_loc.getLatitude(),
							gps_loc.getLongitude());
				} else {
					locationResult.gotLocation(net_loc.getLatitude(),
							net_loc.getLongitude());
				}
				return;
			}

			if (gps_loc != null) {
				locationResult.gotLocation(gps_loc.getLatitude(),
						gps_loc.getLongitude());
				return;
			}
			if (net_loc != null) {
				locationResult.gotLocation(net_loc.getLatitude(),
						net_loc.getLongitude());
				return;
			}

			logDebug("getLocation  GetLastLocation  location not found ");
			locationResult.gotLocation(0, 0);
		}
	}

	public interface LocationResult {
		public void gotLocation(double latitude, double longitude);
	}

	public String getLocationName(Context context, double pLatitude,
			double pLongitude) {

		Geocoder mGeoCoder = new Geocoder(context);
		String detailedAddress = "";
		try {
			// System.out.println("lat and long for the address is:"+
			// pLatitude+"           "+pLongitude);
			List<Address> addresses = mGeoCoder.getFromLocation(pLatitude,
					pLongitude, 10); // <10>
			for (Address address : addresses) {
				detailedAddress += address.getAddressLine(0) + ",";
			}
			// System.out.println("address location name for driver is:"+
			// detailedAddress);
		} catch (IOException e) {
			Log.e("LocateMe", "Could not get Geocoder data", e);
		}
		return detailedAddress;

	}
}
