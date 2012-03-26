package pictureManager.domain;

import java.sql.Timestamp;

public class Picture extends IdentifiableEntity {
	
	private String pictureName;
	private Timestamp pictureCreated;
	private int pictureDimensionWidth;
	private int pictureDimensionHeight;
	private String pictureStorageLocation;
	private long eventID;
	
	public Picture(){}
	
	public Picture(String picName, Timestamp picCreated, int picDimensionW, int picDimensionH, String picLocation){
		this.pictureName = picName;
		this.pictureCreated = picCreated;
		this.pictureStorageLocation = picLocation;
		this.pictureDimensionHeight = picDimensionH;
		this.pictureDimensionWidth = picDimensionW;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public Timestamp getPictureCreated() {
		return pictureCreated;
	}

	public void setPictureCreated(Timestamp pictureCreated) {
		this.pictureCreated = pictureCreated;
	}

	public int getPictureDimensionWidth() {
		return pictureDimensionWidth;
	}

	public void setPictureDimensionWidth(int pictureDimensionWidth) {
		this.pictureDimensionWidth = pictureDimensionWidth;
	}

	public int getPictureDimensionHeight() {
		return pictureDimensionHeight;
	}

	public void setPictureDimensionHeight(int pictureDimensionHeight) {
		this.pictureDimensionHeight = pictureDimensionHeight;
	}

	public String getPictureLocation() {
		return pictureStorageLocation;
	}

	public void setPictureLocation(String pictureLocation) {
		this.pictureStorageLocation = pictureLocation;
	}
	
	public long getEventID() {
		return eventID;
	}

	public void setEventID(long eventID) {
		this.eventID = eventID;
	}

	@Override
	public String toString(){
		return(this.pictureName + "(id:" + this.getId() + ") " + " is located at " + this.pictureStorageLocation + " has a width of: " + this.pictureDimensionWidth + " and a height of: " + this.pictureDimensionHeight + " was created on " + this.pictureCreated.toString());
	}

}
