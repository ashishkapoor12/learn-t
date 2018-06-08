package android.test.com.trips;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.com.trips.model.Trip;
import android.test.com.trips.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    //  Version
    private static final int DATABASE_VERSION = 1;
    //  Name
    private static final String DATABASE_NAME = "trips_db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create
        //table
        db.execSQL(Trip.CREATE_TABLE);
        db.execSQL(User.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Trip.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }


    public void deleteUserData(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(User.TABLE_NAME, User.COLUMN_PRIM_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();

    }


    public void insertUser(User user) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(User.COLUMN_NAME, user.getName());
        values.put(User.COLUMN_ID, user.getUser_id());


        values.put(User.COLUMN_EMAIL, user.getEmail());
        values.put(User.COLUMN_GENDER, user.getGender());
        values.put(User.COLUMN_COMMENT, user.getComment());

        db.replace(User.TABLE_NAME, null, values);

        long id = db.insert(User.TABLE_NAME, null, values);
        db.close();

        // return id;
    }


    public User getUser() {

        String selectQuery = "SELECT * FROM " + User.TABLE_NAME + " ORDER BY id DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String str = "";
        User user = null;
        if (cursor.moveToFirst())

            user = new User(cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(User.COLUMN_ID)), cursor.getString(cursor.getColumnIndex(User.COLUMN_EMAIL)), cursor.getString(cursor.getColumnIndex(User.COLUMN_GENDER)), cursor.getInt(cursor.getColumnIndex(Trip.COLUMN_ID)), cursor.getString(cursor.getColumnIndex(Trip.COLUMN_COMMENT)));

        cursor.close();

        return user;
    }

    public long insertTrip(Trip trip) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add

        values.put(Trip.COLUMN_TRIP_TYPE, trip.getComment());
        values.put(Trip.COLUMN_TITLE, trip.getTitle());
        values.put(Trip.COLUMN_DATE, trip.getDate());
        values.put(Trip.COLUMN_DURATION, trip.getDuration());
        values.put(Trip.COLUMN_COMMENT, trip.getComment());
        values.put(Trip.COLUMN_PHOTO, trip.getPhoto());
        values.put(Trip.COLUMN_DEST, trip.getDestination());
        values.put(Trip.COLUMN_LOCATION, trip.getLocation());

        // insert row
        long id = db.insert(Trip.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public Trip getTrip(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Trip.TABLE_NAME, new String[]{Trip.COLUMN_ID, Trip.COLUMN_TRIP_TYPE, Trip.COLUMN_TITLE, Trip.COLUMN_DURATION, Trip.COLUMN_DATE, Trip.COLUMN_DEST, Trip.COLUMN_COMMENT, Trip.COLUMN_PHOTO, Trip.COLUMN_LOCATION

        }, Trip.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) cursor.moveToFirst();

        // prepare  object
        Trip trip = new Trip(cursor.getString(cursor.getColumnIndex(Trip.COLUMN_TRIP_TYPE)), cursor.getString(cursor.getColumnIndex(Trip.COLUMN_TITLE)), cursor.getString(cursor.getColumnIndex(Trip.COLUMN_DATE)), cursor.getString(cursor.getColumnIndex(Trip.COLUMN_DURATION)),

                cursor.getString(cursor.getColumnIndex(Trip.COLUMN_COMMENT)), cursor.getBlob(cursor.getColumnIndex(Trip.COLUMN_PHOTO)), cursor.getString(cursor.getColumnIndex(Trip.COLUMN_DEST)), cursor.getString(cursor.getColumnIndex(Trip.COLUMN_LOCATION)),

                cursor.getInt(cursor.getColumnIndex(Trip.COLUMN_ID)));

        // close the db connection
        cursor.close();

        return trip;
    }


    public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Trip.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Trip trip = new Trip();
                trip.setId(cursor.getInt(cursor.getColumnIndex(Trip.COLUMN_ID)));
                trip.setType(cursor.getString(cursor.getColumnIndex(Trip.COLUMN_TRIP_TYPE)));
                trip.setTitle(cursor.getString(cursor.getColumnIndex(Trip.COLUMN_TITLE)));
                trip.setDate(cursor.getString(cursor.getColumnIndex(Trip.COLUMN_DATE)));
                trip.setDuration(cursor.getString(cursor.getColumnIndex(Trip.COLUMN_DURATION)));
                trip.setComment(cursor.getString(cursor.getColumnIndex(Trip.COLUMN_COMMENT)));
                trip.setPhoto(cursor.getBlob(cursor.getColumnIndex(Trip.COLUMN_PHOTO)));
                trip.setDestination(cursor.getString(cursor.getColumnIndex(Trip.COLUMN_DEST)));
                trip.setLocation(cursor.getString(cursor.getColumnIndex(Trip.COLUMN_LOCATION)));
                trips.add(trip);
            } while (cursor.moveToNext());
        }
        db.close();
        return trips;
    }

    public int getTripsCount() {
        String countQuery = "SELECT  * FROM " + Trip.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public void deleteTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Trip.TABLE_NAME, Trip.COLUMN_ID + " = ?", new String[]{String.valueOf(trip.getId())});
        db.close();
    }

}
