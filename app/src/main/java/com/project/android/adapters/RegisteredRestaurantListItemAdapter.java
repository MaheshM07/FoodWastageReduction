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
import com.project.android.model.Restaurant;
import com.project.android.utility.Constants;

import java.util.List;

public class RegisteredRestaurantListItemAdapter extends ArrayAdapter<Restaurant>
{
    private Activity context;
    List<Restaurant> restaurantList;
    LayoutInflater inflater;

    public RegisteredRestaurantListItemAdapter(Activity context, int resourceId)
    {
        super(context, resourceId);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder
    {
        TextView nameTV;
        ImageView iconIV;
    }

    public View getView(int position, View view, ViewGroup parent) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.registeredrestaurantslist_item, null);

            holder.nameTV = view.findViewById(R.id.name);
            holder.iconIV = view.findViewById(R.id.profile);
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }

        holder.nameTV.setText(restaurantList.get(position).getName());
        String imagePath = restaurantList.get(position).getProfilePath();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        if (null != bitmap) {
            holder.iconIV.setImageBitmap(bitmap);
        }
        else
        {
            int resID = context.getResources().getIdentifier("noimage", Constants.DRAWABLE_RESOURCE, context.getPackageName());
            holder.iconIV.setImageResource(resID);
        }

        return view;
    }

    public List<Restaurant> getRestaurantList() {
        return this.restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList)
    {
        this.restaurantList = restaurantList;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Restaurant getItem(int position) {
        return restaurantList.get(position);
    }

}
