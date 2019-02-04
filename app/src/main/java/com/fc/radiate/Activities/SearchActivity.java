package com.fc.radiate.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.fc.radiate.Adapters.StationAdapter;
import com.fc.radiate.DataManagement.FetchData;
import com.fc.radiate.DataManagement.Station;
import com.fc.radiate.DataManagement.RadioContract.*;
import com.fc.radiate.Listeners.*;
import com.fc.radiate.R;
import java.io.Serializable;

public class SearchActivity extends AppCompatActivity implements StationItemClickListener{
    private static final String[] projectionString = {
            StationEntry.ID,
            StationEntry.NAME,
            StationEntry.STREAM_URL,
            StationEntry.ICON_URL,
            StationEntry.COUNTY,
            StationEntry.LANGUAGE,
            StationEntry.BITRATE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        Cursor cur = getBaseContext().getContentResolver().query(
                StationEntry.CONTENT_URI,
                projectionString,
                null,
                null,
                null
        );
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = findViewById(R.id.searchRV);
        StationAdapter stationAdapter = new StationAdapter(cur, this);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(stationAdapter);
    }

    @Override
    public void onStationItemClick(int clickedItemIndex, Station station) {
        Intent i = new Intent(this,PlayerActivity.class);
        i.putExtra("st", (Serializable) station);
        startActivity(i);
    }
}