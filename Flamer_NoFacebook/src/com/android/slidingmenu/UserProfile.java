package com.android.slidingmenu;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.appdupe.flamer.model.KeyValuePair;
import com.appdupe.flamer.model.ProfileImageModel;
import com.appdupe.flamer.utility.AppLog;
import com.appdupe.flamer.utility.ConnectionDetector;
import com.appdupe.flamer.utility.Constant;
import com.appdupe.flamer.utility.ExtendedGallery;
import com.appdupe.flamer.utility.HttpRequest;
import com.appdupe.flamer.utility.JsonParser;
import com.appdupe.flamer.utility.Ultilities;
import com.appdupe.flamer.utility.Utility;
import com.appdupe.flamernofb.R;

public class UserProfile extends Fragment {

	private static final String TAG = "UserProfileFragment";
	private ExtendedGallery imageExtendedGallery;
	private ArrayList<String> imageList;
	private ImageAdapterForGellary mAdapterForGellary;
	private LinearLayout count_layout;
	private int count;
	private TextView[] page_text;
	private TextView usernametextivew, ueragetextviw, aboutuserTextview,
			userboutTextview, userStatus;
	private LinearLayout userAboutLayout;
	private ProgressDialog mDialog;
	private int[] imageHeightandWIdth;
	private ConnectionDetector cd;
	private SharedPreferences preferences;
	private Editor editor;

	@Override
	public View getView() {
		return super.getView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout2, null);

		preferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		editor = preferences.edit();
		Ultilities ultilities = new Ultilities();
		Typeface HelveticaInseratLTStd_Roman = Typeface.createFromAsset(
				getActivity().getAssets(),
				"fonts/HelveticaInseratLTStd-Roman.otf");
		Typeface HelveticaLTStd_Light = Typeface.createFromAsset(getActivity()
				.getAssets(), "fonts/HelveticaLTStd-Light.otf");
		imageHeightandWIdth = ultilities
				.getImageHeightAndWidthForProfileGellary(getActivity());

		imageExtendedGallery = (ExtendedGallery) view
				.findViewById(R.id.imageExtendedGallery);
		userboutTextview = (TextView) view.findViewById(R.id.userboutTextview);
		usernametextivew = (TextView) view.findViewById(R.id.usernametextivew);
		ueragetextviw = (TextView) view.findViewById(R.id.ueragetextviw);
		count_layout = (LinearLayout) view.findViewById(R.id.image_count);
		aboutuserTextview = (TextView) view
				.findViewById(R.id.aboutuserTextview);
		userAboutLayout = (LinearLayout) view
				.findViewById(R.id.userAboutLayout);
		userAboutLayout.setVisibility(View.GONE);
		userStatus = ((TextView) view.findViewById(R.id.userStatustextView));
		if (preferences.getInt(Constant.PREF_USER_AGE, 0) != 0) {
			usernametextivew.setText(preferences.getString(
					Constant.PREF_FIRST_NAME, "")
					+ " "
					+ preferences.getString(Constant.PREF_LAST_NAME, "")
					+ "  "
					+ preferences.getInt(Constant.PREF_USER_AGE, 0));
		} else {

		}

		imageList = new ArrayList<String>();
		mAdapterForGellary = new ImageAdapterForGellary(getActivity(),
				imageList);

		usernametextivew.setTypeface(HelveticaInseratLTStd_Roman);
		ueragetextviw.setTypeface(HelveticaLTStd_Light);
		userboutTextview.setTypeface(HelveticaInseratLTStd_Roman);
		aboutuserTextview.setTypeface(HelveticaInseratLTStd_Roman);
		imageExtendedGallery.setAdapter(mAdapterForGellary);
		cd = new ConnectionDetector(getActivity());
		if (cd.isConnectingToInternet()) {
			getProfileImages();
		} else {
			Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT)
					.show();
		}
		return view;
	}

	private void getProfileImages() {
		Utility.showProcess(getActivity(), "Getting Images..");
		final HttpRequest httpRequest = new HttpRequest();
		final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new KeyValuePair(Constant.KEY_FACEBOOK_ID,
				preferences.getString(Constant.FACEBOOK_ID, "")));
		new Thread(new Runnable() {
			@Override
			public void run() {
				String json;
				try {
					json = httpRequest.postData(Constant.get_user_pro_pic,
							nameValuePairs);
					AppLog.Log(TAG, "Image json:" + json);
					ArrayList<ProfileImageModel> List = JsonParser
							.parseProfileImageData(json);
					AppLog.Log(TAG, "Return Image Size:" + List.size());
					SetListIntoPref(List);

					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							imageList = getImagesFromPref();
							setAdapterForGallery(imageList);
							Utility.closeprocess(getActivity());

						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!preferences.getString(Constant.PREF_USER_STATUS, "").equals("")) {
			userStatus.setText(" :  "
					+ preferences.getString(Constant.PREF_USER_STATUS, ""));
		} else {
			userStatus.setText(" :  N/A");
		}
		preferences.getString(Constant.PREF_USER_ABOUT, "");
		imageList = getImagesFromPref();
		setAdapterForGallery(imageList);

	}

	private void setAdapterForGallery(ArrayList<String> List) {
		count = 0;
		page_text = null;
		mAdapterForGellary.notifyDataSetChanged();
		count = List.size();
		AppLog.Log(TAG, "List Size:" + count);
		page_text = new TextView[count];
		count_layout.removeAllViews();
		for (int i = 0; i < count; i++) {
			page_text[i] = new TextView(getActivity());
			page_text[i].setText(".");
			page_text[i].setTextSize(45);
			page_text[i].setTypeface(null, Typeface.BOLD);
			page_text[i].setTextColor(android.graphics.Color.GRAY);
			count_layout.addView(page_text[i]);
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

	protected void SetListIntoPref(ArrayList<ProfileImageModel> List) {
		if (List.size() > 0) {
			for (int i = 0; i < List.size(); i++) {
				final ProfileImageModel imageModel = List.get(i);
				int imageIndex = imageModel.getIndexId();
				switch (imageIndex) {
				case 0:
					editor.putString(Constant.PREF_PROFILE_IMAGE_ONE,
							imageModel.getImageUrl());
					editor.commit();
					break;
				case 1:
					editor.putString(Constant.PREF_PROFILE_IMAGE_TWO,
							imageModel.getImageUrl());
					editor.commit();
					break;
				case 2:
					editor.putString(Constant.PREF_PROFILE_IMAGE_THREE,
							imageModel.getImageUrl());
					editor.commit();
					break;
				case 3:
					editor.putString(Constant.PREF_PROFILE_IMAGE_FOUR,
							imageModel.getImageUrl());
					editor.commit();
					break;
				case 4:
					editor.putString(Constant.PREF_PROFILE_IMAGE_FIVE,
							imageModel.getImageUrl());
					editor.commit();
					break;
				case 5:
					editor.putString(Constant.PREF_PROFILE_IMAGE_SIX,
							imageModel.getImageUrl());
					editor.commit();
					break;
				default:
					break;
				}
			}

		}

	}

	private ArrayList<String> getImagesFromPref() {
		String photoOne = preferences.getString(
				Constant.PREF_PROFILE_IMAGE_ONE, null);
		String photoTwo = preferences.getString(
				Constant.PREF_PROFILE_IMAGE_TWO, null);
		String photoThree = preferences.getString(
				Constant.PREF_PROFILE_IMAGE_THREE, null);
		String photoFour = preferences.getString(
				Constant.PREF_PROFILE_IMAGE_FOUR, null);
		String photoFive = preferences.getString(
				Constant.PREF_PROFILE_IMAGE_FIVE, null);
		String photoSix = preferences.getString(
				Constant.PREF_PROFILE_IMAGE_SIX, null);
		imageList.clear();
		if (photoOne != null) {
			imageList.add(photoOne);
		}
		if (photoTwo != null) {
			imageList.add(photoTwo);
		}
		if (photoThree != null) {
			imageList.add(photoThree);
		}
		if (photoFour != null) {
			imageList.add(photoFour);
		}
		if (photoFive != null) {
			imageList.add(photoFive);
		}
		if (photoSix != null) {
			imageList.add(photoSix);
		}
		return imageList;

	}

	@Override
	public void onPause() {
		super.onPause();
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	private class ImageAdapterForGellary extends ArrayAdapter<String> {

		Activity mActivity = getActivity();
		private LayoutInflater mInflater;
		AQuery aQuery;
		private ImageOptions options;

		public ImageAdapterForGellary(Context context, List<String> objects) {
			super(context, R.layout.galleritem, objects);
			mInflater = (LayoutInflater) mActivity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			Log.i(TAG, "Profile image list size:" + objects.size());
			options = new ImageOptions();
			options.memCache = true;
			options.fileCache = true;
			options.animation = AQuery.FADE_IN;
		}

		@Override
		public int getCount() {
			return super.getCount();
		}

		@Override
		public String getItem(int position) {
			return super.getItem(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {

				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.galleritem, null);
				aQuery = new AQuery(convertView);
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
			if (getItem(position) != null) {
				// holder.mProgressBar.setVisibility(View.GONE);
				// holder.imageview.setImageBitmap(getItem(position).getmBitmap()
				aQuery.id(holder.imageview).image(getItem(position), options)
						.progress(R.id.pbGalleryItemImage);
				// /*
				// * Bitmap. createScaledBitmap ( getItem ( position ).
				// getmBitmap
				// * (), imageheightandWidth [1], imageheightandWidth [0], false
				// )
				// */);
			} else {
				Log.i(TAG, "image bitmap is null");
			}
			return convertView;
		}

		class ViewHolder {
			ImageView imageview;
			// ProgressBar mProgressBar;
		}
	}

}
