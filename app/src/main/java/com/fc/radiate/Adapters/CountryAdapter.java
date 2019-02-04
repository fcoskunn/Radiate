package com.fc.radiate.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fc.radiate.Listeners.CountryItemClickListener;
import com.fc.radiate.R;
import com.fc.radiate.ViewHolders.CountryViewHolder;

public class CountryAdapter extends RecyclerView.Adapter<CountryViewHolder> {
    private final CountryItemClickListener clickListener;
    private Cursor cursor;

    public CountryAdapter(Cursor cursor, CountryItemClickListener clickListener) {
        this.cursor = cursor;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new CountryViewHolder(inflater.inflate(R.layout.fragment_country_item, viewGroup, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder countryViewHolder, int i) {
        cursor.moveToPosition(i);
        String name = cursor.getString(0);
        int sCount = cursor.getInt(1);
        countryViewHolder.bind(name
                + " (" + sCount + ")");
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }
}
