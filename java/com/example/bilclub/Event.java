package com.example.bilclub;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Time;
import java.sql.Date;

public class Event
{
    String eventName;
    Date eventDate;
    Time eventTime;
    LatLng loc;
    Uri uri;
    public Event(){}

    public Event(String eventName, Date eventDate, Time eventTime, LatLng loc, Uri uri) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.loc = loc;
        this.uri = uri;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Time getEventTime() {
        return eventTime;
    }

    public void setEventTime(Time eventTime) {
        this.eventTime = eventTime;
    }

    public LatLng getLoc() {
        return loc;
    }

    public void setLoc(LatLng loc) {
        this.loc = loc;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
