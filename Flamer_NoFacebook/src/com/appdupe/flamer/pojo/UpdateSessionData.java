package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class UpdateSessionData 
{       @SerializedName("errNum")
        private int errNum;

        @SerializedName("errFlag")
        private int errFlag;
        public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		@SerializedName("errMsg")
        private String errMsg;
        
        @SerializedName("token")
        private String token;
        
		public int getErrNum() {
			return errNum;
		}
		public void setErrNum(int errNum) 
		{
			this.errNum = errNum;
		}
		public int getErrFlag() {
			return errFlag;
		}
		public void setErrFlag(int errFlag) {
			this.errFlag = errFlag;
		}
		public String getErrMsg() {
			return errMsg;
		}
		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}
	
	
	
	

	
}
