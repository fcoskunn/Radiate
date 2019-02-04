package com.fc.radiate.DataManagement;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.BaseColumns;

import java.io.ByteArrayOutputStream;

public class RadioContract {

    public static final String CONTENT_AUTHORITY = "com.fc.radiate";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_STATION = "station";
    public static final String PATH_COUNTRY = "country";
    public static final String PATH_LANGUAGE = "language";
    public static final String PATH_POPULARS = "populars";
    public static final String PATH_FAVS= "favs";


    public static class StationEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_STATION)
                .build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_STATION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_STATION;

        public static final String TABLE_NAME = "stations";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String STREAM_URL = "url";
        public static final String ICON_URL = "icon_url";
        public static final String COUNTY = "country";
        public static final String LANGUAGE = "language";
        public static final String BITRATE = "bitrate";

        public static Uri buildStationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }

    public static class FavEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVS)
                .build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVS;

        public static final String TABLE_NAME = "favs";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String STREAM_URL = "url";
        public static final String ICON_URL = "icon_url";
        public static final String COUNTY = "country";
        public static final String LANGUAGE = "language";
        public static final String BITRATE = "bitrate";

        public static Uri buildFavsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }

    public static class CountryEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_COUNTRY)
                .build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_COUNTRY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_COUNTRY;

        public static final String TABLE_NAME = "countries";
        public static final String VALUE = "name";
        public static final String STATION_COUNT = "station_count";

        public static Uri buildCountryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static class LanguageEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_LANGUAGE)
                .build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_LANGUAGE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_LANGUAGE;

        public static final String TABLE_NAME = "languages";
        public static final String VALUE = "name";

        public static Uri buildLanguageUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static class PopularStationsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_POPULARS)
                .build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULARS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULARS;

        public static final String TABLE_NAME = "populars";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String STREAM_URL = "url";
        public static final String ICON_URL = "icon_url";
        public static final String COUNTY = "country";
        public static final String LANGUAGE = "language";
        public static final String BITRATE = "bitrate";


        public static Uri buildPopularStationsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static byte[] getPictureByteOfArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap getBitmapFromByte(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
