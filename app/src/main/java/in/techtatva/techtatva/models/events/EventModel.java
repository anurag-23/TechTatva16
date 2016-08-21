package in.techtatva.techtatva.models.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Naman on 6/3/2016.
 */
public class EventModel extends RealmObject {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("event_name")
    @Expose
    private String eventName;

    @SerializedName("event_id")
    @Expose
    private String eventId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("event_max_team_number")
    @Expose
    private String eventMaxTeamNumber;

    @SerializedName("cat_name")
    @Expose
    private String catName;

    @SerializedName("cat_id")
    @Expose
    private String catId;

    @SerializedName("venue")
    @Expose
    private String venue;

    @SerializedName("start_time")
    @Expose
    private String startTime;

    @SerializedName("end_time")
    @Expose
    private String endTime;

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("contact_name")
    @Expose
    private String contactName;

    @SerializedName("contact_number")
    @Expose
    private String contactNumber;

    public EventModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getEventMaxTeamNumber() {
        return eventMaxTeamNumber;
    }

    public void setEventMaxTeamNumber(String eventMaxTeamNumber) {
        this.eventMaxTeamNumber = eventMaxTeamNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
