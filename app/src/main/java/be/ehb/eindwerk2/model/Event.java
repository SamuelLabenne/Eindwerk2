package be.ehb.eindwerk2.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Event implements Serializable {

    private int id;
    private String title, description;
    Timestamp timestamp;
    String Time;
    String City;
    /*private List<User> invited;
    private Location location;
    private LocalDateTime time;*/

    public Event(){}

    public Event(String title, String description /*, List<User> invited, Location location, LocalDateTime time*/) {
        this.title = title;
        this.description = description;
        /*this.invited = invited;
        this.location = location;
        this.time = time;*/
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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
/*
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
    }*/
}