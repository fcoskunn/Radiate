package com.fc.radiate.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fc.radiate.Activities.PlayerActivity;
import com.fc.radiate.Adapters.StationAdapter;
import com.fc.radiate.DataManagement.FetchData;
import com.fc.radiate.DataManagement.RadioContract.StationEntry;
import com.fc.radiate.DataManagement.Station;
import com.fc.radiate.Listeners.StationItemClickListener;
import com.fc.radiate.R;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationsOnCountryFragment extends Fragment implements StationItemClickListener, View.OnKeyListener, LoaderManager.LoaderCallbacks<Cursor> {
    public static final int ST_LOADER = 0;

    private static final String[] projectionString = {
            StationEntry.ID,
            StationEntry.NAME,
            StationEntry.STREAM_URL,
            StationEntry.ICON_URL,
            StationEntry.COUNTY,
            StationEntry.LANGUAGE,
            StationEntry.BITRATE,
    };
    private StationAdapter stationAdapter;
    private ProgressDialog progressDialog;


    public StationsOnCountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String country = getArguments().getString("CountryName");
        stationAdapter = new StationAdapter(null, this);
        RecyclerView rv = new RecyclerView(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(stationAdapter);
        FetchData.getStationByCountry(stationAdapter, country);
        getLoaderManager().initLoader(ST_LOADER, null, this);
        return rv;
    }

    @Override
    public void onStationItemClick(int clickedItemIndex, Station station) {
        Intent i = new Intent(getActivity(), PlayerActivity.class);
        i.putExtra("st", (Serializable) station);
        startActivity(i);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(ST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new CountriesFragment());
                fragmentTransaction.commit();
            }
        }
        return false;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        Log.v("sadasd", getArguments().getString("CountryName"));
        return new CursorLoader(getContext(),
                StationEntry.CONTENT_URI,
                projectionString,
                StationEntry.COUNTY + " =? ",
                new String[]{getArguments().getString("CountryName")},
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        stationAdapter.swapCursor(cursor);
        progressDialog.dismiss();
        Log.v("dsadasdsad", "dismiss");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        stationAdapter.swapCursor(null);
    }
}

