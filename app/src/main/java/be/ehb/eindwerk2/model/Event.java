package be.ehb.eindwerk2.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Event implements Serializable {

    private int id;
    private String title, description;
    private List<User> invited;
    private Location location;
    private LocalDateTime time;

    public Event(){}

    public Event(String title, String description, List<User> invited, Location location, LocalDateTime time) {
        this.title = title;
        this.description = description;
        this.invited = invited;
        this.location = location;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getInvited() {
        return invited;
    }

    public void setInvited(List<User> invited) {
        this.invited = invited;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}