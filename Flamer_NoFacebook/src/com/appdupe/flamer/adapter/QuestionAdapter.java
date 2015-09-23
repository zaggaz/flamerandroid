package com.appdupe.flamer.adapter;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appdupe.flamer.QuestionsActivity;
import com.appdupe.flamer.model.AnswerModel;
import com.appdupe.flamer.model.QuestionModel;
import com.appdupe.flamer.utility.AppLog;
import com.appdupe.flamer.utility.ConnectionDetector;
import com.appdupe.flamernofb.R;

public class QuestionAdapter extends PagerAdapter {
	private static final String TAG = "QuestionAdapter";
	private QuestionsActivity activity;
	private ArrayList<QuestionModel> list;
	public ArrayList<AnswerModel> selectedList;
	public int selectedRadioPosition = -1;
	private ConnectionDetector cd;
	private int questionPosition;

	public QuestionAdapter(QuestionsActivity activity,
			ArrayList<QuestionModel> list, ArrayList<AnswerModel> answerList) {
		this.activity = activity;
		this.list = list;
		this.selectedList = answerList;
		cd = new ConnectionDetector(activity);

	}

	@Override
	public int getCount() {
		return list.size();
	}

	private View currentView;

	@Override
	public Object instantiateItem(View collection, final int position) {
		selectedRadioPosition = -1;
		final Holder holder = new Holder();
		View v = collection;

		// AnswerModel model = null;
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.question_item, null);
		holder.txtQuestion = (TextView) v
				.findViewById(R.id.txtQuestionItemQuestion);
		holder.radioGroup = (RadioGroup) v
				.findViewById(R.id.radioGroupQuestionItem);
		holder.submit = (Button) v.findViewById(R.id.btnQustionItemSubmit);
		holder.ivQuestionIndicatorLeft = (ImageView) v
				.findViewById(R.id.ivQuestionIndicatorLeft);
		holder.ivQuestionIndicatorRight = (ImageView) v
				.findViewById(R.id.ivQuestionIndicatorRight);
		final QuestionModel model = list.get(position);
		holder.txtQuestion.setText(model.getQuestion());
		final ArrayList<AnswerModel> answerList = model.getAnswer();
		int answerListSize = answerList.size();
		AppLog.Log(TAG, "ANSWER SIZE:" + answerListSize);
		RadioButton answerButton[] = new RadioButton[answerListSize];

		for (int i = 0; i < answerListSize; i++) {
			final AnswerModel answerModel = answerList.get(i);
			// answerButton[i] = new RadioButton(activity);
			answerButton[i] = (RadioButton) activity.getLayoutInflater()
					.inflate(R.layout.radiobutton, null);
			answerButton[i].setText(answerModel.getAnswer());
			answerButton[i].setId(answerModel.getAnswerId());
			if (answerModel.getFlag() == 1) {
				answerButton[i].setChecked(true);
			}
			final int pos = i;
			answerButton[i].setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					selectedRadioPosition = pos;
					AppLog.Log(TAG, "selcted postion:" + selectedRadioPosition);
				}
			});
			holder.radioGroup.addView(answerButton[i]);
		}

		holder.ivQuestionIndicatorLeft
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						activity.questionIndicatorLeft();

					}
				});
		// Change By Dilavar
		holder.ivQuestionIndicatorRight
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						activity.questionIndicatorRight();

					}
				});

		holder.submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				int selectedAnswerId;
				selectedAnswerId = holder.radioGroup.getCheckedRadioButtonId();
				if (selectedAnswerId == -1) {
					Toast.makeText(activity,
							"You must select atleast one answer",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (selectedAnswerId != 0) {
					model.setSelectedAnswerId(selectedAnswerId);
					if (selectedRadioPosition != -1) {
						answerList.get(selectedRadioPosition).setFlag(1);
					}
					if (list.size() - 1 > position) {
						activity.gotoNext();
					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								activity);
						builder.setTitle("Questions");
						builder.setMessage("Thank you for your answers. Are you sure want to proceed?");
						builder.setCancelable(false);
						builder.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();

										if (!cd.isConnectingToInternet()) {
											Toast.makeText(activity,
													"No Internet",
													Toast.LENGTH_SHORT).show();
											dialog.cancel();
										} else {
											activity.onBackPressed();
										}
										// deleteLayout.setVisibility(View.GONE);
										// deleteImage(selectedImage);
									}
								});
						builder.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										// deleteLayout.setVisibility(View.GONE);
									}
								});

						AlertDialog alert = builder.create();
						alert.show();
					}
				}

			}
		});
		((ViewPager) collection).addView(v);
		collection.setTag(holder);
		currentView = v;
		return v;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	public void manageIndicator(boolean right, boolean left) {
		View rightView, leftView;
		rightView = currentView.findViewById(R.id.ivQuestionIndicatorRight);
		leftView = currentView.findViewById(R.id.ivQuestionIndicatorLeft);
		rightView.setVisibility(right ? View.VISIBLE : View.INVISIBLE);
		leftView.setVisibility(left ? View.VISIBLE : View.INVISIBLE);
	}

	class Holder {
		TextView txtQuestion;
		Button submit;
		ImageView ivQuestionIndicatorRight, ivQuestionIndicatorLeft;
		RadioGroup radioGroup;
	}

}
