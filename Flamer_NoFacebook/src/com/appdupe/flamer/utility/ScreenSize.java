package com.appdupe.flamer.utility;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenSize {
	
	Context context;
	
	DisplayMetrics metrics;
	WindowManager wm;
	
	float scale, resize, adjust;
	private double defaultScreenSize = 7.507;
	
	public ScreenSize(Context context) {
		this.context = context;
		metrics = new DisplayMetrics();
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);			
	}
	
	public float getScreenDensity() {
		scale = context.getResources().getDisplayMetrics().density;
		return scale;
	}
	
	public double getScreenWidth() {
		wm.getDefaultDisplay().getMetrics(metrics);	
		double width = Math.pow(metrics.widthPixels/metrics.xdpi,2);
		return width;
	}
	
	public double getScreenHeight() {
		wm.getDefaultDisplay().getMetrics(metrics);	
		double height = Math.pow(metrics.heightPixels/metrics.ydpi,2);
		return height;
	}
	
	public double getScreenWidthPixel() {
		wm.getDefaultDisplay().getMetrics(metrics);	
		double width = metrics.widthPixels;
		return width;
	}
	
	public double getScreenHeightPixel() {
		wm.getDefaultDisplay().getMetrics(metrics);	
		double height = metrics.heightPixels;
		return height;
	}
	
	public double getScreenInches() {
		double x = getScreenWidth();
        double y = getScreenHeight();
        double screenInches = Math.sqrt(x+y);
        return screenInches;
	}
	
	public double getScaleValue()
	{	
		double screenInches = getScreenInches();         
        double adjustValue = (float) (screenInches / defaultScreenSize);
		return adjustValue;
	}
	
	public void setDefaultScreenSize(double defaultScreenSize) {
		this.defaultScreenSize = defaultScreenSize;
	}
	
	public int calculateSize(int value) {
		double scaleSize = getScaleValue() * getScreenDensity();
        return (int)(value * scaleSize);
	}
	
	public int getWidthRatio(int value) {
		return (int)(getScreenWidthPixel() * value)/100;
	}	
	
	public int getHeightRatio(int value) {
		return (int)(getScreenHeightPixel() * value)/100;
	}
	
	public int getTextScaleValue(int value)
	{
		double screenInches = getScreenInches();         
        double adjustValue = (float) (screenInches / defaultScreenSize);
		return (int) (value * adjustValue);
	}
}
