package com.appdupe.flamer.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.appdupe.flamer.utility.GellaryData;
import com.appdupe.flamernofb.R;

public class HomeAdapter extends ArrayAdapter<GellaryData> {

	Activity activity;

	private ArrayList<GellaryData> data = new ArrayList<GellaryData>();

	/* FeaturedActivity fac; */
	public Drawable image_in_detailActivity;

	public static final int DIALOG_PROGRESS = 1;

	public HomeAdapter(Activity context, ArrayList<GellaryData> list) {
		super(context, R.layout.gellaryitem, list);
		this.data = list;
		this.activity = context;
	}

	@Override
	public GellaryData getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public int getCount() {
		return super.getCount();
	}

	// public HomeAdapter(Activity c, ArrayList<GellaryData> itemListAds)
	// {
	// /*private final static String TAG="HomeAdapter";*/
	// // this.context = context;
	// /*this.user = username;
	// this.pass = password;*/
	// this.activity = c;
	// this.mContext = c;
	// this.sContext = c;
	// // TypedArray attr = mContext;
	// this.data = itemListAds;
	// /*TypedArray attr = mContext
	// .obtainStyledAttributes(R.styleable.HelloGallery);
	// mGalleryItemBackground = attr.getResourceId(
	// R.styleable.HelloGallery_android_galleryItemBackground, 0);
	// TypedArray attrs = sContext
	// .obtainStyledAttributes(R.styleable.HelloGallery_smallImage);
	// small_imageBackground = attrs.getResourceId(
	// R.styleable.HelloGallery_android_galleryItemBackground, 1);
	// attr.recycle();*/
	// }

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		System.out.println("HomeAdapter getview");
		boolean flag = true;

		if (convertView == null) {
			LayoutInflater vi = activity.getLayoutInflater();
			convertView = vi.inflate(R.layout.gellaryitem, null);
		}
		ImageView galleimageview = (ImageView) convertView
				.findViewById(R.id.gelleryimageview);
		galleimageview.setImageResource(getItem(position).getResourceId());

		return convertView;

	}
}
