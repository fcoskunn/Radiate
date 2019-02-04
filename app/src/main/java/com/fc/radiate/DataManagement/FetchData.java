package com.fc.radiate.DataManagement;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.fc.radiate.Activities.MainActivity;
import com.fc.radiate.Adapters.CountryAdapter;
import com.fc.radiate.Adapters.StationAdapter;
import com.fc.radiate.Api.ApiService;
import com.fc.radiate.Api.Client;
import com.fc.radiate.App;
import com.fc.radiate.DataManagement.RadioContract.CountryEntry;
import com.fc.radiate.DataManagement.RadioContract.LanguageEntry;
import com.fc.radiate.DataManagement.RadioContract.PopularStationsEntry;
import com.fc.radiate.DataManagement.RadioContract.StationEntry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchData {
    static List<Country> countryList;
    static List<String> langList;
    static List<String> cList;
    static List<Language> languageList;
    static List<Station> favs;
    static List<Station> stationList;
    static List<Station> searchResult;
    private static Station currentStation;

    public static List<Station> getStationList() {
        return stationList;
    }

    public static void setStationList(List<Station> stationList) {
        FetchData.stationList = stationList;
    }

    public static List<Station> getSearchResult() {
        return searchResult;
    }

    public static void fetchCountries(final CountryAdapter countryAdapter) {
        Client client = new Client();
        final ApiService apiService = client.getClient().create(ApiService.class);
        final Call<List<Country>> counrtyCall = apiService.getCountries();
        counrtyCall.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                countryList = response.body();
                //countryAdapter.updateView(countryList);
                for (Country country : countryList) {
                    addCountry(country);
                    Log.v("TETETETE", String.valueOf(country.getName()+" -- "+country.getStationcount()));
                }
                MainActivity.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.v("ERRORFC", "getting countries did not work!");
            }
        });
    }

    public static void fetchSearchResult(
            //final StationAdapter stationAdapter,
            String SearchName,
            String countryName,
            String languageName,
            String tags,
            String limit,
            String orderBy,
            boolean reverse,
            int offset
    ) {
        searchResult = new ArrayList<>();
        Client client = new Client();
        final ApiService apiService = client.getClient().create(ApiService.class);
        if (languageName.equals("All")) languageName = "";
        if (countryName.equals("All")) countryName = "";
        final Call<List<Station>> stationCall = apiService.getSearchResults(
                SearchName,
                countryName,
                languageName,
                tags,
                limit,
                orderBy,
                reverse,
                offset);
        stationCall.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                searchResult = response.body();
                if (searchResult == null) {
                    Log.v("RESULT", "NOTFOUND ");
                } else {
                    Log.v("RESULT", "FOUND " + searchResult.size());
                }
                for (Station station : searchResult) {
                    addStation(station);
                }
                Log.v("URLRESPONSE", response.toString());
                MainActivity.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {

            }
        });
    }

    public static void fetchCountries(final ArrayAdapter<String> arrayAdapter) {
        cList = new ArrayList<>();
        cList.add("All");
        Client client = new Client();
        final ApiService apiService = client.getClient().create(ApiService.class);
        final Call<List<Country>> counrtyCall = apiService.getCountries();
        counrtyCall.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                countryList = response.body();
                for (int i = 0; i < countryList.size(); i++) {
                    cList.add(countryList.get(i).getName());
                }
                arrayAdapter.addAll(cList);
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.v("ERRORFC", "getting countries did not work!");
            }
        });
    }

    public static void fetchLanguages(final ArrayAdapter<String> arrayAdapter) {
        langList = new ArrayList<>();
        langList.add("All");
        Client client = new Client();
        final ApiService apiService = client.getClient().create(ApiService.class);
        final Call<List<Language>> languageCall = apiService.getLanguages();
        languageCall.enqueue(new Callback<List<Language>>() {
            @Override
            public void onResponse(Call<List<Language>> call, Response<List<Language>> response) {
                languageList = response.body();
                for (int i = 0; i < languageList.size(); i++) {
                    langList.add(CapsFirst(languageList.get(i).getName()));
                    addLanguage(languageList.get(i));
                }
                arrayAdapter.addAll(langList);
            }

            @Override
            public void onFailure(Call<List<Language>> call, Throwable t) {

            }
        });
    }

    public static Station getCurrentStation() {
        return currentStation;
    }

    public static void setCurrentStation(Station currentStation) {
        FetchData.currentStation = currentStation;
    }

    public static void fetchTopclickStations(final StationAdapter stationAdapter, String url) {
        Client client = new Client();
        final ApiService apiService = client.getClient().create(ApiService.class);
        final Call<List<Station>> stationCall = apiService.getTopclickStations(url);
        stationCall.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                stationList = response.body();
                //stationAdapter.updateView(stationList);
                for (Station station : stationList) {
                    addStation(station);
                    addPopularStation(station);
                }
                MainActivity.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {

            }
        });

    }

    public static void getStationByCountry(final StationAdapter stationAdapter, String url) {
        Client client = new Client();
        MainActivity.progressDialog.show();
        final ApiService apiService = client.getClient().create(ApiService.class);
        final Call<List<Station>> stationCall = apiService.getStationByCountry(url, "clickcount", true);
        stationCall.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                stationList = response.body();
                //stationAdapter.updateView(stationList);
                for (Station station : stationList) {
                    addStation(station);
                }
                MainActivity.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {

            }
        });

    }

    public static String CapsFirst(String str) {
        String[] words = str.split(" ");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            ret.append(Character.toUpperCase(words[i].charAt(0)));
            ret.append(words[i].substring(1));
            if (i < words.length - 1) {
                ret.append(' ');
            }
        }
        return ret.toString();
    }

    public static long addStation(Station station) {
        ContentResolver mResolver = App.getContext().getContentResolver();
        Uri insertedUri;
        ContentValues stationValues = new ContentValues();
        String[] projectedColumns = {StationEntry.ID};
        String selectedString = StationEntry.ID + "=?";
        String[] selectionArgs = {station.getId()};
        Cursor stationCursor;
        long val = -1;
        stationCursor = mResolver.query(StationEntry.CONTENT_URI,
                projectedColumns,
                selectedString,
                selectionArgs,
                null
        );

        if (stationCursor.moveToFirst()) {
            int stationIdIndex = stationCursor.getColumnIndex(StationEntry.ID);
            //val = stationCursor.getLong(stationIdIndex);
        } else {
            stationValues.put(StationEntry.ID, station.getId());
            stationValues.put(StationEntry.NAME, station.getName());
            stationValues.put(StationEntry.STREAM_URL, station.getUrl());
            stationValues.put(StationEntry.ICON_URL, station.getFavicon());
            stationValues.put(StationEntry.COUNTY, station.getCountry());
            stationValues.put(StationEntry.LANGUAGE, station.getLanguage());
            stationValues.put(StationEntry.BITRATE, station.getBitrate());
            insertedUri = mResolver.insert(StationEntry.CONTENT_URI, stationValues);
            val = ContentUris.parseId(insertedUri);
        }
        return val;

    }

    public static long addCountry(Country country) {
        ContentResolver mResolver = App.getContext().getContentResolver();
        Uri insertedUri;
        ContentValues countryInfo = new ContentValues();
        String[] projectedColumns = {CountryEntry.VALUE};
        String selectedString = CountryEntry.VALUE + "=?";
        String[] selectionArgs = {CapsFirst(country.getName())};
        Cursor countryCursor;
        long val=-1;
        countryCursor = mResolver.query(CountryEntry.CONTENT_URI,
                projectedColumns,
                selectedString,
                selectionArgs,
                null
        );
        if (countryCursor.moveToFirst()) {
            int countryIdIndex = countryCursor.getColumnIndex(CountryEntry._ID);
            //val = countryCursor.getLong(countryIdIndex);
        } else {
            countryInfo.put(CountryEntry.VALUE, CapsFirst(country.getName()));
            countryInfo.put(CountryEntry.STATION_COUNT, Integer.valueOf(country.getStationcount()));
            insertedUri = mResolver.insert(CountryEntry.CONTENT_URI, countryInfo);
            val = ContentUris.parseId(insertedUri);
        }
        return val;
    }

    public static long addLanguage(Language language) {
        ContentResolver mResolver = App.getContext().getContentResolver();
        Uri insertedUri;
        ContentValues languageInfo = new ContentValues();
        String[] projectedColumns = {LanguageEntry.VALUE};
        String selectedString = LanguageEntry.VALUE + "=?";
        String[] selectionArgs = {language.getName()};
        Cursor languageCursor;
        long val= -1;
        languageCursor = mResolver.query(LanguageEntry.CONTENT_URI,
                projectedColumns,
                selectedString,
                selectionArgs,
                null
        );
        if (languageCursor.moveToFirst()) {
            int languageIdIndex = languageCursor.getColumnIndex(LanguageEntry._ID);
            //val = languageCursor.getLong(languageIdIndex);
        } else {
            languageInfo.put(LanguageEntry.VALUE, language.getName());
            insertedUri = mResolver.insert(LanguageEntry.CONTENT_URI, languageInfo);
            val = ContentUris.parseId(insertedUri);
        }
        return val;
    }

    public static long addPopularStation(Station station) {
        ContentResolver mResolver = App.getContext().getContentResolver();
        Uri insertedUri;
        ContentValues popularStationInfo = new ContentValues();
        String[] projectedColumns = {PopularStationsEntry.ID};
        String selectedString = PopularStationsEntry.ID + "=?";
        String[] selectionArgs = {station.getId()};
        Cursor popularStationCursor;
        long val = -1;
        popularStationCursor = mResolver.query(PopularStationsEntry.CONTENT_URI,
                projectedColumns,
                selectedString,
                selectionArgs,
                null
        );
        if (popularStationCursor.moveToFirst()) {
            int popularStationIdIndex = popularStationCursor.getColumnIndex(PopularStationsEntry._ID);
            //val = popularStationCursor.getLong(popularStationIdIndex);
        } else {
            popularStationInfo.put(StationEntry.ID, station.getId());
            popularStationInfo.put(StationEntry.NAME, station.getName());
            popularStationInfo.put(StationEntry.STREAM_URL, station.getUrl());
            popularStationInfo.put(StationEntry.ICON_URL, station.getFavicon());
            popularStationInfo.put(StationEntry.COUNTY, station.getCountry());
            popularStationInfo.put(StationEntry.LANGUAGE, station.getLanguage());
            popularStationInfo.put(StationEntry.BITRATE, station.getBitrate());
            insertedUri = mResolver.insert(PopularStationsEntry.CONTENT_URI, popularStationInfo);
            val = ContentUris.parseId(insertedUri);
        }
        return val;
    }



}


