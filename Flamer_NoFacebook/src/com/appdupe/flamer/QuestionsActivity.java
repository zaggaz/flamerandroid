package com.appdupe.flamer;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.appdupe.flamer.adapter.QuestionAdapter;
import com.appdupe.flamer.model.AnswerModel;
import com.appdupe.flamer.model.KeyValuePair;
import com.appdupe.flamer.model.QuestionModel;
import com.appdupe.flamer.utility.AppLog;
import com.appdupe.flamer.utility.ConnectionDetector;
import com.appdupe.flamer.utility.Constant;
import com.appdupe.flamer.utility.HttpRequest;
import com.appdupe.flamer.utility.JsonParser;
import com.appdupe.flamer.utility.Ultilities;
import com.appdupe.flamer.utility.Utility;
import com.appdupe.flamernofb.R;

public class QuestionsActivity extends Activity implements OnClickListener,
		OnPageChangeListener {
	private static final String TAG = "QuestionsActivity";
	private ViewPager pager;
	private Button btnClose;
	private ArrayList<QuestionModel> list;
	private ArrayList<AnswerModel> answerList;
	private QuestionAdapter adapter;
	private String deviceId = "", facebookId = "";
	private SharedPreferences preferences;
	// private ImageView ivIndicatorLeft, ivIndicatorRight;
	private ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions);
		initData();
		cd = new ConnectionDetector(this);
		if (!cd.isConnectingToInternet()) {
			Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
			return;
		} else {
			getQuestionData();
		}

	}

	private void initData() {
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		pager = (ViewPager) findViewById(R.id.pagerQuestions);
		btnClose = (Button) findViewById(R.id.btnQuestionClose);
		// ivIndicatorLeft = (ImageView)
		// findViewById(R.id.ivQuestionIndicatorLeft);
		// ivIndicatorRight = (ImageView)
		// findViewById(R.id.ivQuestionIndicatorRight);
		// ivIndicatorLeft.setOnClickListener(this);
		// ivIndicatorRight.setOnClickListener(this);
		list = new ArrayList<QuestionModel>();
		answerList = new ArrayList<AnswerModel>();
		// adapter = new QuestionAdapter(this, list);
		// pager.setAdapter(adapter);
		btnClose.setOnClickListener(this);
		deviceId = Ultilities.getDeviceId(this);
		facebookId = preferences.getString(Constant.FACEBOOK_ID, "");

	}

	private void getQuestionData() {
		Utility.showProcess(this, "Getting Question...");
		final HttpRequest httpRequest = new HttpRequest();
		final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new KeyValuePair(Constant.KEY_FACEBOOK_ID,
				facebookId));
		new Thread(new Runnable() {

			@Override
			public void run() {
				String json;
				try {
					json = httpRequest.postData(Constant.getQuestion_url,
							nameValuePairs);
					list = JsonParser.parseQuestionData(json);
					AppLog.Log(TAG, "QuestionSize ::-->" + list.size());
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Utility.closeprocess(QuestionsActivity.this);
							adapter = new QuestionAdapter(
									QuestionsActivity.this, list, answerList);
							pager.setAdapter(adapter);
							pager.setOnPageChangeListener(QuestionsActivity.this);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnQuestionClose:
			onBackPressed();
			break;
		// Change By Dilavar
		// case R.id.ivQuestionIndicatorLeft:
		// pager.setCurrentItem(pager.getCurrentItem() - 1);
		// break;
		// case R.id.ivQuestionIndicatorRight:
		// pager.setCurrentItem(pager.getCurrentItem() + 1);
		// break;
		default:
			break;
		}
	}

	// Change By Dilavar
	public void questionIndicatorLeft() {
		pager.setCurrentItem(pager.getCurrentItem() - 1);
	}

	// Change By Dilavar
	public void questionIndicatorRight() {
		pager.setCurrentItem(pager.getCurrentItem() + 1);
	}

	@Override
	public void onBackPressed() {
		submitQuestion();

	}

	public void submitQuestion() {
		JSONArray jsonArray = getSelectedAnswerArray();
		AppLog.Log(TAG, "CHANGE IN ANSWER:" + jsonArray.length());
		if (jsonArray.length() > 0) {
			Utility.showProcess(QuestionsActivity.this, "Submitting..");
			final HttpRequest httpRequest = new HttpRequest();
			final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new KeyValuePair(Constant.KEY_FACEBOOK_ID,
					facebookId));
			AppLog.Log(TAG, jsonArray.toString());
			nameValuePairs.add(new KeyValuePair(Constant.JSON, jsonArray
					.toString()));
			new Thread(new Runnable() {
				@Override
				public void run() {
					String json;
					try {
						json = httpRequest.postData(Constant.updateAnswer_url,
								nameValuePairs);
						AppLog.Log(TAG, "Answer json:" + json);
					} catch (Exception e) {
						e.printStackTrace();

					}

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Utility.closeprocess(QuestionsActivity.this);
							finish();
						}
					});

				}
			}).start();
		} else {
			finish();
		}

	}

	private JSONArray getSelectedAnswerArray() {
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			QuestionModel model = list.get(i);
			if (model.getSelectedAnswerId() != -1) {
				JSONObject object = new JSONObject();
				try {
					object.put(Constant.ANSWER_ID, model.getSelectedAnswerId());
					object.put(Constant.QUESTION_ID, model.getQuestionId());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				AppLog.Log(TAG, "OBJECT" + object.toString());
				array.put(object);
			}

		}
		return array;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if (arg0 == list.size() - 1) {
			adapter.manageIndicator(false, true);
			// ivIndicatorRight.setVisibility(View.GONE);
		} else if (arg0 == 0) {
			// ivIndicatorLeft.setVisibility(View.GONE);
			adapter.manageIndicator(true, false);
		} else {
			adapter.manageIndicator(true, true);
			// ivIndicatorLeft.setVisibility(View.VISIBLE);
			// ivIndicatorRight.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onPageSelected(int arg0) {

	}

	public void gotoNext() {
		if (list.size() > pager.getCurrentItem()) {
			pager.setCurrentItem(pager.getCurrentItem() + 1);
		}
	}

}
