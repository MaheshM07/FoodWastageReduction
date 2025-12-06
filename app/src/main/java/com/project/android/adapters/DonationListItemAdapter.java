package com.project.android.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.android.R;
import com.project.android.model.Donation;
import com.project.android.model.Organization;
import com.project.android.utility.Constants;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DonationListItemAdapter extends ArrayAdapter<Donation> {
    private Activity context;
    List<Donation> donationList;
    LayoutInflater inflater;

    public DonationListItemAdapter(Activity context, int resourceId)
    {
        super(context, resourceId);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder
    {
        TextView numberOfPersonsTV;
        TextView dateTV;
        TextView foodTypeTV;

    }

    public View getView(int position, View view, ViewGroup parent) {

        final DonationListItemAdapter.ViewHolder holder;
        if (view == null) {
            holder = new DonationListItemAdapter.ViewHolder();
            view = inflater.inflate(R.layout.alldonationslist_item, null);
            holder.numberOfPersonsTV = view.findViewById(R.id.numberOfPersons);
            holder.dateTV =  view.findViewById(R.id.date);
            holder.foodTypeTV = view.findViewById(R.id.foodType);
            view.setTag(holder);
        } else
        {
            holder = (DonationListItemAdapter.ViewHolder) view.getTag();
        }

        holder.numberOfPersonsTV.setText("Donated for:" + String.valueOf(donationList.get(position).getNumberOfPersons()) +" persons");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String date = dateFormatter.format(donationList.get(position).getDate().getTime());

        holder.dateTV.setText("Donated On:" +date);
        holder.foodTypeTV.setText("Food Type:" + donationList.get(position).getFoodType());

        return view;
    }

    public List<Donation> getDonationList() {
        return this.donationList;
    }

    public void setDonationList(List<Donation> donationList)
    {
        this.donationList = donationList;
    }

    @Override
    public int getCount() {
        return donationList.size();
    }

    @Override
    public Donation getItem(int position) {
        return donationList.get(position);
    }

}

