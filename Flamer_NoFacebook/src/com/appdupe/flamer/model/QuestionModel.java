package com.appdupe.flamer.model;

import java.util.ArrayList;


public class QuestionModel {

	private String Question;
	private int QuestionId;
	private int selectedAnswerId = -1;

	public int getQuestionId() {
		return QuestionId;
	}

	public int getSelectedAnswerId() {
		return selectedAnswerId;
	}

	public void setSelectedAnswerId(int selectedAnswerId) {
		this.selectedAnswerId = selectedAnswerId;
	}

	public void setQuestionId(int questionId) {
		QuestionId = questionId;
	}

	public String getQuestion() {
		return Question;
	}

	public void setQuestion(String question) {
		Question = question;
	}

	public ArrayList<AnswerModel> getAnswer() {
		return answer;
	}

	public void setAnswer(ArrayList<AnswerModel> answer) {
		this.answer = answer;
	}

	private ArrayList<AnswerModel> answer;
}
