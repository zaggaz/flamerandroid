package com.appdupe.flamer.pojo;

import com.google.gson.annotations.SerializedName;

public class UserFaceBookInfo {
	@SerializedName("likes")
	private UserLikes urserLikes;

	@SerializedName("hometown")
	private Hometown homeTown;

	@SerializedName("first_name")
	private String firstName;
	@SerializedName("last_name")
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@SerializedName("email")
	private String email;
	@SerializedName("id")
	private long faceBookId;
	@SerializedName("relationship_status")
	private String relationship_status;
	@SerializedName("birthday")
	private String birthday;
	@SerializedName("bio")
	private String bio;
	@SerializedName("gender")
	private String gender;
	@SerializedName("name")
	private String name;

	@SerializedName("location")
	private Location location;

	@SerializedName("interested_in")
	private String[] interestedIn;
	@SerializedName("age_range")
	private AgeRange ageRange;

	public AgeRange getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(AgeRange ageRange) {
		this.ageRange = ageRange;
	}

	public String[] getInterestedIn() {
		return interestedIn;
	}

	public void setInterestedIn(String[] interestedIn) {
		this.interestedIn = interestedIn;
	}

	public long getFaceBookId() {
		return faceBookId;
	}

	public void setFaceBookId(long faceBookId) {
		this.faceBookId = faceBookId;
	}

	public String getRelationship_status() {
		return relationship_status;
	}

	public void setRelationship_status(String relationship_status) {
		this.relationship_status = relationship_status;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Hometown getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(Hometown homeTown) {
		this.homeTown = homeTown;
	}
	// public UserLikes getUrserLikes() {
	// return urserLikes;
	// }
	// public void setUrserLikes(UserLikes urserLikes) {
	// this.urserLikes = urserLikes;
	// }

}
