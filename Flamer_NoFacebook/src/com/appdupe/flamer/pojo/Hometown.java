package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

import android.util.Log;

public class Hometown 
{           @SerializedName("id")
            private long id;
           @SerializedName("name") 
            private String homeTownName;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getHomeTownName() {
			return homeTownName;
		}
		public void setHomeTownName(String homeTownName) {
			this.homeTownName = homeTownName;
		}
           
            
}
