package com.fc.radiate.DataManagement;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.fc.radiate.DataManagement.RadioContract.CountryEntry;
import com.fc.radiate.DataManagement.RadioContract.LanguageEntry;
import com.fc.radiate.DataManagement.RadioContract.PopularStationsEntry;
import com.fc.radiate.DataManagement.RadioContract.StationEntry;
import com.fc.radiate.DataManagement.RadioContract.*;

public class RadiateContentProvider extends ContentProvider {
    public static final int STATION = 100;
    public static final int COUNTRY = 200;
    public static final int LANGUAGE = 300;
    public static final int POPULAR = 400;
    public static final int FAVS = 500;


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private RadioDatabase radioDatabase;

    public RadiateContentProvider() {
    }

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = RadioContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, RadioContract.PATH_COUNTRY, COUNTRY);
        matcher.addURI(authority, RadioContract.PATH_LANGUAGE, LANGUAGE);
        matcher.addURI(authority, RadioContract.PATH_POPULARS, POPULAR);
        matcher.addURI(authority, RadioContract.PATH_STATION, STATION);
        matcher.addURI(authority, RadioContract.PATH_FAVS, STATION);

        return matcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = radioDatabase.getWritableDatabase();
        db.delete(FavEntry.TABLE_NAME, selection, selectionArgs);
        return 1;
    }

    @Override
    public int update(Uri uri, ContentValues values,String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case STATION:
                return StationEntry.CONTENT_ITEM_TYPE;
            case POPULAR:
                return PopularStationsEntry.CONTENT_ITEM_TYPE;
            case LANGUAGE:
                return LanguageEntry.CONTENT_TYPE;
            case FAVS:
                return LanguageEntry.CONTENT_TYPE;
            case COUNTRY:
                return CountryEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = radioDatabase.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case STATION: {
                long _id = db.insert(StationEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = StationEntry.buildStationUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
            }
            break;
            case COUNTRY: {
                long _id = db.insert(CountryEntry.TABLE_NAME, null, values);

                if (_id > 0) {
                    returnUri = CountryEntry.buildCountryUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
            }
            break;
            case LANGUAGE: {
                long _id = db.insert(LanguageEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = LanguageEntry.buildLanguageUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
            }
            break;
            case POPULAR: {
                long _id = db.insert(PopularStationsEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = PopularStationsEntry.buildPopularStationsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
            }
            case FAVS: {
                long _id = db.insert(FavEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = FavEntry.buildFavsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
            }
            break;
            default: {
                throw new UnsupportedOperationException("Unknown uri:" + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        radioDatabase = new RadioDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = radioDatabase.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case STATION: {
                retCursor = db.query(StationEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }
            break;
            case COUNTRY: {
                retCursor = db.query(CountryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }
            break;
            case LANGUAGE: {
                retCursor = db.query(LanguageEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }
            break;
            case FAVS: {
                retCursor = db.query(FavEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }
            break;
            case POPULAR: {
                retCursor = db.query(PopularStationsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }
            break;
            default: {
                throw new UnsupportedOperationException("Unknown uri:" + uri);
            }
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

}
