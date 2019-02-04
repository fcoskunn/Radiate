package com.fc.radiate.Fragments;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fc.radiate.Activities.MainActivity;
import com.fc.radiate.Adapters.CountryAdapter;
import com.fc.radiate.DataManagement.FetchData;
import com.fc.radiate.DataManagement.RadioContract.CountryEntry;
import com.fc.radiate.Listeners.CountryItemClickListener;
import com.fc.radiate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountriesFragment extends Fragment implements CountryItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int COUNTRY_LOADER = 0;
    private static final String[] projectionString = {
            CountryEntry.VALUE,
            CountryEntry.STATION_COUNT,
    };
    private static CountryAdapter countryAdapter;
    private StationsOnCountryFragment stationsOnCountryFragment;

    public CountriesFragment() {
        stationsOnCountryFragment = new StationsOnCountryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView rv = new RecyclerView(getContext());
        countryAdapter = new CountryAdapter(null, this);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        FetchData.fetchCountries(countryAdapter);
        rv.setAdapter(countryAdapter);
        return rv;
    }

    @Override
    public void onCountryItemClick(int clickedItemIndex, String clickedCountryName) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, stationsOnCountryFragment);
        Bundle bundle = new Bundle();
        bundle.putString("CountryName", FetchData.CapsFirst(clickedCountryName.substring(0, clickedCountryName.indexOf('('))));
        stationsOnCountryFragment.setArguments(bundle);
        MainActivity.progressDialog.show();
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(COUNTRY_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(getContext(),
                CountryEntry.CONTENT_URI,
                projectionString,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        countryAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        countryAdapter.swapCursor(null);
    }
}
