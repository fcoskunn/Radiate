package com.fc.radiate.ViewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fc.radiate.Listeners.CountryItemClickListener;
import com.fc.radiate.R;

public class CountryViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView countryNameView;
    private CountryItemClickListener clickListener;
    private String Country;



    public CountryViewHolder(@NonNull View itemView, CountryItemClickListener clickListener) {
        super(itemView);
        countryNameView = itemView.findViewById(R.id.CountryName_VH);
        itemView.setOnClickListener(this);
        this.clickListener = clickListener;

    }

    public void bind(String CountryName){
        countryNameView.setText(CountryName);
        Country = CountryName;
    }

    @Override
    public void onClick(View v) {
        int clickedPosition = getAdapterPosition();
        clickListener.onCountryItemClick(clickedPosition, Country);
    }
}
