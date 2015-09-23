package com.android.slidingmenu;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.appdupe.flamer.utility.HttpRequest;
import com.appdupe.flamer.utility.JsonParser;
import com.appdupe.flamer.utility.Utility;
import com.appdupe.flamernofb.R;

public class EditProfileNew extends Activity implements OnClickListener {
	private ImageView firstImage, secondImage, thirdImage, fourthImage,
			fifthImage, sixImage;
	private LinearLayout deleteLayout;
	private int selectedImage;
	private String firstImageUrl, secondImageUrl, thirdImageUrl,
			fourthImageUrl, fifthImageUrl, sixthImageUrl, imagePath;
	private TextView editprofileTextivew;
	private EditText edtStatus;
	private SharedPreferences preferences;
	private Editor editor;
	private AQuery aQuery;
	private Button btnEdit, btnDelete, btnSetProfile, btnSubmit;
	private File imageFile;
	private Uri imageUri;
	private Bitmap photoBitmap;
	private static final int CAMERA_REQUEST_CODE = 100;
	private static final int RESULT_LOAD_IMAGE = 101;
	private static final String TAG = "EditProfileNew";
	private int mobile_width = 480;
	int errorFlag = 1;
	private ConnectionDetector detector;
	private ImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_profile_new);
		initData();
	}

	private void initData() {
		aQuery = new AQuery(this);
		detector = new ConnectionDetector(this);
		options = new ImageOptions();
		options.fileCache = true;
		options.memCache = true;
		options.animation = AQuery.FADE_IN;
		firstImage = (ImageView) findViewById(R.id.userprofilpicture);
		secondImage = (ImageView) findViewById(R.id.firtstImage);
		thirdImage = (ImageView) findViewById(R.id.secondImage);
		fourthImage = (ImageView) findViewById(R.id.thirdimageview);
		fifthImage = (ImageView) findViewById(R.id.fourtthimageview);
		sixImage = (ImageView) findViewById(R.id.fifthImageview);
		editprofileTextivew = (TextView) findViewById(R.id.editprofileTextivew);
		edtStatus = (EditText) findViewById(R.id.edtStatusS);
		deleteLayout = (LinearLayout) findViewById(R.id.deleteandeditbuttonlayout);
		btnEdit = (Button) findViewById(R.id.editbuton);
		btnDelete = (Button) findViewById(R.id.deletebutton);
		btnSetProfile = (Button) findViewById(R.id.setProfileButton);
		btnSubmit = (Button) findViewById(R.id.btnEditProfileSubmit);
		btnSubmit.setOnClickListener(this);
		btnEdit.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnSetProfile.setOnClickListener(this);
		firstImage.setOnClickListener(this);
		secondImage.setOnClickListener(this);
		thirdImage.setOnClickListener(this);
		fourthImage.setOnClickListener(this);
		fifthImage.setOnClickListener(this);
		sixImage.setOnClickListener(this);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();
		Typeface topbartextviewFont = Typeface.createFromAsset(getAssets(),
				"fonts/HelveticaLTStd-Light.otf");
		editprofileTextivew.setTypeface(topbartextviewFont);
		editprofileTextivew.setTextColor(Color.rgb(255, 255, 255));
		editprofileTextivew.setTextSize(20);
		edtStatus.setText(preferences.getString(Constant.PREF_USER_STATUS, ""));
		if (detector.isConnectingToInternet()) {
			getProfileImages();
		} else {
			Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
			return;
		}

	}

	private void getDataFromPref() {
		firstImageUrl = preferences.getString(Constant.PREF_PROFILE_IMAGE_ONE,
				"");
		secondImageUrl = preferences.getString(Constant.PREF_PROFILE_IMAGE_TWO,
				"");
		thirdImageUrl = preferences.getString(
				Constant.PREF_PROFILE_IMAGE_THREE, "");
		fourthImageUrl = preferences.getString(
				Constant.PREF_PROFILE_IMAGE_FOUR, "");
		fifthImageUrl = preferences.getString(Constant.PREF_PROFILE_IMAGE_FIVE,
				"");
		sixthImageUrl = preferences.getString(Constant.PREF_PROFILE_IMAGE_SIX,
				"");

	}

	private void showImage() {
		if (!firstImageUrl.equals("")) {
			aQuery.id(firstImage).image(firstImageUrl, options)
					.progress(R.id.progressProfile);
		}
		if (!secondImageUrl.equals("")) {
			findViewById(R.id.progressFirst).setVisibility(View.VISIBLE);
			aQuery.id(secondImage).image(secondImageUrl, options);
		}
		if (!thirdImageUrl.equals("")) {
			findViewById(R.id.progressSecond).setVisibility(View.VISIBLE);
			aQuery.id(thirdImage).image(thirdImageUrl, options);
		}
		if (!fourthImageUrl.equals("")) {
			findViewById(R.id.progressThird).setVisibility(View.VISIBLE);
			aQuery.id(fourthImage).image(fourthImageUrl, options);
		}
		if (!fifthImageUrl.equals("")) {
			findViewById(R.id.progressFour).setVisibility(View.VISIBLE);
			aQuery.id(fifthImage).image(fifthImageUrl, options);
		}
		if (!sixthImageUrl.equals("")) {
			findViewById(R.id.progressFive).setVisibility(View.VISIBLE);
			aQuery.id(sixImage).image(sixthImageUrl, options);
		}
	}

	@Override
	public void onClick(View v) {
		getDataFromPref();
		switch (v.getId()) {
		case R.id.userprofilpicture:
			selectedImage = 1;
			onImageClick(1, firstImageUrl, false);
			break;
		case R.id.firtstImage:
			selectedImage = 2;
			onImageClick(2, secondImageUrl, false);
			break;
		case R.id.secondImage:
			selectedImage = 3;
			onImageClick(3, thirdImageUrl, false);
			break;
		case R.id.thirdimageview:
			selectedImage = 4;
			onImageClick(4, fourthImageUrl, false);
			break;
		case R.id.fourtthimageview:
			selectedImage = 5;
			onImageClick(5, fifthImageUrl, false);
			break;
		case R.id.fifthImageview:
			selectedImage = 6;
			onImageClick(6, sixthImageUrl, false);
			break;
		case R.id.editbuton:
			onImageClick(selectedImage, "", true);
			break;
		case R.id.deletebutton:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Delete Image");
			builder.setMessage("Do you want to delete this image?");
			builder.setCancelable(false);
			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
							deleteLayout.setVisibility(View.GONE);
							deleteImage(selectedImage);
						}
					});
			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
							deleteLayout.setVisibility(View.GONE);
						}
					});

			AlertDialog alert = builder.create();
			alert.show();

			break;
		case R.id.setProfileButton:
			deleteLayout.setVisibility(View.GONE);
			setprofile(selectedImage);
			break;
		case R.id.btnEditProfileSubmit:
			if (!detector.isConnectingToInternet()) {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}
			Utility.showProcess(EditProfileNew.this, "Updating status..");
			new Thread(new Runnable() {

				@Override
				public void run() {
					updateStatus();
				}
			}).start();
			break;
		default:
			break;
		}
	}

	private void updateStatus() {

		final HttpRequest httpRequest = new HttpRequest();
		final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new KeyValuePair(Constant.KEY_FACEBOOK_ID,
				preferences.getString(Constant.FACEBOOK_ID, "")));
		nameValuePairs.add(new KeyValuePair(Constant.KEY_STATUS, edtStatus
				.getText().toString().trim()));
		try {
			String statusResponse = httpRequest.postData(
					Constant.status_update_url, nameValuePairs);
			Log.i(TAG, "status submitted");
			final String string = JsonParser
					.parseStatusResponse(statusResponse);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Utility.closeprocess(EditProfileNew.this);
					if (string != null) {
						Toast.makeText(EditProfileNew.this, string,
								Toast.LENGTH_SHORT).show();
						editor.putString(Constant.PREF_USER_STATUS, edtStatus
								.getText().toString().trim());
						editor.commit();
					} else {
						Toast.makeText(EditProfileNew.this,
								"Status update failed", Toast.LENGTH_SHORT)
								.show();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setprofile(int i) {
		if (!detector.isConnectingToInternet()) {
			Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
			return;
		}

		String imageId = null;
		switch (i) {
		case 2:
			imageId = (String) secondImage.getTag();
			break;
		case 3:
			imageId = (String) thirdImage.getTag();
			break;
		case 4:
			imageId = (String) fourthImage.getTag();
			break;
		case 5:
			imageId = (String) fifthImage.getTag();
			break;
		case 6:
			imageId = (String) sixImage.getTag();
			break;

		default:
			break;
		}

		Utility.showProcess(EditProfileNew.this, "Changing Image..");
		final HttpRequest httpRequest = new HttpRequest();
		final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new KeyValuePair(Constant.KEY_FACEBOOK_ID,
				preferences.getString(Constant.FACEBOOK_ID, "")));
		nameValuePairs.add(new KeyValuePair(Constant.KEY_NEW_INDEX_ID, (i - 1)
				+ ""));
		nameValuePairs
				.add(new KeyValuePair(Constant.KEY_NEW_IMAGE_ID, imageId));
		AppLog.Log(
				TAG,
				"FaceBook Id::"
						+ preferences.getString(Constant.FACEBOOK_ID, "")
						+ " index Id::" + (i - 1) + " image id" + imageId);
		new Thread(new Runnable() {
			@Override
			public void run() {
				String json;
				try {
					json = httpRequest.postData(Constant.change_profile_pic,
							nameValuePairs);
					AppLog.Log(TAG, "Response after changing profile:" + json);
					ArrayList<ProfileImageModel> listProfile = JsonParser
							.parseProfileImageData(json);
					SetListIntoPref(listProfile);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							getDataFromPref();
							showImage();
							Utility.closeprocess(EditProfileNew.this);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Utility.closeprocess(EditProfileNew.this);
						}
					});
				}
			}

		}).start();

	}

	private void deleteImage(int selectedImage) {
		switch (selectedImage) {
		case 2:
			if (detector.isConnectingToInternet()) {
				String Tag = (String) secondImage.getTag();
				delete_picture(Integer.parseInt(Tag));
			} else {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}
			editor.putString(Constant.PREF_PROFILE_IMAGE_TWO, null);
			editor.commit();
			secondImage.setImageResource(R.drawable.add_image);
			findViewById(R.id.progressFirst).setVisibility(View.GONE);
			break;
		case 3:
			if (detector.isConnectingToInternet()) {
				String Tag = (String) thirdImage.getTag();
				delete_picture(Integer.parseInt(Tag));
			} else {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}
			editor.putString(Constant.PREF_PROFILE_IMAGE_THREE, null);
			editor.commit();
			thirdImage.setImageResource(R.drawable.add_image);
			findViewById(R.id.progressSecond).setVisibility(View.GONE);
			break;
		case 4:
			if (detector.isConnectingToInternet()) {
				String Tag = (String) fourthImage.getTag();
				delete_picture(Integer.parseInt(Tag));
			} else {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}

			editor.putString(Constant.PREF_PROFILE_IMAGE_FOUR, null);
			editor.commit();
			fourthImage.setImageResource(R.drawable.add_image);
			findViewById(R.id.progressThird).setVisibility(View.GONE);
			break;
		case 5:
			if (detector.isConnectingToInternet()) {
				String Tag = (String) fifthImage.getTag();
				delete_picture(Integer.parseInt(Tag));
			} else {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;

			}
			editor.putString(Constant.PREF_PROFILE_IMAGE_FIVE, null);
			editor.commit();
			fifthImage.setImageResource(R.drawable.add_image);
			findViewById(R.id.progressFour).setVisibility(View.GONE);
			break;
		case 6:
			if (detector.isConnectingToInternet()) {
				String Tag = (String) sixImage.getTag();
				delete_picture(Integer.parseInt(Tag));
			} else {
				Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
				return;
			}
			editor.putString(Constant.PREF_PROFILE_IMAGE_SIX, null);
			editor.commit();
			sixImage.setImageResource(R.drawable.add_image);
			findViewById(R.id.progressFive).setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

	private void onImageClick(int selection, String imageUrl, boolean edit) {
		selectedImage = selection;
		AppLog.Log(TAG, "ImageUrl" + imageUrl);
		if (edit) {
			deleteLayout.setVisibility(View.GONE);
			ShowDialogForChoose();
		} else {
			if (imageUrl.equals("")) {
				deleteLayout.setVisibility(View.GONE);
				ShowDialogForChoose();
			} else {
				deleteLayout.setVisibility(View.VISIBLE);
				if (selectedImage == 1) {
					btnDelete.setVisibility(View.GONE);
					btnSetProfile.setVisibility(View.GONE);
				} else {
					btnDelete.setVisibility(View.VISIBLE);
					btnSetProfile.setVisibility(View.VISIBLE);
				}

			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bitmap bitmap = null;

		if (CAMERA_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
			bitmap = resizeBitmap(imagePath);
		} else if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == Activity.RESULT_OK && null != data) {
			if (data != null) {
				Uri contentURI = data.getData();
				imagePath = getRealPathFromURI(contentURI);
				bitmap = resizeBitmap(imagePath);
			}
		}
		if (resultCode == RESULT_OK) {
			String base64String = "";
			if (bitmap != null) {
				base64String = Utility.getBase64String(bitmap);
			} else {
				Toast.makeText(this, "Some Error Occured", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			switch (selectedImage) {
			case 1:

				if (detector.isConnectingToInternet()) {
					uploadImage(0, base64String);
				} else {
					Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				aQuery.id(firstImage).image(bitmap);
				editor.putString(Constant.PREF_PROFILE_IMAGE_ONE, imagePath);
				editor.commit();
				break;
			case 2:

				if (detector.isConnectingToInternet()) {
					uploadImage(1, base64String);
				} else {
					Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				aQuery.id(secondImage).image(bitmap);
				editor.putString(Constant.PREF_PROFILE_IMAGE_TWO, imagePath);
				editor.commit();

				break;
			case 3:
				if (detector.isConnectingToInternet()) {
					uploadImage(2, base64String);
				} else {
					Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				aQuery.id(thirdImage).image(bitmap);
				editor.putString(Constant.PREF_PROFILE_IMAGE_THREE, imagePath);
				editor.commit();
				break;
			case 4:
				if (detector.isConnectingToInternet()) {
					uploadImage(3, base64String);
				} else {
					Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				aQuery.id(fourthImage).image(bitmap);
				editor.putString(Constant.PREF_PROFILE_IMAGE_FOUR, imagePath);
				editor.commit();
				break;
			case 5:
				if (detector.isConnectingToInternet()) {
					uploadImage(4, base64String);
				} else {
					Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT)
							.show();
					return;

				}
				aQuery.id(fifthImage).image(bitmap);
				editor.putString(Constant.PREF_PROFILE_IMAGE_FIVE, imagePath);
				editor.commit();
				break;
			case 6:
				if (detector.isConnectingToInternet()) {
					uploadImage(5, base64String);
				} else {
					Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT)
							.show();
					return;

				}
				aQuery.id(sixImage).image(bitmap);
				editor.putString(Constant.PREF_PROFILE_IMAGE_SIX, imagePath);
				editor.commit();
				break;

			default:
				break;
			}
		}
	}

	private Bitmap resizeBitmap(String path) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		int outWidth, outHeight;
		if (path != null) {
			BitmapFactory.decodeFile(path, options);
			outWidth = options.outWidth;
			outHeight = options.outHeight;
		} else {
			if (photoBitmap != null) {
				outWidth = photoBitmap.getWidth();
				outHeight = photoBitmap.getHeight();
			} else {
				return null;
			}
		}

		int ratio = (int) ((((float) outWidth) / mobile_width) + 0.5f);

		if (ratio == 0) {
			ratio = 1;
		}

		if (path != null) {
			options.inJustDecodeBounds = false;
			options.inSampleSize = ratio;
			// Applog.Log(TAG, "from gallery  " + ratio);
			photoBitmap = BitmapFactory.decodeFile(path, options);
			// ivItemPhoto.setImageBitmap(photoBitmap);
			return photoBitmap;
		} else {
			outWidth = outWidth / ratio;
			outHeight = outHeight / ratio;
			photoBitmap = Bitmap.createScaledBitmap(photoBitmap, outWidth,
					outHeight, true);
			return photoBitmap;
		}
	}

	private String getRealPathFromURI(Uri contentURI) {
		String result;
		Cursor cursor = getContentResolver().query(contentURI, null, null,
				null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file
								// path
			result = contentURI.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
	}

	private void delete_picture(int image_id) {
		Utility.showProcess(EditProfileNew.this, "Deleting Image..");
		final HttpRequest httpRequest = new HttpRequest();
		final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new KeyValuePair(Constant.KEY_FACEBOOK_ID,
				preferences.getString(Constant.FACEBOOK_ID, "")));
		nameValuePairs.add(new KeyValuePair(Constant.KEY_IMAGE_ID, image_id
				+ ""));
		new Thread(new Runnable() {
			@Override
			public void run() {
				String json;
				try {
					json = httpRequest.postData(Constant.delete_user_pic,
							nameValuePairs);
					JSONObject object = new JSONObject(json);
					errorFlag = object.getInt(Constant.ERR_FLAG);
					AppLog.Log(TAG, "Delete json:" + json);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Utility.closeprocess(EditProfileNew.this);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();

	}

	private void uploadImage(final int id, String base64) {
		Utility.showProcess(EditProfileNew.this, "Uploding Image..");
		AppLog.Log(TAG, "Index ID::" + id + " AND base64" + base64);
		// final int errorFlag = 1;
		final HttpRequest httpRequest = new HttpRequest();
		final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new KeyValuePair(Constant.KEY_FACEBOOK_ID,
				preferences.getString(Constant.FACEBOOK_ID, "")));
		nameValuePairs.add(new KeyValuePair("ent_index_id", id + ""));
		nameValuePairs.add(new KeyValuePair("ent_userimage", base64));
		new Thread(new Runnable() {
			@Override
			public void run() {
				String json;
				try {
					json = httpRequest.postData(Constant.upload_user_pic,
							nameValuePairs);
					AppLog.Log(TAG, "Upload image response:" + json);
					JSONObject object = new JSONObject(json);

					errorFlag = object.getInt(Constant.ERR_FLAG);
					if (errorFlag == 0) {

						setImageTagAndUrlToPref(id,
								object.getInt("ent_image_id"),
								object.getString("picURL"));
					} else {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(EditProfileNew.this,
										"Some Error Occured Please try again.",
										Toast.LENGTH_SHORT).show();
							}
						});
					}
					AppLog.Log(TAG, "Answer json:" + json);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Utility.closeprocess(EditProfileNew.this);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();
	}

	private void getProfileImages() {
		Utility.showProcess(EditProfileNew.this, "Getting Images..");
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
					ArrayList<ProfileImageModel> imageList = JsonParser
							.parseProfileImageData(json);
					AppLog.Log(TAG, "Return Image Size:" + imageList.size());
					SetListIntoPref(imageList);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							getDataFromPref();
							showImage();
							Utility.closeprocess(EditProfileNew.this);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	protected void SetListIntoPref(ArrayList<ProfileImageModel> imageList) {
		if (imageList.size() > 0) {
			for (int i = 0; i < imageList.size(); i++) {
				final ProfileImageModel imageModel = imageList.get(i);
				int imageIndex = imageModel.getIndexId();
				switch (imageIndex) {
				case 0:
					setImageTagAndUrlToPref(0, imageModel.getImageId(),
							imageModel.getImageUrl());
					break;
				case 1:
					setImageTagAndUrlToPref(1, imageModel.getImageId(),
							imageModel.getImageUrl());
					break;
				case 2:
					setImageTagAndUrlToPref(2, imageModel.getImageId(),
							imageModel.getImageUrl());
					break;
				case 3:
					setImageTagAndUrlToPref(3, imageModel.getImageId(),
							imageModel.getImageUrl());
					break;
				case 4:
					setImageTagAndUrlToPref(4, imageModel.getImageId(),
							imageModel.getImageUrl());
					break;
				case 5:
					setImageTagAndUrlToPref(5, imageModel.getImageId(),
							imageModel.getImageUrl());
					break;
				default:
					break;
				}
			}

		}

	}

	protected void setImageTagAndUrlToPref(final int id, final int i,
			final String url) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				switch (id) {
				case 0:
					editor.putString(Constant.PREF_PROFILE_IMAGE_ONE, url);
					editor.commit();
					firstImage.setTag(i + "");
					break;
				case 1:
					editor.putString(Constant.PREF_PROFILE_IMAGE_TWO, url);
					editor.commit();
					secondImage.setTag(i + "");
					break;
				case 2:
					editor.putString(Constant.PREF_PROFILE_IMAGE_THREE, url);
					editor.commit();
					thirdImage.setTag(i + "");
					break;
				case 3:
					editor.putString(Constant.PREF_PROFILE_IMAGE_FOUR, url);
					editor.commit();
					fourthImage.setTag(i + "");
					break;
				case 4:
					editor.putString(Constant.PREF_PROFILE_IMAGE_FIVE, url);
					editor.commit();
					fifthImage.setTag(i + "");
					break;
				case 5:
					editor.putString(Constant.PREF_PROFILE_IMAGE_SIX, url);
					editor.commit();
					sixImage.setTag(i + "");
					break;

				default:
					break;
				}
			}
		});

	}

	private Uri getTempUri() {
		// Create an image file name
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String dt = sdf.format(new Date());
		imageFile = null;
		imageFile = new File(Environment.getExternalStorageDirectory()
				+ "/Flamer/", "Camera_" + dt + ".jpg");
		AppLog.Log(
				TAG,
				"New Camera Image Path:- "
						+ Environment.getExternalStorageDirectory()
						+ "/Flamer/" + "Camera_" + dt + ".jpg");
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/Flamer");
		if (!file.exists()) {
			file.mkdir();
		}
		if (!imageFile.exists()) {
			try {
				imageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		imagePath = Environment.getExternalStorageDirectory() + "/Flamer/"
				+ "Camera_" + dt + ".jpg";
		imageUri = Uri.fromFile(imageFile);
		return imageUri;
	}

	private void ShowDialogForChoose() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setAdapter(new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, new String[] {
								"Camera", "Gallery" }),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == 0) {
									Intent cameraIntent = new Intent(
											android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

									cameraIntent.putExtra(
											MediaStore.EXTRA_OUTPUT,
											getTempUri());
									startActivityForResult(cameraIntent,
											CAMERA_REQUEST_CODE);
								} else if (which == 1) {
									Intent i = new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
									startActivityForResult(i, RESULT_LOAD_IMAGE);
								}
							}
						});
		builder.create().show();
	}
}
