package com.example.inventorymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanager.Profile;
import com.example.inventorymanager.R;

import java.util.ArrayList;

public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Profile> profileArrayList;

    public HomeViewAdapter(Context context, ArrayList<Profile> profileArrayList) {
        this.context = context;
        this.profileArrayList = profileArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homeview_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // grab profile at index
        Profile profile = profileArrayList.get(position);

        //set name and sub description in recycler view
        holder.name.setText(profile.getProfileName());
        holder.subDesc.setText(profile.getBusinessOrPersonal());

    }


    @Override
    public int getItemCount() {
        return profileArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView subDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            subDesc = itemView.findViewById(R.id.subdesc);
        }
    }
}