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

import com.fc.radiate.Activities.PlayerActivity;
import com.fc.radiate.Adapters.StationAdapter;
import com.fc.radiate.DataManagement.FetchData;
import com.fc.radiate.DataManagement.RadioContract.PopularStationsEntry;
import com.fc.radiate.DataManagement.RadioContract.StationEntry;
import com.fc.radiate.DataManagement.Station;
import com.fc.radiate.Listeners.StationItemClickListener;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment implements StationItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    public static final int POP_LOADER = 0;
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

    public PopularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView rv = new RecyclerView(getContext());

        getLoaderManager().initLoader(POP_LOADER, null, this);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        stationAdapter = new StationAdapter(null, this);
        rv.setAdapter(stationAdapter);
        FetchData.fetchTopclickStations(stationAdapter, "20");
        return rv;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(POP_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
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
        return new CursorLoader(getContext(),
                PopularStationsEntry.CONTENT_URI,
                projectionString,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        stationAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        stationAdapter.swapCursor(null);

    }
}