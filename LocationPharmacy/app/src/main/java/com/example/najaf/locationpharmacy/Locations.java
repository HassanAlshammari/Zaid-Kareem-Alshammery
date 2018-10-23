package com.example.najaf.locationpharmacy;

public class Locations {
    private  String ID;
    private  String LocationName;
    private  String LocationAddress;
    private  String LocationDescription;
    private  String Log;
    private  String Lat;
    private  String State;

    public Locations(String ID, String locationName, String locationAddress, String locationDescription, String log, String lat, String state) {
        this.ID = ID;
        LocationName = locationName;
        LocationAddress = locationAddress;
        LocationDescription = locationDescription;
        Log = log;
        Lat = lat;
        State = state;
    }

    public Locations(String locationName, String locationAddress, String locationDescription, String log, String lat, String state) {
        LocationName = locationName;
        LocationAddress = locationAddress;
        LocationDescription = locationDescription;
        Log = log;
        Lat = lat;
        State = state;
    }
    public Locations(){}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getLocationAddress() {
        return LocationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        LocationAddress = locationAddress;
    }

    public String getLocationDescription() {
        return LocationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        LocationDescription = locationDescription;
    }

    public String getLog() {
        return Log;
    }

    public void setLog(String log) {
        Log = log;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
