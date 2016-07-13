package in.techtatva.techtatva.models.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



/**
 * Created by Naman on 7/6/2016.
 */
public class EventsListModel {
    @SerializedName("data")
    @Expose
    private List<EventModel> events;

    public EventsListModel() {
    }

    public List<EventModel> getEvents() {
        return events;
    }

    public void setEvents(List<EventModel> events) {
        this.events = events;
    }
}
