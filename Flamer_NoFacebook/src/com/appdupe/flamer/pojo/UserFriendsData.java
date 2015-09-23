package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class UserFriendsData 
{
	@SerializedName("pic_square")
	   private String friendPicurl;
	  
		@SerializedName("name")
	   private String friendname;

		public String getFriendPicurl() {
			return friendPicurl;
		}

		public void setFriendPicurl(String friendPicurl) {
			this.friendPicurl = friendPicurl;
		}

		public String getFriendname() {
			return friendname;
		}

		public void setFriendname(String friendname) {
			this.friendname = friendname;
		}
	   
	   
	
	   
}
