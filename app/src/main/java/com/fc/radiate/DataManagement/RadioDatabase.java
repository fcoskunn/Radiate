package com.fc.radiate.DataManagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fc.radiate.DataManagement.RadioContract.CountryEntry;
import com.fc.radiate.DataManagement.RadioContract.LanguageEntry;
import com.fc.radiate.DataManagement.RadioContract.PopularStationsEntry;
import com.fc.radiate.DataManagement.RadioContract.StationEntry;
import com.fc.radiate.DataManagement.RadioContract.FavEntry;


public class RadioDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "radioStations.db";
    private static final int DATABASE_VERSION = 2;


    public RadioDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_STATION_TABLE = "CREATE TABLE " + StationEntry.TABLE_NAME + " (" +
                StationEntry.ID + " INTEGER PRIMARY KEY NOT NULL, " +
                StationEntry.NAME + " TEXT NOT NULL, " +
                StationEntry.STREAM_URL + " TEXT NOT NULL, " +
                StationEntry.ICON_URL + " TEXT, " +
                StationEntry.COUNTY + " TEXT, " +
                StationEntry.LANGUAGE + " TEXT, " +
                StationEntry.BITRATE + " TEXT, " +
                " UNIQUE (" + StationEntry.ID + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_COUNTRY_TABLE = "CREATE TABLE " + CountryEntry.TABLE_NAME + " (" +
                CountryEntry.VALUE + " TEXT NOT NULL, " +
                CountryEntry.STATION_COUNT + " INTEGER);";

        final String SQL_CREATE_LANGUAGES_TABLE = "CREATE TABLE " + LanguageEntry.TABLE_NAME + " (" +
                LanguageEntry.VALUE + " TEXT NOT NULL);";

        final String SQL_CREATE_POPULAR_STATIONS_TABLE = "CREATE TABLE " + PopularStationsEntry.TABLE_NAME + " (" +
                PopularStationsEntry.ID + " INTEGER PRIMARY KEY NOT NULL, " +
                PopularStationsEntry.NAME + " TEXT NOT NULL, " +
                PopularStationsEntry.STREAM_URL + " TEXT NOT NULL, " +
                PopularStationsEntry.ICON_URL + " TEXT, " +
                PopularStationsEntry.COUNTY + " TEXT, " +
                PopularStationsEntry.LANGUAGE + " TEXT, " +
                PopularStationsEntry.BITRATE + " TEXT, " +
                " UNIQUE (" + PopularStationsEntry.ID + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_FAV_STATIONS_TABLE = "CREATE TABLE " + FavEntry.TABLE_NAME + " (" +
                FavEntry.ID + " INTEGER PRIMARY KEY NOT NULL, " +
                FavEntry.NAME + " TEXT NOT NULL, " +
                FavEntry.STREAM_URL + " TEXT NOT NULL, " +
                FavEntry.ICON_URL + " TEXT, " +
                FavEntry.COUNTY + " TEXT, " +
                FavEntry.LANGUAGE + " TEXT, " +
                FavEntry.BITRATE + " TEXT, " +
                " UNIQUE (" + FavEntry.ID + ") ON CONFLICT REPLACE);";


        db.execSQL(SQL_CREATE_COUNTRY_TABLE);
        db.execSQL(SQL_CREATE_LANGUAGES_TABLE);
        db.execSQL(SQL_CREATE_STATION_TABLE);
        db.execSQL(SQL_CREATE_POPULAR_STATIONS_TABLE);
        db.execSQL(SQL_CREATE_FAV_STATIONS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CountryEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + StationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PopularStationsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LanguageEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavEntry.TABLE_NAME);
        onCreate(db);
    }
}
