package pictureManager.domain;

import java.sql.Timestamp;

public class Event extends IdentifiableEntity {
	
	private String eventName;
	private Timestamp eventDate;
	private EventLocation eventLocation;
	private long eventLocationId;
	
	public Event(){}
	
	public Event(String eveName, Timestamp eveDate, EventLocation eveLocation){
		this.eventName = eveName;
		this.eventDate = eveDate;
		this.eventLocation = eveLocation;

	}
	
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Timestamp getEventDate() {
		return eventDate;
	}

	public void setEventDate(Timestamp eventDate) {
		this.eventDate = eventDate;
	}

	public EventLocation getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(EventLocation eventLocation) {
		this.eventLocation = eventLocation;
	}
	
	public long getEventLocationId() {
		return eventLocationId;
	}

	public void setEventLocationId(long eventLocationId) {
		this.eventLocationId = eventLocationId;
	}

	@Override
	public String toString(){
		return(this.eventName + "(id:" + this.getId() + ") " + " happened om " + this.eventDate + " at this address: " + this.eventLocation.toString());
	}


}
