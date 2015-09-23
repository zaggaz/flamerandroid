package com.appdupe.flamer.pojo;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FaceBookAlbumData 
{
	@SerializedName("data")
    private ArrayList<FaceBookAlubumScr> alubumScrsList;

	public ArrayList<FaceBookAlubumScr> getAlubumScrsList() {
		return alubumScrsList;
	}

	public void setAlubumScrsList(ArrayList<FaceBookAlubumScr> alubumScrsList) {
		this.alubumScrsList = alubumScrsList;
	}
	
}
