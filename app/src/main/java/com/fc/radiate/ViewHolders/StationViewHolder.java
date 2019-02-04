package com.fc.radiate.ViewHolders;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fc.radiate.App;
import com.fc.radiate.DataManagement.RadioContract;
import com.fc.radiate.DataManagement.RadioContract.FavEntry;
import com.fc.radiate.DataManagement.Station;
import com.fc.radiate.Listeners.StationItemClickListener;
import com.fc.radiate.R;
import com.squareup.picasso.Picasso;

public class StationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    Context context;
    private Station station;
    private String stationId;
    private TextView stationName;
    private TextView stationCountry;
    private TextView stationBitValue;
    private Boolean stationFavStatus;
    private ImageButton favButton;
    private ImageView iconView;
    private StationItemClickListener clickListener;

    private static final String[] projectionString = {
            RadioContract.StationEntry.ID,
            RadioContract.StationEntry.NAME,
            RadioContract.StationEntry.STREAM_URL,
            RadioContract.StationEntry.ICON_URL,
            RadioContract.StationEntry.COUNTY,
            RadioContract.StationEntry.LANGUAGE,
            RadioContract.StationEntry.BITRATE,

    };

    public StationViewHolder(@NonNull final View itemView, StationItemClickListener clickListener) {
        super(itemView);
        context = itemView.getContext();
        stationName = itemView.findViewById(R.id.VH_StationName);
        stationCountry = itemView.findViewById(R.id.VH_StationCountry);
        stationBitValue = itemView.findViewById(R.id.VH_StationBit);
        favButton = itemView.findViewById(R.id.VH_FavButton);
        iconView = itemView.findViewById(R.id.VH_StationImage);
        itemView.setOnClickListener(this);
        this.clickListener = clickListener;

    }

    public void bind(final Station station) {
        this.station = station;
        stationId = station.getId();
        stationName.setText(station.getName());
        stationCountry.setText(station.getCountry());
        stationBitValue.setText(station.getBitrate());
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        boolean ShouldGetImgOverInternet = pref.getBoolean("DownloadImages", true);

        if (!station.getFavicon().equals("") && ShouldGetImgOverInternet) {
            Picasso.get()
                    .load(station.getFavicon())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(iconView);
        } else {
            iconView.setImageResource(R.mipmap.ic_launcher);
        }

        /*
        ContentResolver mResolver = App.getContext().getContentResolver();
        String selectedString = FavEntry.NAME + " =? ";
        String[] selectionArgs = {station.getName()};
        Cursor stationCursor;
        stationCursor = mResolver.query(FavEntry.CONTENT_URI,
                null,
                selectedString,
                selectionArgs,
                null
        );

        Log.v("Size of cursor" , String.valueOf(stationCursor.getCount()));
        if (stationCursor.getCount() == 0) {
            favButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        } else {
            favButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        }
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver mResolver = context.getContentResolver();
                String selectedString = FavEntry.ID + "= ? ";
                String[] selectionArgs = {station.getId()};
                Cursor stationCursor;
                stationCursor = mResolver.query(FavEntry.CONTENT_URI,
                        projectionString,
                        selectedString,
                        selectionArgs,
                        null
                );
                if (stationCursor.getCount() == 0) {
                    favButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                    ContentValues Values = new ContentValues();
                    Values.put(FavEntry.ID, station.getId());
                    Values.put(FavEntry.NAME, station.getName());
                    Values.put(FavEntry.STREAM_URL, station.getUrl());
                    Values.put(FavEntry.ICON_URL, station.getFavicon());
                    Values.put(FavEntry.COUNTY, station.getCountry());
                    Values.put(FavEntry.LANGUAGE, station.getLanguage());
                    Values.put(FavEntry.BITRATE, station.getBitrate());
                    mResolver.insert(FavEntry.CONTENT_URI, Values);
                } else {
                    favButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                    context.getContentResolver().delete(FavEntry.CONTENT_URI, selectedString, selectionArgs);
                }
            }
        });

        */
    }

    @Override
    public void onClick(View v) {
        int clickedPosition = getAdapterPosition();
        clickListener.onStationItemClick(clickedPosition, station);
    }

}
