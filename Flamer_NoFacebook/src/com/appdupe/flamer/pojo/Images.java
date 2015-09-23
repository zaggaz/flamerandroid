package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class Images 
{
	 @SerializedName("0")
    private String firstImage;
	 @SerializedName("1")
    private String secondImage;
	 @SerializedName("2")
    private String thirdtImage;
	 @SerializedName("3")
    private String fourthImage;
	public String getFirstImage() {
		return firstImage;
	}
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}
	public String getSecondImage() {
		return secondImage;
	}
	public void setSecondImage(String secondImage) {
		this.secondImage = secondImage;
	}
	public String getThirdtImage() {
		return thirdtImage;
	}
	public void setThirdtImage(String thirdtImage) {
		this.thirdtImage = thirdtImage;
	}
	public String getFourthImage() {
		return fourthImage;
	}
	public void setFourthImage(String fourthImage) 
	{
		this.fourthImage = fourthImage;
	}
    
   
	/*"images": [
		        {
		            "0": "http://108.166.190.172:81/tinderClone/pics/mypic2.jpg"
		        },
		        {
		            "1": "http://108.166.190.172:81/tinderClone/pics/mypic3.jpg"
		        }
		    ]*/
	
	
}
