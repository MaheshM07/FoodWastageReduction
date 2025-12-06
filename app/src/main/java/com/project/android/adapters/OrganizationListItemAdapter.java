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


import java.util.List;

import com.project.android.R;
import com.project.android.model.Organization;
import com.project.android.utility.Constants;

public class OrganizationListItemAdapter extends ArrayAdapter<Organization> {
    private Activity context;
    List<Organization> organizationList;
    LayoutInflater inflater;

    public OrganizationListItemAdapter(Activity context, int resourceId)
    {
        super(context, resourceId);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder
    {
        TextView nameTV;
        TextView mailTV;
        TextView mobileTV;
        TextView addressTV;
        ImageView iconIV;
    }

    public View getView(int position, View view, ViewGroup parent) {

        final OrganizationListItemAdapter.ViewHolder holder;
        if (view == null) {
            holder = new OrganizationListItemAdapter.ViewHolder();
            view = inflater.inflate(R.layout.allorganizationslist_item, null);
            holder.nameTV = view.findViewById(R.id.name);
            holder.mailTV =  view.findViewById(R.id.mail);
            holder.mobileTV = view.findViewById(R.id.mobile);
            holder.addressTV=view.findViewById(R.id.address);
            holder.iconIV = view.findViewById(R.id.profile);
            view.setTag(holder);
        } else
        {
            holder = (OrganizationListItemAdapter.ViewHolder) view.getTag();
        }

        holder.nameTV.setText(organizationList.get(position).getOrganizationname());
        holder.mailTV.setText(organizationList.get(position).getEmail());
        holder.mobileTV.setText(organizationList.get(position).getPhno());
        holder.addressTV.setText(organizationList.get(position).getAddress());
        String imagePath = organizationList.get(position).getProfilePath();
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

    public List<Organization> getOrganizationList() {
        return this.organizationList;
    }

    public void setOrganizationList(List<Organization> organizationList)
    {
        this.organizationList = organizationList;
    }

    @Override
    public int getCount() {
        return organizationList.size();
    }

    @Override
    public Organization getItem(int position) {
        return organizationList.get(position);
    }

}

