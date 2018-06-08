package android.test.com.trips.model;

import java.util.Arrays;

public class Trip {

    public static final String TABLE_NAME = "trips";

    public static final String COLUMN_TRIP_TYPE = "type";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_DEST = "destination";
    public static final String COLUMN_LOCATION = "location";

    public static final String COLUMN_ID = "id";

    private String type;
    private String title;
    private String date;
    private String duration;
    private String comment;
    private  byte[] photo;
    private String destination;
    private String location;

    private int id;


    //create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TITLE + " TEXT,"  + COLUMN_DATE + " TEXT," + COLUMN_DURATION + " TEXT," +
            COLUMN_COMMENT + " TEXT," + COLUMN_PHOTO + " BLOB," + COLUMN_DEST + " TEXT," + COLUMN_LOCATION + " TEXT," + COLUMN_TRIP_TYPE + " TEXT" + ")";


    public Trip() {
    }


    public Trip(String type, String title, String date, String duration, String comment,  byte[] photo, String destination, String location) {
        this.type = type;
        this.title = title;
        this.date = date;
        this.duration = duration;
        this.comment = comment;
        this.photo = photo;
        this.destination = destination;
        this.location = location;
    }




    public Trip(String type, String title, String date, String duration, String comment,  byte[] photo, String destination, String location, int id) {
        this.type = type;
        this.title = title;
        this.date = date;
        this.duration = duration;
        this.comment = comment;
        this.photo = photo;
        this.destination = destination;
        this.location = location;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public  byte[] getPhoto() {
        return photo;
    }

    public void setPhoto( byte[] photo) {
        this.photo = photo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "Trip{" + "type='" + type + '\'' + ", title='" + title + '\'' + ", date='" + date + '\'' + ", duration='" + duration + '\'' + ", comment='" + comment + '\'' + ", photo=" + Arrays.toString(photo) + ", destination='" + destination + '\'' + ", location='" + location + '\'' + ", id=" + id + '}';
    }
}
