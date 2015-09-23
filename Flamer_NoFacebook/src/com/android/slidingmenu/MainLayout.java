package com.android.slidingmenu;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class MainLayout extends LinearLayout {
	private static boolean mDebugLog = true;
	private static String mDebugTag = "Layout1";

	void logDebug(String msg) {

		if (mDebugLog) {
			Log.d(mDebugTag, msg);
		}
	}

	private static final int SLIDING_DURATION = 500;
	private static final int QUERY_INTERVAL = 16;
	int mainLayoutWidth;
	private View menu;
	private View menuRigth;
	private View content;
	private boolean ismoveRtoL;
	private boolean ismoveLToR;
	private static int menuRightMargin = 100;

	private enum MenuState {
		HIDING, HIDDEN, SHOWING, SHOWN,
	};

	private enum MenuStateRight {
		HIDING, HIDDEN, SHOWING, SHOWN,
	};

	private int contentXOffset;
	private MenuState currentMenuState = MenuState.HIDDEN;
	private MenuStateRight currentMenuStateleft = MenuStateRight.HIDDEN;
	private Scroller menuScroller = new Scroller(this.getContext(),
			new EaseInInterpolator());
	private Runnable menuRunnable = new MenuRunnable();
	private Runnable menuRunnableRight = new MenuRunnableRight();
	private Handler menuHandler = new Handler();
	int prevX = 0;
	boolean isDragging = false;
	int lastDiffX = 0;

	public MainLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MainLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		logDebug("onMeasure  ");
		mainLayoutWidth = MeasureSpec.getSize(widthMeasureSpec);
		logDebug("onMeasure  mainLayoutWidth  " + mainLayoutWidth);
		menuRightMargin = mainLayoutWidth * 10 / 100;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		menu = this.getChildAt(0);
		menuRigth = this.getChildAt(1);
		content = this.getChildAt(2);
		content.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				logDebug("setOnTouchListener onTouch ");
				return MainLayout.this.onContentTouch(v, event);
			}
		});
		menu.setVisibility(View.GONE);
		menuRigth.setVisibility(View.GONE);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (changed) {
			LayoutParams contentLayoutParams = (LayoutParams) content
					.getLayoutParams();
			contentLayoutParams.height = this.getHeight();
			contentLayoutParams.width = this.getWidth();
			LayoutParams menuLayoutParams = (LayoutParams) menu
					.getLayoutParams();
			menuLayoutParams.height = this.getHeight();
			menuLayoutParams.width = this.getWidth() - menuRightMargin;

			LayoutParams LeftmenuLayoutParams = (LayoutParams) menuRigth
					.getLayoutParams();
			LeftmenuLayoutParams.height = this.getHeight();
			LeftmenuLayoutParams.width = this.getWidth() - menuRightMargin;

		}
		menu.layout(left, top, right - menuRightMargin, bottom);
		menuRigth.layout(left - menuRightMargin, top, right, bottom);
		content.layout(left + contentXOffset, top, right + contentXOffset,
				bottom);

	}

	public void toggleMenu() {

		if (currentMenuState == MenuState.HIDING
				|| currentMenuState == MenuState.SHOWING)
			return;

		switch (currentMenuState) {
		case HIDDEN:
			currentMenuState = MenuState.SHOWING;
			menu.setVisibility(View.VISIBLE);
			menuScroller.startScroll(0, 0, menu.getLayoutParams().width - 50,
					0, SLIDING_DURATION);
			break;
		case SHOWN:
			currentMenuState = MenuState.HIDING;
			menuScroller.startScroll(contentXOffset, 0, -contentXOffset, 0,
					SLIDING_DURATION);
			break;
		default:
			break;
		}
		menuHandler.postDelayed(menuRunnable, QUERY_INTERVAL);
		this.invalidate();
	}

	public void toggleMenuRight() {

		if (currentMenuStateleft == MenuStateRight.HIDING
				|| currentMenuStateleft == MenuStateRight.SHOWING)
			return;

		switch (currentMenuStateleft) {
		case HIDDEN:
			currentMenuStateleft = MenuStateRight.SHOWING;
			menuRigth.setVisibility(View.VISIBLE);
			menuScroller.startScroll(0, 0,
					-menuRigth.getLayoutParams().width + 50, 0,
					SLIDING_DURATION);
			break;
		case SHOWN:
			currentMenuStateleft = MenuStateRight.HIDING;
			menuScroller.startScroll(contentXOffset, 0, -contentXOffset, 0,
					SLIDING_DURATION);
			break;
		default:
			break;
		}
		menuHandler.postDelayed(menuRunnableRight, QUERY_INTERVAL);
		this.invalidate();
	}

	protected class MenuRunnable implements Runnable {
		@Override
		public void run() {
			boolean isScrolling = menuScroller.computeScrollOffset();
			adjustContentPosition(isScrolling);
		}
	}

	protected class MenuRunnableRight implements Runnable {
		@Override
		public void run() {
			boolean isScrolling = menuScroller.computeScrollOffset();
			adjustContentPositionRight(isScrolling);
		}
	}

	private void adjustContentPosition(boolean isScrolling) {
		int scrollerXOffset = menuScroller.getCurrX();

		content.offsetLeftAndRight(scrollerXOffset - contentXOffset);

		contentXOffset = scrollerXOffset;
		this.invalidate();
		if (isScrolling)
			menuHandler.postDelayed(menuRunnable, QUERY_INTERVAL);
		else
			this.onMenuSlidingComplete();
	}

	private void adjustContentPositionRight(boolean isScrolling) {
		int scrollerXOffset = menuScroller.getCurrX();

		content.offsetLeftAndRight(scrollerXOffset - contentXOffset);

		contentXOffset = scrollerXOffset;
		this.invalidate();
		if (isScrolling)
			menuHandler.postDelayed(menuRunnableRight, QUERY_INTERVAL);
		else
			this.onMenuSlidingRightComplete();
	}

	private void onMenuSlidingComplete() {
		switch (currentMenuState) {
		case SHOWING:
			currentMenuState = MenuState.SHOWN;
			break;
		case HIDING:
			currentMenuState = MenuState.HIDDEN;
			menu.setVisibility(View.GONE);
			break;
		default:
			return;
		}
	}

	private void onMenuSlidingRightComplete() {
		switch (currentMenuStateleft) {
		case SHOWING:
			currentMenuStateleft = MenuStateRight.SHOWN;
			break;
		case HIDING:
			currentMenuStateleft = MenuStateRight.HIDDEN;
			menu.setVisibility(View.GONE);
			break;
		default:
			return;
		}
	}

	protected class EaseInInterpolator implements Interpolator {
		@Override
		public float getInterpolation(float t) {
			return (float) Math.pow(t - 1, 5) + 1;
		}

	}

	public boolean isMenuShown() {
		return currentMenuState == MenuState.SHOWN;
	}

	public boolean onContentTouch(View v, MotionEvent event) {
		logDebug("onContentTouch");
		if (currentMenuState == MenuState.HIDING
				|| currentMenuState == MenuState.SHOWING)
			return false;
		else if (currentMenuStateleft == MenuStateRight.HIDING
				|| currentMenuStateleft == MenuStateRight.SHOWING)
			return false;
		int curX = (int) event.getRawX();
		int diffX = 0;
		logDebug("onContentTouch  curX  " + curX);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			prevX = curX;
			logDebug("onContentTouch  ACTION_DOWN curX  " + curX);
			logDebug("onContentTouch  ACTION_DOWN prevX  " + prevX);
			return true;

		case MotionEvent.ACTION_MOVE:
			logDebug("onContentTouch  ACTION_MOVE curX  " + curX);
			logDebug("onContentTouch  ACTION_MOVE isDragging  " + isDragging);
			if (!isDragging) {
				isDragging = true;
				menu.setVisibility(View.VISIBLE);
			}

			diffX = curX - prevX;
			logDebug("onContentTouch  ACTION_MOVE diffX  " + diffX);
			logDebug("onContentTouch  ACTION_MOVE curX  " + curX);
			logDebug("onContentTouch  ACTION_MOVE prevX  " + prevX);
			logDebug("onContentTouch  ACTION_MOVE contentXOffset  "
					+ contentXOffset);

			if (contentXOffset + diffX <= 0) {
				diffX = -contentXOffset;
			}

			else if (contentXOffset + diffX > mainLayoutWidth - menuRightMargin) {
				logDebug("onContentTouch  ACTION_MOVE mainLayoutWidth  "
						+ mainLayoutWidth);
				logDebug("onContentTouch  ACTION_MOVE menuRightMargin  "
						+ menuRightMargin);
				logDebug("onContentTouch  ACTION_MOVE contentXOffset  "
						+ contentXOffset);
				diffX = mainLayoutWidth - menuRightMargin - contentXOffset;
			}

			logDebug("onContentTouch  ACTION_MOVE diffX  " + diffX);
			content.offsetLeftAndRight(diffX);
			contentXOffset += diffX;
			this.invalidate();

			prevX = curX;
			lastDiffX = diffX;
			logDebug("onContentTouch  ACTION_MOVE lastDiffX  " + lastDiffX);
			logDebug("onContentTouch  ACTION_MOVE prevX  " + prevX);
			return true;

		case MotionEvent.ACTION_UP:
			Log.d("MainLayout.java onContentTouch()", "Up lastDiffX "
					+ lastDiffX);
			logDebug("ACTION_UP  prevX " + prevX);
			logDebug("ACTION_UP  drop x is " + event.getRawX());
			logDebug("ACTION_UP  lastDiffX " + lastDiffX);

			if (lastDiffX > 0) {
				currentMenuState = MenuState.SHOWING;
				menuScroller.startScroll(contentXOffset, 0,
						menu.getLayoutParams().width - contentXOffset, 0,
						SLIDING_DURATION);
			} else if (lastDiffX < 0) {
				currentMenuState = MenuState.HIDING;
				menuScroller.startScroll(contentXOffset, 0, -contentXOffset, 0,
						SLIDING_DURATION);
			}

			menuHandler.postDelayed(menuRunnable, QUERY_INTERVAL);
			this.invalidate();
			isDragging = false;
			prevX = 0;
			lastDiffX = 0;
			return true;

		default:
			break;
		}

		return false;
	}
}