package com.appdupe.flamer.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appdupe.flamer.model.AnswerModel;
import com.appdupe.flamer.model.FacebookData;
import com.appdupe.flamer.model.PreferenceModel;
import com.appdupe.flamer.model.ProfileImageModel;
import com.appdupe.flamer.model.QuestionModel;
import com.appdupe.flamer.pojo.GridViewData;
import com.appdupe.flamer.pojo.ListviewAlubumData;
import com.facebook.Response;
import com.google.android.gms.internal.af;
import com.google.gson.JsonObject;

public class JsonParser {
	private static final String TAG = "JsonParser";

	/**
	 * 
	 * @param response
	 * @return
	 * 
	 *         { "albums": { "data": [ { "id": "142627302449341", "from": {
	 *         "name": "Imtiyaz Khalani", "id": "100001062166607" }, "name":
	 *         "Profile Pictures", "link":
	 *         "https://www.facebook.com/album.php?fbid=142627302449341&id=100001062166607&aid=22362"
	 *         , "cover_photo": "757135034331895", "privacy": "everyone",
	 *         "count": 18, "type": "profile", "created_time":
	 *         "2010-09-30T11:59:46+0000", "updated_time":
	 *         "2014-05-10T06:22:54+0000", "can_upload": false, "comments": {
	 *         "data": [ { "id": "142627302449341_4811117", "from": { "name":
	 *         "Asim Agham", "id": "635369849866641" }, "message":
	 *         "bhai jaan photo pan em che ho tmaro", "can_remove": true,
	 *         "created_time": "2012-04-28T18:21:11+0000", "like_count": 0,
	 *         "user_likes": false } ], "paging": { "cursors": { "after":
	 *         "MQ==", "before": "MQ==" } } } }, { "id": "378042385574497",
	 *         "from": { "name": "Imtiyaz Khalani", "id": "100001062166607" },
	 *         "name": "Cover Photos", "link":
	 *         "https://www.facebook.com/album.php?fbid=378042385574497&id=100001062166607&aid=90055"
	 *         , "cover_photo": "391985267513542", "privacy": "everyone",
	 *         "count": 5, "type": "cover", "created_time":
	 *         "2012-04-15T08:25:58+0000", "updated_time":
	 *         "2014-03-17T18:24:00+0000", "can_upload": false }, { "id":
	 *         "115698598475545", "from": { "name": "Imtiyaz Khalani", "id":
	 *         "100001062166607" }, "name": "Timeline Photos", "link":
	 *         "https://www.facebook.com/album.php?fbid=115698598475545&id=100001062166607&aid=6876"
	 *         , "cover_photo": "115698601808878", "privacy": "everyone",
	 *         "count": 17, "type": "wall", "created_time":
	 *         "2010-06-14T08:10:26+0000", "updated_time":
	 *         "2013-07-12T05:21:53+0000", "can_upload": false }, { "id":
	 *         "574601469251920", "from": { "name": "Imtiyaz Khalani", "id":
	 *         "100001062166607" }, "name": "Sample Photos", "link":
	 *         "https://www.facebook.com/album.php?fbid=574601469251920&id=100001062166607&aid=1073741825"
	 *         , "cover_photo": "574601492585251", "privacy": "friends",
	 *         "count": 2, "type": "app", "created_time":
	 *         "2013-05-27T12:00:06+0000", "updated_time":
	 *         "2013-05-27T12:00:13+0000", "can_upload": false, "comments": {
	 *         "data": [ { "id": "574601469251920_6386845", "from": { "name":
	 *         "Yogesh Raval", "id": "274605306044578" }, "message":
	 *         "a bhai priksa puri thai gai", "can_remove": true,
	 *         "created_time": "2013-05-27T12:23:57+0000", "like_count": 0,
	 *         "user_likes": false }, { "id": "574601469251920_6386848", "from":
	 *         { "name": "Yogesh Raval", "id": "274605306044578" }, "message":
	 *         "computer ma formet marvanu 6", "can_remove": true,
	 *         "created_time": "2013-05-27T12:24:13+0000", "like_count": 0,
	 *         "user_likes": false }, { "id": "574601469251920_6386851", "from":
	 *         { "name": "Yogesh Raval", "id": "274605306044578" }, "message":
	 *         "hallo kaya 6", "can_remove": true, "created_time":
	 *         "2013-05-27T12:24:45+0000", "like_count": 0, "user_likes": false
	 *         } ], "paging": { "cursors": { "after": "Mw==", "before": "MQ==" }
	 *         } } }, { "id": "535723446473056", "from": { "name":
	 *         "Imtiyaz Khalani", "id": "100001062166607" }, "name":
	 *         "Mobile Uploads", "link":
	 *         "https://www.facebook.com/album.php?fbid=535723446473056&id=100001062166607&aid=122169"
	 *         , "privacy": "everyone", "type": "mobile", "created_time":
	 *         "2013-02-26T16:42:09+0000", "updated_time":
	 *         "2013-04-30T07:58:16+0000", "can_upload": false } ], "paging": {
	 *         "cursors": { "after": "NTM1NzIzNDQ2NDczMDU2", "before":
	 *         "MTQyNjI3MzAyNDQ5MzQx" } } },
	 * 
	 *         }
	 */

	public static final ArrayList<ListviewAlubumData> getAlbumList(
			Response response) {
		ArrayList<ListviewAlubumData> alubumDatas = new ArrayList<ListviewAlubumData>();
		try {
			JSONObject jsonObject = response.getGraphObject()
					.getInnerJSONObject();
			JSONArray array = jsonObject.getJSONArray("data");
			ListviewAlubumData model;
			for (int i = 0; i < array.length(); i++) {
				model = new ListviewAlubumData();
				jsonObject = array.getJSONObject(i);
				model.setAlubumName(jsonObject.getString("name"));
				model.setAlubumid(jsonObject.getString("id"));
				model.setPhotoCount(jsonObject.getInt("count"));
				jsonObject = jsonObject.getJSONObject("photos");
				JSONArray photoesArray = jsonObject.getJSONArray("data");
				if (photoesArray.length() != 0) {
					jsonObject = photoesArray.getJSONObject(0);
					photoesArray = jsonObject.getJSONArray("images");
					if (photoesArray.length() != 0) {
						jsonObject = photoesArray.getJSONObject(0);
						model.setPickUrl(jsonObject.getString("source"));
					}
				}
				alubumDatas.add(model);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return alubumDatas;
	}

	public static final ArrayList<GridViewData> getAlbumPhotoesList(
			Response response) {
		ArrayList<GridViewData> list = new ArrayList<GridViewData>();
		try {
			JSONObject jsonObject = response.getGraphObject()
					.getInnerJSONObject();
			jsonObject = jsonObject.getJSONObject("photos");

			JSONArray array = jsonObject.getJSONArray("data");
			GridViewData model;

			for (int i = 0; i < array.length(); i++) {
				model = new GridViewData();
				jsonObject = array.getJSONObject(i);
				model.setPicUrl(jsonObject.getString("source"));
				list.add(model);
			}

		} catch (Exception e) {

		}

		return list;
	}

	public static final ArrayList<QuestionModel> parseQuestionData(String json) {
		AppLog.Log(TAG, "QuestionData:" + json);
		ArrayList<QuestionModel> list = new ArrayList<QuestionModel>();
		try {
			JSONObject object = new JSONObject(json);
			if (object.getInt(Constant.ERR_FLAG) == 0) {
				JSONArray questionArray = object
						.getJSONArray(Constant.QUESTION_DETAIL);
				for (int i = 0; i < questionArray.length(); i++) {
					JSONObject jsonObject = questionArray.getJSONObject(i);
					QuestionModel model = new QuestionModel();
					model.setQuestionId(jsonObject.getInt(Constant.QUESTION_ID));
					model.setQuestion(jsonObject.getString(Constant.QUESTION));
					ArrayList<AnswerModel> answerModelList = new ArrayList<AnswerModel>();
					JSONArray answerArray = jsonObject
							.getJSONArray(Constant.OPTIONS);
					for (int j = 0; j < answerArray.length(); j++) {
						JSONObject answerObject = answerArray.getJSONObject(j);
						AnswerModel answerModel = new AnswerModel();
						answerModel.setQuestionId(answerObject
								.getInt(Constant.QUESTION_ID));
						answerModel.setAnswerId(answerObject
								.getInt(Constant.ANSWER_ID));
						answerModel.setAnswer(answerObject
								.getString(Constant.OPTION));
						answerModel.setFlag(answerObject.getInt(Constant.FLAG));
						answerModelList.add(answerModel);
					}
					model.setAnswer(answerModelList);
					list.add(model);
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public static FacebookData parseUserInfo(JSONObject jsonObject) {
		FacebookData facebookData = new FacebookData();
		try {
			facebookData.setId(jsonObject.getString("id"));
			String sex = jsonObject.getString("gender");
			if (sex.equals("male")) {
				facebookData.setSex("1");
			} else {
				facebookData.setSex("2");
			}
			facebookData.setFirstName(jsonObject.getString("first_name"));
			facebookData.setLastName(jsonObject.getString("last_name"));
			if (jsonObject.has("birthday")) {
				String dateString = jsonObject.getString("birthday");
				Date date = new SimpleDateFormat("MM/dd/yyyy")
						.parse(dateString);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				String myString = calendar.get(Calendar.YEAR) + "-"
						+ (calendar.get(Calendar.MONTH) + 1) + "-"
						+ calendar.get(Calendar.DAY_OF_MONTH);
				AppLog.Log(TAG, "Date from Facebook:" + myString);
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// facebookData.setBirthday(sdf.format(date));
				facebookData.setBirthday(myString);
			} else {
				facebookData.setBirthday("");
			}
			JSONObject ageObject = jsonObject.getJSONObject("age_range");
			if (ageObject.has("min")) {
				facebookData.setMinAge(ageObject.getInt("min"));
			}
			if (ageObject.has("max")) {
				facebookData.setMaxAge(ageObject.getInt("max"));
			}
			JSONObject pictureObject = jsonObject.getJSONObject("picture");
			pictureObject = pictureObject.getJSONObject("data");
			facebookData.setProfilePicture(pictureObject.getString("url"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return facebookData;
	}

	public static FacebookData parseLoginResponse(String response) {
		FacebookData data = new FacebookData();
		try {
			JSONObject object = new JSONObject(response);
			if (object.getInt(Constant.ERR_FLAG) == 0) {
				if (object.has("profilePic")) {
					data.setProfilePicture(object.getString("profilePic"));
					data.setMinAge(object.getInt("age"));
					data.setStatus(object.getString("status"));
					return data;
				}
			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static PreferenceModel parsePreferenceResponse(String response) {
		PreferenceModel model = new PreferenceModel();

		try {
			JSONObject object = new JSONObject(response);
			if (object.getInt("errFlag") == 0) {
				model.setErrFlag(0);
				model.setPrefLowerAge(object.getInt("prLAge"));
				model.setPrefRadius(object.getInt("prRad"));
				model.setPrefSex(object.getInt("prSex"));
				model.setPrefUpperAge(object.getInt("prUAge"));
				model.setSex(object.getInt("sex"));
			} else {
				model.setErrFlag(1);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	public static ArrayList<ProfileImageModel> parseProfileImageData(String json) {
		ArrayList<ProfileImageModel> list = new ArrayList<ProfileImageModel>();
		try {
			JSONObject jsonObject = new JSONObject(json);
			if (jsonObject.getInt(Constant.ERR_FLAG) == 0) {
				JSONArray array = new JSONArray(
						jsonObject.getString("Userphotos"));
				for (int i = 0; i < array.length(); i++) {
					JSONObject object = array.getJSONObject(i);
					ProfileImageModel model = new ProfileImageModel();
					model.setImageId(object.getInt("image_id"));
					model.setIndexId(object.getInt("index_id"));
					model.setImageUrl(object.getString("image_url"));
					list.add(model);

				}
				return list;
			} else {
				return list;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public static String parseStatusResponse(String statusResponse) {
		try {
			JSONObject object = new JSONObject(statusResponse);
			if (object.getInt(Constant.ERR_FLAG) == 0) {
				return object.getString("errMsg");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
