/**
 * ============================================================================
 *
 * Copyright (C) 2011 Android Museum Systems.  All rights reserved. The content 
 * presented herein may not, under any circumstances, be reproduced, in 
 * whole or in any part or form, without written permission from 
 * Museum Systems.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are NOT permitted. Neither the name of Museum Systems,
 * nor the names of contributors may be used to endorse or promote products 
 * derived from this software without specific prior written permission.
 *
 * ============================================================================
 *
 * Author: Admin
 *  
 *
 * Revision History
 * ----------------------------------------------------------------------------
 * Date                Author              Comment, bug number, fix description
 *
 * Feb 26, 2012      tuan@edge-works.net           version 1.0
 *
 * ----------------------------------------------------------------------------
 */

package com.appdupe.flamer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Gallery;

/**
 * Feb 26, 2012.
 * 
 * @author Admin
 * @version 1.0
 * @copyright Copyright (c) Android Museum Systems, all rights reserved
 */

public class ExtendedGallery extends Gallery {

	/** The stuck. */
	private boolean stuck = false;
	// -----------------------------------------------------
	private MotionEvent downEvent;
	private int touchSlop;
	private float lastMotionY;
	private float lastMotionX;

	// -----------------------------------------------------

	/**
	 * Instantiates a new extended gallery.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public ExtendedGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		System.out.println("Inside ExtendedGallery");

		initTouchSlop();
	}

	private void initTouchSlop() {
		// TODO Auto-generated method stub
		final ViewConfiguration configuration = ViewConfiguration
				.get(getContext());
		touchSlop = configuration.getScaledTouchSlop();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final float x = ev.getX();
		final float y = ev.getY();

		switch (ev.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_MOVE: {
			final int xDiff = (int) Math.abs(x - lastMotionX);
			final int yDiff = (int) Math.abs(y - lastMotionY);

			// have we moved enough to consider this a scroll
			if (xDiff > touchSlop || yDiff > touchSlop) {
				// this is the event we want, but we need to resend the Down
				// event as this could have been consumed by a child
				// Log.d(TAG,
				// "Move event detected: Start intercepting touch events");
				if (downEvent != null)
					this.onTouchEvent(downEvent);
				downEvent = null;
				return true;
			}
			return false;
		}
		case MotionEvent.ACTION_DOWN: {
			// need to save the on down event incase this is going to be a
			// scroll
			downEvent = MotionEvent.obtain(ev);
			lastMotionX = x;
			lastMotionY = y;
			return false;
		}
		default: {
			// if this is not a down or scroll event then it is not for us
			downEvent = null;
			return false;
		}
		}
	}

	/**
	 * Instantiates a new extended gallery.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public ExtendedGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Instantiates a new extended gallery.
	 * 
	 * @param context
	 *            the context
	 */
	public ExtendedGallery(Context context) {
		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Gallery#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return stuck || super.onTouchEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Gallery#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			return stuck || super.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Sets the scrolling enabled.
	 * 
	 * @param enabled
	 *            the new scrolling enabled
	 */
	public void setScrollingEnabled(boolean enabled) {
		stuck = !enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Gallery#onFling(android.view.MotionEvent,
	 * android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return super.onFling(e1, e2, 0, velocityY);
	}
}
