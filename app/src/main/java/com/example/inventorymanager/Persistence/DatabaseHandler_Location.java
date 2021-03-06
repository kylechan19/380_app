package com.example.inventorymanager.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.inventorymanager.Util.Constants;
import com.example.inventorymanager.model.Location;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHandler_Location extends SQLiteOpenHelper {

    private Context context;

    public DatabaseHandler_Location(@Nullable Context context) {
        super(context, Constants.DB_NAME_LOCATION, null, Constants.DB_VERSION_LOCATION + 1 + 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROFILE_TABLE = "CREATE TABLE " + Constants.TABLE__NAME_LOCATION + "("
                + Constants.KEY_ID_LOCATION + " Integer PRIMARY KEY,"
                + Constants.KEY_LOCATION_NAME + " TEXT,"
                + Constants.KEY_ADDRESS + " TEXT,"
                + Constants.KEY_SERIAL_NUMBER_LOCATION + " TEXT,"
                + Constants.KEY_MODEL_LOCATION + " TEXT, "
                + Constants.KEY_QUANTITY_LOCATION+ " INTEGER,"
                + Constants.KEY_PRICE_LOCATION + " REAL,"
                + Constants.KEY_DATE_LOCATION + " LONG);";

        db.execSQL(CREATE_PROFILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE__NAME_LOCATION);
        onCreate(db);
    }

    //CRUD Operations

    public void addLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.KEY_LOCATION_NAME, location.getLocationName());
        contentValues.put(Constants.KEY_ADDRESS, location.getAddress());

        contentValues.put(Constants.KEY_SERIAL_NUMBER_LOCATION, location.getSerialNo());
        contentValues.put(Constants.KEY_MODEL_LOCATION, location.getModel());
        contentValues.put(Constants.KEY_QUANTITY_LOCATION, location.getQuantity());
        contentValues.put(Constants.KEY_PRICE_LOCATION, location.getPrice());
        contentValues.put(Constants.KEY_DATE_LOCATION, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE__NAME_LOCATION, null, contentValues);
        Log.d("@HERE", "addLocation: " +location.getLocationName());
    }

    public Location getLocation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE__NAME_LOCATION,
                new String[]{Constants.KEY_ID_LOCATION,
                        Constants.KEY_LOCATION_NAME,
                        Constants.KEY_ADDRESS,
                        Constants.KEY_SERIAL_NUMBER_LOCATION,
                        Constants.KEY_MODEL_LOCATION,
                        Constants.KEY_QUANTITY_LOCATION,
                        Constants.KEY_PRICE_LOCATION,
                        Constants.KEY_DATE_LOCATION},
                Constants.KEY_ID_LOCATION + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }

        Location location = new Location();
        if (cursor!=null) {
            location.setKey(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID_LOCATION))));
            location.setLocationName(cursor.getString(cursor.getColumnIndex(Constants.KEY_LOCATION_NAME)));
            location.setAddress(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDRESS)));

            location.setSerialNo(cursor.getString(cursor.getColumnIndex(Constants.KEY_SERIAL_NUMBER_LOCATION)));
            location.setModel(cursor.getString(cursor.getColumnIndex(Constants.KEY_MODEL_LOCATION)));
            location.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QUANTITY_LOCATION)));
            location.setPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_PRICE_LOCATION)));

            //Convert Time (long) to string
            DateFormat dateFormat = DateFormat.getDateInstance();
            String format = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_LOCATION))));
            location.setDateOfPurchase(format);
        }

        return location;
    }

    public ArrayList<Location> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Location> locationsList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE__NAME_LOCATION,
                new String[]{Constants.KEY_ID_LOCATION,
                        Constants.KEY_LOCATION_NAME,
                        Constants.KEY_ADDRESS,
                        Constants.KEY_SERIAL_NUMBER_LOCATION,
                        Constants.KEY_MODEL_LOCATION,
                        Constants.KEY_QUANTITY_LOCATION,
                        Constants.KEY_PRICE_LOCATION,
                        Constants.KEY_DATE_LOCATION},
                null, null, null, null, null);

        if(cursor.moveToFirst()) {
            do {
                Location location = new Location();
                location.setKey(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID_LOCATION))));
                location.setLocationName(cursor.getString(cursor.getColumnIndex(Constants.KEY_LOCATION_NAME)));
                location.setAddress(cursor.getString(cursor.getColumnIndex(Constants.KEY_ADDRESS)));

                location.setSerialNo(cursor.getString(cursor.getColumnIndex(Constants.KEY_SERIAL_NUMBER_LOCATION)));
                location.setModel(cursor.getString(cursor.getColumnIndex(Constants.KEY_MODEL_LOCATION)));
                location.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QUANTITY_LOCATION)));
                location.setPrice(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_PRICE_LOCATION)));

                //Convert Time (long) to string
                DateFormat dateFormat = DateFormat.getDateInstance();
                String format = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_LOCATION))));
                location.setDateOfPurchase(format);

                locationsList.add(location);
            }while (cursor.moveToNext());
        }

        return locationsList;
    }

    public int updateLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.KEY_LOCATION_NAME, location.getLocationName());
        contentValues.put(Constants.KEY_ADDRESS, location.getAddress());

        contentValues.put(Constants.KEY_SERIAL_NUMBER_LOCATION, location.getSerialNo());
        contentValues.put(Constants.KEY_MODEL_LOCATION, location.getModel());
        contentValues.put(Constants.KEY_QUANTITY_LOCATION, location.getQuantity());
        contentValues.put(Constants.KEY_PRICE_LOCATION, location.getPrice());
        contentValues.put(Constants.KEY_DATE_LOCATION, java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE__NAME_LOCATION, contentValues, Constants.KEY_ID_LOCATION +"=?", new String[]{String.valueOf(location.getKey())});
    }

    public int getLocationCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE__NAME_LOCATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    public void deleteLocation(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE__NAME_LOCATION, Constants.KEY_ID_LOCATION + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
