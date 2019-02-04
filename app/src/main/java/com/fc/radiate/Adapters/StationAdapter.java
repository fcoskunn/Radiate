package com.fc.radiate.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fc.radiate.DataManagement.Station;
import com.fc.radiate.Listeners.StationItemClickListener;
import com.fc.radiate.R;
import com.fc.radiate.ViewHolders.StationViewHolder;

public class StationAdapter extends RecyclerView.Adapter<StationViewHolder> {
    private final StationItemClickListener clickListener;
    Cursor cursor;

    public StationAdapter(Cursor cursor, StationItemClickListener clickListener) {
        this.cursor = cursor;
        this.clickListener = clickListener;
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new StationViewHolder(inflater.inflate(R.layout.fragment_item, viewGroup, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder stationViewHolder, int i) {
        cursor.moveToPosition(i);
        Station station = new Station(
                String.valueOf(cursor.getInt(0)),
                String.valueOf(cursor.getString(1)),
                String.valueOf(cursor.getString(2)),
                String.valueOf(cursor.getString(3)),
                String.valueOf(cursor.getString(4)),
                String.valueOf(cursor.getString(5)),
                String.valueOf(cursor.getString(6))
        );
        stationViewHolder.bind(station);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }


}
