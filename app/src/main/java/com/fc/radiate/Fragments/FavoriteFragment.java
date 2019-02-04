package com.fc.radiate.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fc.radiate.Activities.*;
import com.fc.radiate.Activities.PlayerActivity;
import com.fc.radiate.Adapters.StationAdapter;
import com.fc.radiate.DataManagement.RadioContract.FavEntry;
import com.fc.radiate.DataManagement.RadioContract.StationEntry;
import com.fc.radiate.DataManagement.Station;
import com.fc.radiate.Listeners.StationItemClickListener;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements StationItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int FAV_LOADER = 0;
    private static final String[] projectionString = {
            StationEntry.ID,
            StationEntry.NAME,
            StationEntry.STREAM_URL,
            StationEntry.ICON_URL,
            StationEntry.COUNTY,
            StationEntry.LANGUAGE,
            StationEntry.BITRATE,
    };
    private static StationAdapter stationAdapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(FAV_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Cursor cur = getContext().getContentResolver().query(
                FavEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );


        stationAdapter = new StationAdapter(null, this);
        RecyclerView rv = new RecyclerView(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(stationAdapter);
        return rv;
    }

    @Override
    public void onStationItemClick(int clickedItemIndex, Station station) {
        Intent i = new Intent(getActivity(), PlayerActivity.class);
        i.putExtra("st", (Serializable) station);
        startActivity(i);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(getContext(), FavEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        stationAdapter.swapCursor(cursor);
        MainActivity.progressDialog.dismiss();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        stationAdapter.swapCursor(null);
    }
}
