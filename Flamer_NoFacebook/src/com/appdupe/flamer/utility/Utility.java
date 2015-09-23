package com.appdupe.flamer.utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.json.JSONObject;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.provider.MediaStore;
import android.util.Log;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;

@SuppressWarnings("deprecation")
public class Utility extends Application {

	public static Facebook mFacebook;

	@SuppressWarnings("deprecation")
	public static ProgressDialog pd;
	public static AsyncFacebookRunner mAsyncRunner;
	public static JSONObject mFriendsList;
	public static String userUID = null;

	public static String objectID = null;
	// public static FriendsGetProfilePics model;
	public static AndroidHttpClient httpclient = null;
	public static Hashtable<String, String> currentPermissions = new Hashtable<String, String>();

	private static int MAX_IMAGE_DIMENSION = 720;

	// Need to fix this issue
	public static final String HACK_ICON_URL = "http://www.facebookmobileweb.com/hackbook/img/facebook_icon_large.png";

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

	public static Bitmap getBitmap(String url) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(new FlushedInputStream(is));
			bis.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.close();
			}
		}
		return bm;
	}

	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}

	public static final String getBase64String(Bitmap photoBitmap) {
		String photo;
		if (photoBitmap != null) {

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, bao);
			// photoBitmap.recycle();
			photo = android.util.Base64.encodeToString(bao.toByteArray(),
					android.util.Base64.DEFAULT);
			try {
				bao.close();
				bao = null;
				photoBitmap = null;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			photo = "";
		}
		return photo;
	}

	public static byte[] scaleImage(Context context, Uri photoUri)
			throws IOException {
		InputStream is = context.getContentResolver().openInputStream(photoUri);
		BitmapFactory.Options dbo = new BitmapFactory.Options();
		dbo.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, dbo);
		is.close();

		int rotatedWidth, rotatedHeight;
		int orientation = getOrientation(context, photoUri);

		if (orientation == 90 || orientation == 270) {
			rotatedWidth = dbo.outHeight;
			rotatedHeight = dbo.outWidth;
		} else {
			rotatedWidth = dbo.outWidth;
			rotatedHeight = dbo.outHeight;
		}

		Bitmap srcBitmap;
		is = context.getContentResolver().openInputStream(photoUri);
		if (rotatedWidth > MAX_IMAGE_DIMENSION
				|| rotatedHeight > MAX_IMAGE_DIMENSION) {
			float widthRatio = ((float) rotatedWidth)
					/ ((float) MAX_IMAGE_DIMENSION);
			float heightRatio = ((float) rotatedHeight)
					/ ((float) MAX_IMAGE_DIMENSION);
			float maxRatio = Math.max(widthRatio, heightRatio);

			// Create the bitmap from file
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = (int) maxRatio;
			srcBitmap = BitmapFactory.decodeStream(is, null, options);
		} else {
			srcBitmap = BitmapFactory.decodeStream(is);
		}
		is.close();

		/*
		 * if the orientation is not 0 (or -1, which means we don't know), we
		 * have to do a rotation.
		 */
		if (orientation > 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);

			srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
					srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
		}

		String type = context.getContentResolver().getType(photoUri);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (type.equals("image/png")) {
			srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		} else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
			srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		}
		byte[] bMapArray = baos.toByteArray();
		baos.close();
		return bMapArray;
	}

	public static int getOrientation(Context context, Uri photoUri) {
		/* it's on the external media. */
		Cursor cursor = context.getContentResolver().query(photoUri,
				new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
				null, null, null);

		if (cursor.getCount() != 1) {
			return -1;
		}

		cursor.moveToFirst();
		int orientation = cursor.getInt(0);
		cursor.close();

		return orientation;
	}

	public static void addBitmapToSdCardFromURL(String murl, File mFile) {
		Bitmap bm = null;
		try {
			URL url = new URL(murl);
			URLConnection connection = url.openConnection();
			connection.connect();
			// this will be useful so that you can show a typical 0-100%
			// progress bar
			int fileLength = connection.getContentLength();

			// download the file
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(mFile.getAbsolutePath());

			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				// publishProgress((int) (total * 100 / fileLength));
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
		} catch (Exception e) {
		}

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

	public static String convertDateIntoString(Date date, String outputFormat) {
		// Create an instance of SimpleDateFormat used for formatting
		// the string representation of date (month/day/year)
		DateFormat df = new SimpleDateFormat(outputFormat);
		// Get the date today using Calendar object.
		// Date today = Calendar.getInstance().getTime();
		// Using DateFormat format method we can create a string
		// representation of a date with the defined format.
		String reportDate = df.format(date);
		return reportDate;
	}

	public static void showProcess(Context context, String message) {
		closeprocess(context);
		pd = new ProgressDialog(context);
		pd.setMessage(message);
		pd.setCancelable(false);
		if (pd != null) {
			pd.show();
		}
	}

	public static void closeprocess(Context context) {
		if (pd != null) {
			pd.dismiss();
			pd = null;
		}
	}

}
